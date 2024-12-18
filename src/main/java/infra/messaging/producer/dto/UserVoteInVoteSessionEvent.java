package infra.messaging.producer.dto;

import lombok.Data;

@Data
public class UserVoteInVoteSessionEvent {
  private Integer userId;
  private Integer voteSessionId;
  private Boolean vote;
}
