package com.example.votingsystem.repository;

import com.example.votingsystem.entity.SessionResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionResultsRepository extends JpaRepository<SessionResults, Integer> {}
