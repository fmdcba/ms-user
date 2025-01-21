package com.mindhub.ms_user.repositories;

import com.mindhub.ms_user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
