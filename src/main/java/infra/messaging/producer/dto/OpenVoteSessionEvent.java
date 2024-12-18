package infra.messaging.producer.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OpenVoteSessionEvent {
  private Integer id;
  private String title;
  private String description;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
}
