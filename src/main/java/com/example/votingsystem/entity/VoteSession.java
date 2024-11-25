package com.example.votingsystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "vote_sessions")
public class VoteSession implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "end_time")
  private LocalDateTime endTime;

  @Column(name = "open")
  private Boolean open;
}
