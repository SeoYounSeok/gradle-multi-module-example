package com.example.persistence.jpa.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserInfoModel;
import com.example.persistence.domain.UserModel;
import com.example.persistence.jpa.user.config.JpaConfiguration;
import com.example.persistence.jpa.user.entity.UserEntity;

@DataJpaTest
@ContextConfiguration(classes = { UserPersistenceImpl.class, JpaConfiguration.class })
public class UserPersistenceImplTest {

    @Autowired
    private UserPersistence userPersistence;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity("test-account", "test-password", "test-role");
    }

    @Test
    void contextLoad() {
        assertThat(userPersistence).isNotNull();
    }

    @Test
    @DisplayName("객체 저장 테스트")
    void test_save_with_type_casting() {
        UserEntity newUser = (UserEntity) userPersistence.save(testUser);

        assertThat(newUser.getUserId()).isNotNull();
    }

    @Test
    @DisplayName("findByUserId Not-Found")
    void test_findByUserId_not_found() {
        Optional<UserEntity> user = userPersistence.findByUserId("test-id").map(UserEntity.class::cast);

        assertFalse(user.isPresent());
    }

    @Test
    @DisplayName("findByAccount ")
    void test_findByAccount() {
        UserEntity newUser = (UserEntity) userPersistence.save(testUser);

        assertTrue(userPersistence.findByAccount(newUser.getAccount()).isPresent());
    }

    @Test
    @DisplayName("객체 삭제 테스트")
    void test_remove() {
        userPersistence.save(testUser);

        assertTrue(userPersistence.findByUserId(testUser.getUserId()).isPresent());

        userPersistence.remove(testUser.getUserId());
        assertFalse(userPersistence.findByUserId(testUser.getUserId()).isPresent());
    }




    // 외부 모듈에서 *Model 인터페이스 상속받은 객체 주입시 정상 작동 테스트
    class TestUserDto implements UserModel {
        private String userId;
        private String account;
        private String password;
        private String role;
        private Long infoId;
        private Object userInfo; // not user in this test

        public TestUserDto(String account, String password, String role) {
            this.account = account;
            this.password = password;
            this.role = role;
        }

        public TestUserDto(UserModel model) {
            this.userId = model.getUserId();
            this.account = model.getAccount();
            this.password = model.getPassword();
            this.role = model.getRole();
            this.infoId = model.getInfoId();
            this.userInfo = model.getUserInfo();
        }
        
        @Override
        public String getUserId() { return this.userId;}

        @Override
        public String getAccount() { return this.account; }

        @Override
        public String getPassword() { return this.password; }

        @Override
        public String getRole() { return this.role; }

        @Override
        public Long getInfoId() { return this.infoId; }

        @Override
        public UserInfoModel getUserInfo() { return null; }
    }

    @Test
    @DisplayName("외부 모듈에서 *Model 인터페이스 상속받은 객체 주입시 정상 생성 테스트")
    void test_create_entity_from_outside() {
        TestUserDto fromOutside = new TestUserDto("account", "password", "role");

        // 외부 모듈은 반드시 해당 레이어의 인터페이스를 상속 하고 있어야 한다.
        assertThat(fromOutside).isInstanceOf(UserModel.class);

        UserEntity newUser = ((UserEntity) userPersistence.save(fromOutside));

        assertTrue(userPersistence.findByAccount(newUser.getAccount()).isPresent());
    }

    @Test
    @DisplayName("외부 모듈에서 객체 조회시 타입 케스팅 테스트")
    void test_findByUserId_from_outside() {
        userPersistence.save(testUser);

        UserModel model = userPersistence.findByUserId(testUser.getUserId()).orElseThrow();
        TestUserDto outSideDto = new TestUserDto(model);

        assertThat(outSideDto.getUserId()).isNotNull();
        assertThat(outSideDto.getAccount()).isEqualTo(testUser.getAccount());
        assertThat(outSideDto.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(outSideDto.getRole()).isEqualTo(testUser.getRole());
    }

}
