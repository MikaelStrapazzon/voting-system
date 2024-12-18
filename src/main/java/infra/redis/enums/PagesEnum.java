package infra.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PagesEnum {
  NewVoteSession("NewVoteSession.json"),
  UserVote("UserVote.json");

  private final String jsonFileName;
}
