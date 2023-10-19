package com.example.persistence.jpa.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;
import com.example.persistence.jpa.user.entity.UserEntity;
import com.example.persistence.jpa.user.repository.JpaUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence<UserModel> {

    private final JpaUserRepository userRepository;

    @Override
    public Optional<UserModel> findByUserId(String userId) {
        return userRepository.findById(userId).map(UserModel.class::cast);
    }

    @Override
    public Optional<UserModel> findByAccount(String account) {
        return userRepository.findByAccount(account).map(UserModel.class::cast);
    }

    @Override
    public List<UserModel> searchLikeByAccount(String account) {
        return userRepository.findByAccountContaining(account).stream()
            .map(UserModel.class::cast)
            .collect(Collectors.toList());
    }

    @Override
    public UserModel save(UserModel domain) {
        if (domain instanceof UserEntity) {
            return userRepository.save((UserEntity) domain);    
        }
        return userRepository.save(ModelDownCasting.toUser(domain));
    }

    @Override
    public void remove(String userId) {
        userRepository.deleteById(userId);
    }


    // private UserEntity downcast(UserModel model) {
    //     UserEntity downCastingObject = new UserEntity();
    //     downCastingObject.setUserId(model.getUserId());
    //     downCastingObject.setAccount(model.getAccount());
    //     downCastingObject.setPassword(model.getPassword());
    //     downCastingObject.setRole(model.getRole());
    //     downCastingObject.setInfoId(model.getInfoId());
    //     downCastingObject.setUserInfo( model.getUserInfo());

    //     return downCastingObject;
    // }
    
}
