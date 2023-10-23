package come.example.persistence.reactive.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.persistence.domain.UserModel;
import com.example.persistence.reactive.user.UserR2dbcPersistenceImpl;
import com.example.persistence.reactive.user.config.R2dbcConfiguration;
import com.example.persistence.reactive.user.entity.UserEntity;
import com.example.persistence.reactive.user.entity.callback.UniqueIdCallback;
import com.example.persistence.reactive.user.entity.callback.UniqueIdEntity;
import com.example.utils.generator.UniqueIdGenerator;

import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataR2dbcTest
@ContextConfiguration(classes = {
        R2dbcConfiguration.class,
        UserR2dbcPersistenceImpl.class,
        UniqueIdCallback.class // add UUID Generator for unit test
})
public class UserR2dbcPersistenceImplTest {

    @Autowired
    private UserR2dbcPersistence<UserModel> userPersistence;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    UserEntity testUser;

    @BeforeAll
    void beforeAll() {
        this.entityTemplate.getDatabaseClient()
                .sql(
                        "CREATE TABLE IF NOT EXISTS TEST_USER ( " +
                                "   user_id VARCHAR(36) PRIMARY KEY, " +
                                "   account VARCHAR(30) NOT NULL, " +
                                "   password VARCHAR(255) NOT NULL, " +
                                "   role VARCHAR(255) NOT NULL, " +
                                "   info_id BIGINT " +
                                " );")
                .then().block();
    }

    @BeforeEach
    void beforeEach() {
        Hooks.onOperatorDebug();

        testUser = new UserEntity("test-account", "test-password", "user");

        this.entityTemplate.getDatabaseClient()
                .sql("DELETE FROM TEST_USER")
                .then()
                .block();
    }

    @Test
    @DisplayName("UserEntity 영속성 때 UniqueIdCallback 동작 테스트")
    void test_create_userEntity_with_callback() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {

            assertThat(testUser).isInstanceOf(UniqueIdEntity.class);

            // 36 길이 목킹
            String mockId = "123456789012345678901234567890123456";

            when(UniqueIdGenerator.uuid()).thenReturn(mockId);

            StepVerifier.create(userPersistence.save(testUser))
                    .expectNextMatches(user -> user.getUserId().equals(mockId))
                    .expectComplete()
                    .verify();
        }
    }

    @Test
    @DisplayName("findByUserId not found 테스트")
    void test_findByUserId() {
        String notFoundId = "nope";

        StepVerifier.create(userPersistence.findByUserId(notFoundId))
                .verifyComplete();
    }

    @Test
    @DisplayName("findByUserId 정상 조회 테스트")
    void test_findByUserId_success() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            String mockId = "test-id";
            when(UniqueIdGenerator.uuid()).thenReturn(mockId);

            userPersistence.save(testUser).block();

            StepVerifier.create(userPersistence.findByUserId(mockId))
                    .expectNextMatches(newUser -> newUser.getUserId().equals(mockId))
                    .expectComplete()
                    .verify();
        }
    }

    @Test
    @DisplayName("findByAccount 정상 조회 테스트")
    void test_findByAccount_success() {
        userPersistence.save(testUser).block();

        StepVerifier.create(userPersistence.findByAccount(testUser.getAccount()))
                .expectNextMatches(newUser -> newUser.getAccount().equals(testUser.getAccount()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("remove 정상 테스트")
    void test_remove_success() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            String mockId = "test-id";
            when(UniqueIdGenerator.uuid()).thenReturn(mockId);

            userPersistence.save(testUser).block();

            StepVerifier.create(userPersistence.findByUserId(mockId))
                    .assertNext(newUser -> newUser.getUserId().equals(mockId))
                    .expectComplete()
                    .verify();

            userPersistence.remove(mockId).block();

            StepVerifier.create(userPersistence.findByUserId(mockId))
                    .verifyComplete();
        }
    }



    static Stream<Arguments> likeAccountTestParams() {
        return Stream.of(
            Arguments.of(Arrays.asList("test1", "test2", "test3"), "te", 3),
            Arguments.of(Arrays.asList("test1", "test2", "hello"), "ll", 1)
        );
    }

    @ParameterizedTest(name = "searchLikeByAccount like 검색 테스트")
    @MethodSource("likeAccountTestParams")
    public void test_searchLikeByAccount(List<String> accounts, String searchAcct, Integer resultCount) {
        for (String acc : accounts) {
            UserEntity testUser = new UserEntity(acc, "test-password", "user");
            userPersistence.save(testUser).block();
        }

        StepVerifier.create(userPersistence.searchLikeByAccount(searchAcct))
            .expectNextCount(resultCount)
            .verifyComplete();
    }
}