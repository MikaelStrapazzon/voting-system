package com.example.votingsystem.service;

import infra.redis.enums.PagesEnum;
import java.util.Optional;

public interface PagesService {
  String getPageById(PagesEnum page);

  Optional<String> findPageById(PagesEnum page);
}
