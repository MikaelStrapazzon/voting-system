package com.example.votingsystem.dto;

import lombok.Data;

@Data
public class VoteSessionOpenDto {
  private String title;
  private String description;
  private Integer duration;
}
