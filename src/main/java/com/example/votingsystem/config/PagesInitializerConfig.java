package com.example.votingsystem.config;

import com.example.votingsystem.dto.enums.PagesEnum;
import infra.redis.PagesRedisTopic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PagesInitializerConfig {
  private final PagesRedisTopic pagesRedisTopic;

  @Bean
  public ApplicationRunner initializeRedis() {
    return args -> {
      for (PagesEnum topic : PagesEnum.values()) {
        String topicName = topic.name();
        String jsonFileName = topic.getJsonFileName();

        if (Boolean.TRUE.equals(pagesRedisTopic.exists(topic))) {
          System.out.println("Topic '" + topicName + "' already loaded");
          continue;
        }

        String jsonContent = loadJsonFile(jsonFileName);
        if (jsonContent != null) {
          pagesRedisTopic.save(topic, jsonContent);
          LOGGER.info("Topic '{}' initialized with content:{}", topicName, jsonContent);
        } else {
          LOGGER.error("Error initializing JSON for topic '{}'", topicName);
        }
      }
    };
  }

  private String loadJsonFile(String fileName) {
    try {
      Path jsonPath = new ClassPathResource("pages/" + fileName).getFile().toPath();
      return Files.readString(jsonPath);
    } catch (IOException e) {
      System.err.println("Erro ao ler arquivo JSON: " + fileName + " - " + e.getMessage());
      return null;
    }
  }
}
