package com.example.votingsystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Table(name = "session_results")
public class SessionResults implements Serializable {

  @Id
  @Column(name = "vote_session_id")
  private Long voteSessionId;

  @Column(name = "votes_yes", nullable = false)
  private Integer votesYes;

  @Column(name = "votes_no", nullable = false)
  private Integer votesNo;

  @Column(name = "non_voters", nullable = false)
  private Integer nonVoters;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
