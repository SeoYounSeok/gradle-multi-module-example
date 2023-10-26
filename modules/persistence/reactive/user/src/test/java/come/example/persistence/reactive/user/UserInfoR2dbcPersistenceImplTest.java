package come.example.persistence.reactive.user;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.adapter.reactor.UserInfoR2dbcPersistence;
import com.example.persistence.domain.UserInfoModel;
import com.example.persistence.reactive.user.UserInfoR2dbcPersistenceImpl;
import com.example.persistence.reactive.user.config.R2dbcConfiguration;
import com.example.persistence.reactive.user.entity.UserInfoEntity;

import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataR2dbcTest
@ContextConfiguration(classes = {
        R2dbcConfiguration.class,
        UserInfoR2dbcPersistenceImpl.class
})
public class UserInfoR2dbcPersistenceImplTest {

    @Autowired
    private UserInfoR2dbcPersistence<UserInfoModel> infoPersistence;

    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    UserInfoEntity testInfo;

    @BeforeAll
    void beforeAll() {
        this.entityTemplate.getDatabaseClient()
                .sql(
                        "CREATE TABLE IF NOT EXISTS TEST_USER_INFO ( " +
                                "   info_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                                "   username VARCHAR(30) NOT NULL, " +
                                "   birthday DATETIME NOT NULL " +
                                " );")
                .then().block();
    }

    @BeforeEach
    void beforeEach() {
        Hooks.onOperatorDebug();

        testInfo = new UserInfoEntity("test-name", LocalDateTime.now());

        this.entityTemplate.getDatabaseClient()
                .sql("DELETE FROM TEST_USER_INFO")
                .then()
                .block();
    }

    @Test
    @DisplayName("USerInfoEntity 영속성 테스트")
    void test_UserInfoEntity_save() {
        StepVerifier.create(infoPersistence.save(testInfo))
                .expectNextMatches(info -> info.getInfoId() != null)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("findByInfoId not found 테스트")
    void test_findByInfoId() {
        Long notFoundId = 999L;

        StepVerifier.create(infoPersistence.findByInfoId(notFoundId))
                .verifyComplete();
    }

    @Test
    @DisplayName("findByInfoId 정상 조회 테스트")
    void test_findByInfoId_success() {
        infoPersistence.save(testInfo).block();

        StepVerifier.create(infoPersistence.findByInfoId(testInfo.getInfoId()))
                .expectNextMatches(info -> (info.getInfoId() == testInfo.getInfoId()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("findByUserName 정상 조회 테스트")
    void test_findByUserName_success() {
        infoPersistence.save(testInfo).block();

        StepVerifier.create(infoPersistence.findByUserName(testInfo.getUserName()))
                .expectNextMatches(info -> info.getUserName().equals(testInfo.getUserName()))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("remove 테스트")
    void test_remove() {
        infoPersistence.save(testInfo).block();

        StepVerifier.create(infoPersistence.findByUserName(testInfo.getUserName()))
            .expectNextMatches(info -> info.getUserName().equals(testInfo.getUserName()))
            .expectComplete()
            .verify();

        infoPersistence.remove(testInfo.getInfoId()).block();

        StepVerifier.create(infoPersistence.findByUserName(testInfo.getUserName()))
            .verifyComplete();
    }

}
