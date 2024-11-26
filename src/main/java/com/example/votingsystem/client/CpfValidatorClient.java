package com.example.votingsystem.client;

import com.example.votingsystem.client.dto.response.ValidCpfDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CpfValidatorClient {

  private RestClient restClient;

  @Value("${app.cpf-validator.url}")
  private String BASE_URL;

  @Value("${app.cpf-validator.token}")
  private String TOKEN;

  @PostConstruct
  public void init() {
    this.restClient = RestClient.builder().baseUrl(BASE_URL).build();
  }

  public ValidCpfDto validateCpf(final String cpf) {
    try {
      return restClient
          .get()
          .uri(
              uriBuilder ->
                  uriBuilder
                      .path("/validator")
                      .queryParam("token", TOKEN)
                      .queryParam("value", cpf)
                      .build())
          .retrieve()
          .body(ValidCpfDto.class);
    } catch (Exception e) {
      throw new RuntimeException("Error validating CPF, please try again later");
    }
  }
}
