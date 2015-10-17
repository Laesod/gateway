package com.repository;

import com.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aautushk on 8/30/2015.
 */
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findAll(Pageable pageable);
    UserEntity findByUsername(String username);
    UserEntity findByEmailVerificationToken(String emailVerificationToken);
    UserEntity findByResetPasswordToken(String emailVerificationToken);
}
