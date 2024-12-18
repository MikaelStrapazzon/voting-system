package com.example.votingsystem.service.impl;

import com.example.votingsystem.dto.enums.PagesEnum;
import com.example.votingsystem.exception.custom.NotFoundException;
import com.example.votingsystem.service.PagesService;
import infra.redis.PagesRedisTopic;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PagesServiceImpl implements PagesService {

  private final PagesRedisTopic pagesRedisTopic;

  @Override
  public String getPageById(PagesEnum page) {
    return findPageById(page).orElseThrow(() -> new NotFoundException("Page not found"));
  }

  @Override
  public Optional<String> findPageById(PagesEnum page) {
    return Optional.ofNullable(pagesRedisTopic.getPage(page));
  }
}
