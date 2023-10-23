package com.example.persistence.jpa.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.adapter.common.UserInfoPersistence;
import com.example.persistence.domain.UserInfoModel;
import com.example.persistence.domain.UserModel;
import com.example.persistence.jpa.user.config.JpaConfiguration;
import com.example.persistence.jpa.user.entity.UserEntity;
import com.example.persistence.jpa.user.entity.UserInfoEntity;
import com.example.persistence.jpa.user.repository.JpaUserRepository;

@DataJpaTest
@ContextConfiguration(classes = { UserInfoPersistenceImpl.class, JpaConfiguration.class })
public class UserInfoPersistenceImplTest {

    @Autowired
    private UserInfoPersistence<UserInfoModel> infoPersistence;

    private UserInfoEntity testInfo;

    @BeforeEach
    void beforeEach() {
        testInfo = new UserInfoEntity("test", LocalDateTime.now());
    }

    @Test
    void context_load() {
        assertThat(infoPersistence).isNotNull();
    }


    @Autowired
    private JpaUserRepository userRepository;

    @Test
    @DisplayName("UserEntity와 연관관계 삭제 테스트")
    void test_remove_with_userEntity() {

        UserEntity user = new UserEntity("test", "test", "test");
        user.setUserInfo(testInfo);

        userRepository.save(user);

        String userId = user.getUserId();

        assertTrue(userRepository.findById(userId).isPresent());
        assertTrue(infoPersistence.findByInfoId(testInfo.getInfoId()).isPresent());

        userRepository.deleteById(userId);

        assertFalse(userRepository.findById(userId).isPresent());
        assertFalse(infoPersistence.findByInfoId(testInfo.getInfoId()).isPresent());
    }

    class TestUserInfoDto implements UserInfoModel {
        private Long infoId;
        private String userName;
        private LocalDateTime birthDay;
        private String userId;

        private UserModel user;

        public TestUserInfoDto(String userName, LocalDateTime birthDay, String userId, UserModel userModel) {
            this.userName = userName;
            this.birthDay = birthDay;
            this.userId = userId;
            this.user = userModel;
        }

        public TestUserInfoDto(UserInfoModel model) {
            this.infoId = model.getInfoId();
            this.userName = model.getUserName();
            this.birthDay = model.getBirthDay();
            this.userId = model.getUserId();
            this.user = model.getUser();
        }

        @Override
        public Long getInfoId() { return this.infoId; }

        @Override
        public String getUserName() { return this.userName; }

        @Override
        public LocalDateTime getBirthDay() { return this.birthDay; }

        @Override
        public String getUserId() { return this.userId; }

        @Override
        public UserModel getUser() { return this.user; }
    }


    @Test
    @DisplayName("외부 모듈에서 *Model 인터페이스 상속받은 객체 주입시 정상 생성 테스트")
    void test_create_userInfo_from_outside() {
        TestUserInfoDto dto = new TestUserInfoDto(null, null, null, null);

        assertThat(dto).isInstanceOf(UserInfoModel.class);

        UserInfoEntity newInfo = (UserInfoEntity) infoPersistence.save(dto);

        assertTrue(infoPersistence.findByInfoId(newInfo.getInfoId()).isPresent());
    }

    @Test
    @DisplayName("외부 모듈에서 객체 조회시 타입 케스팅 테스트")
    void test_find_from_outside() {
        infoPersistence.save(testInfo);

        UserInfoModel model = infoPersistence.findByInfoId(testInfo.getInfoId()).orElseThrow();
        TestUserInfoDto dto = new TestUserInfoDto(model);

        assertThat(dto.getInfoId()).isNotNull();
        assertThat(dto.getUserName()).isEqualTo(testInfo.getUserName());
        assertThat(dto.getBirthDay()).isEqualTo(testInfo.getBirthDay());
    }

}
