package com.example.votingsystem.repository;

import com.example.votingsystem.entity.VoteSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteSessionRepository extends JpaRepository<VoteSession, Integer> {}
