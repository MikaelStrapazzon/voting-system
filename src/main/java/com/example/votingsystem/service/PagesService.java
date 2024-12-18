package com.example.votingsystem.service;

import com.example.votingsystem.dto.enums.PagesEnum;

import java.util.Optional;

public interface PagesService {
  String getPageById(PagesEnum page);

  Optional<String> findPageById(PagesEnum page);
}
