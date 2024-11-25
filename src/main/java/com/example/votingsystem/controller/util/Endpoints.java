package com.example.votingsystem.controller.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Endpoints {
  public static final String V1_USER = "/v1/user";
  public static final String V1_VOTE_SESSION = "/v1/vote-session";
  public static final String V1_VOTE_SESSION_OPEN = "/open";
  public static final String V1_VOTE_SESSION_NEW_USER_VOTE = "/{sessionId}/vote/{userId}";
}
