package com.example.votingsystem.repository;

import com.example.votingsystem.entity.VoteSession;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteSessionRepository extends JpaRepository<VoteSession, Integer> {
  @Query("SELECT vs FROM VoteSession vs WHERE vs.open = true AND vs.endTime < CURRENT_TIMESTAMP")
  List<VoteSession> findNeedClose();
}
