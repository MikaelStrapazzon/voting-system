package com.example.votingsystem.controller;

import static com.example.votingsystem.controller.util.Endpoints.V1_PAGES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.votingsystem.dto.response.generic.SuccessResponse;
import com.example.votingsystem.service.PagesService;
import infra.redis.enums.PagesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = V1_PAGES, produces = APPLICATION_JSON_VALUE)
public class PagesController {

  private final PagesService pagesService;

  @GetMapping(value = "/{page}")
  public ResponseEntity<SuccessResponse<String>> getPage(@PathVariable PagesEnum page) {
    return ResponseEntity.ok(new SuccessResponse<>(pagesService.getPageById(page)));
  }
}
