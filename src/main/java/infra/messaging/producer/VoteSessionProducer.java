package infra.messaging.producer;

import com.example.votingsystem.entity.SessionResults;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import com.example.votingsystem.mapper.messaging.VoteSessionProducerMapper;
import infra.messaging.producer.dto.EndVoteSessionEvent;
import infra.messaging.producer.dto.OpenVoteSessionEvent;
import infra.messaging.producer.dto.UserVoteInVoteSessionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteSessionProducer {
  private final StreamBridge streamBridge;

  private final VoteSessionProducerMapper voteSessionProducerMapper;

  public void openVoteSession(VoteSession voteSession) {
    openVoteSession(voteSessionProducerMapper.voteSessionToOpenVoteSessionEvent(voteSession));
  }

  public void openVoteSession(OpenVoteSessionEvent openVoteSessionEvent) {
    streamBridge.send("producer-start-vote-session", openVoteSessionEvent);
  }

  public void endVoteSession(SessionResults sessionResults) {
    endVoteSession(voteSessionProducerMapper.sessionResultsToEndVoteSessionEvent(sessionResults));
  }

  public void endVoteSession(EndVoteSessionEvent endVoteSessionEvent) {
    streamBridge.send("producer-end-vote-session", endVoteSessionEvent);
  }

  public void userVotedInVoteSession(Vote vote) {
    userVotedInVoteSession(voteSessionProducerMapper.voteToUserVoteInVoteSessionEvent(vote));
  }

  public void userVotedInVoteSession(UserVoteInVoteSessionEvent userVoteInVoteSessionEvent) {
    streamBridge.send("producer-user-vote", userVoteInVoteSessionEvent);
  }
}
