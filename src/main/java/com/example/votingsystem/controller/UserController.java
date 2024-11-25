package com.example.votingsystem.controller;

import static com.example.votingsystem.controller.util.Endpoints.V1_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.votingsystem.dto.request.UserCreateRequest;
import com.example.votingsystem.dto.response.CreateUserResponse;
import com.example.votingsystem.dto.response.generic.SuccessResponse;
import com.example.votingsystem.mapper.controller.UserControllerMapper;
import com.example.votingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = V1_USER, produces = APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;
  private final UserControllerMapper userControllerMapper;

  @PostMapping
  public ResponseEntity<SuccessResponse<CreateUserResponse>> create(
      @RequestBody UserCreateRequest request) {
    var user = userService.save(userControllerMapper.userCreateRequestToUserCreateDto(request));

    return ResponseEntity.ok(
        new SuccessResponse<>(userControllerMapper.UserToCreateUserResponse(user)));
  }
}
