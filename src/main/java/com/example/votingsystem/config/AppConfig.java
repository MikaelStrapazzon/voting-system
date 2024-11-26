package com.example.votingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Configuration
public class AppConfig {

  @Bean
  RestClient restClient(RestClient.Builder builder) {
    final var jdkClientHttpRequestFactory = new JdkClientHttpRequestFactory();
    jdkClientHttpRequestFactory.setReadTimeout(40_000);
    return builder
        .messageConverters(
            httpMessageConverters ->
                httpMessageConverters.add(getMappingJackson2HttpMessageConverter()))
        .requestFactory(jdkClientHttpRequestFactory)
        .build();
  }

  private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Collections.singletonList(APPLICATION_FORM_URLENCODED));
    return converter;
  }
}
