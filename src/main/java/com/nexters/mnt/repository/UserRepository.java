package com.nexters.mnt.repository;

import com.nexters.mnt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
