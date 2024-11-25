package com.example.votingsystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@Table(name = "votes")
public class Vote implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "vote_session_id")
  private Integer voteSessionId;

  @Column(name = "vote")
  private Boolean vote;

  @CreationTimestamp
  @Column(name = "voted_at")
  private LocalDateTime votedAt;
}
