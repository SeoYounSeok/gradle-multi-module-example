package come.example.persistence.reactive.user;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.persistence.reactive.user.UserInfoR2dbcPersistenceImpl;
import com.example.persistence.reactive.user.UserR2dbcPersistenceImpl;
import com.example.persistence.reactive.user.config.R2dbcConfiguration;
import com.example.persistence.reactive.user.entity.UserEntity;
import com.example.persistence.reactive.user.entity.UserInfoEntity;
import com.example.persistence.reactive.user.entity.callback.UniqueIdCallback;
import com.example.utils.generator.UniqueIdGenerator;

import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataR2dbcTest
@ContextConfiguration(classes = {
        R2dbcConfiguration.class,
        UserInfoR2dbcPersistenceImpl.class,
        UserR2dbcPersistenceImpl.class,
        UniqueIdCallback.class // add UUID Generator for unit test
})
public class R2dbcJoinPersistenceTest {
    
    @Autowired
    private UserR2dbcPersistence userPersistence;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

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

        this.entityTemplate.getDatabaseClient()
                .sql(
                        "CREATE TABLE IF NOT EXISTS TEST_USER_INFO ( " +
                                "   info_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                                "   username VARCHAR(30) NOT NULL, " +
                                "   birthday DATETIME NOT NULL " +
                                " );")
                .then().block();
    }

    UserEntity testUser;
    UserInfoEntity testInfo;

    @BeforeEach
    void beforeEach() {
        Hooks.onOperatorDebug();

        testInfo = new UserInfoEntity("test-name", LocalDateTime.now());
        testUser = new UserEntity("test-account", "test-password", "user");
        testUser.setUserInfo(testInfo);
    }



    @Test
    @DisplayName("User, UserInfo soft join 조회 테스트")
    void test_findbyUserId_with_UserInfo() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            String mockId = "test-id";
            when(UniqueIdGenerator.uuid()).thenReturn(mockId);

            userPersistence.save(testUser).block();

            StepVerifier.create(userPersistence.findByUserId(mockId))
                    .expectNextMatches(newUser -> {

                        assertThat(newUser.getUserId()).isEqualTo(mockId);
                        assertThat(newUser.getUserInfo().getInfoId()).isEqualTo(newUser.getInfoId());

                        return true;
                    })
                    .expectComplete()
                    .verify();
        }
    }

}
