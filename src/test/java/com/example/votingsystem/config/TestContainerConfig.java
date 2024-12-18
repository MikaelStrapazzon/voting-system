package com.example.votingsystem.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfig {

  @Bean
  @ServiceConnection(name = "kafka")
  public KafkaContainer kafkaContainer(final DynamicPropertyRegistry registry) {
    KafkaContainer kafkaContainer =
        new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));

    registry.add("spring.cloud.stream.kafka.binder.brokers", kafkaContainer::getBootstrapServers);

    return kafkaContainer;
  }

  @Bean
  @ServiceConnection(name = "oracle-db")
  public OracleContainer oracleContainer(final DynamicPropertyRegistry registry) {

    return new OracleContainer(
            DockerImageName.parse("gvenzl/oracle-free:23-slim")
                .asCompatibleSubstituteFor("gvenzl/oracle-xe"))
        .withDatabaseName("votingsystem")
        .withUsername("dev_db_user")
        .withPassword("dev_db_password")
        .withReuse(true);
  }
}
