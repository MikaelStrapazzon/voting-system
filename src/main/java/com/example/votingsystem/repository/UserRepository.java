package com.example.votingsystem.repository;

import com.example.votingsystem.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByCpf(String cpf);
}
