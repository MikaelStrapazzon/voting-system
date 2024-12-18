package infra.redis;

import com.example.votingsystem.dto.enums.PagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagesRedisTopic {
  private final String pagePrefixTopic = "page-configurations:";

  private final StringRedisTemplate redisTemplate;

  public String getPage(PagesEnum page) {
    return redisTemplate.opsForValue().get(pagePrefixTopic + page);
  }

  public boolean exists(PagesEnum page) {
    return redisTemplate.hasKey(pagePrefixTopic + page);
  }

  public void save(PagesEnum page, String content) {
    redisTemplate.opsForValue().set(pagePrefixTopic + page, content);
  }
}
