package com.example.votingsystem.controller.VoteSessionControllerTest;

import static com.example.votingsystem.controller.util.Endpoints.V1_VOTE_SESSION;
import static com.example.votingsystem.controller.util.Endpoints.V1_VOTE_SESSION_OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.votingsystem.config.TestConfig;
import com.example.votingsystem.dto.request.OpenNewVoteSessionRequest;
import com.example.votingsystem.dto.response.OpenNewVoteSessionResponse;
import com.example.votingsystem.dto.response.generic.ErrorDetailsFieldResponse;
import com.example.votingsystem.dto.response.generic.SuccessResponse;
import com.example.votingsystem.util.dto.ResponseMockMvcRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class OpenNewVoteSessionTest extends TestConfig {

  @Test
  void openNewVoteSession_thenReturnNewOpenVoteSession() throws Exception {

    var requestBody =
        new OpenNewVoteSessionRequest("Election Title", "Description of the vote session", 10);

    var request = executeRequestVoteSessionOpen_valid(requestBody);

    assertEquals(200, request.httpStatusCode());

    var responseDto = request.data().data();
    assertNotNull(responseDto);
    assertEquals(requestBody.title(), responseDto.title());
    assertEquals(requestBody.description(), responseDto.description());

    assertNotNull(responseDto.startTime());
    assertNotNull(responseDto.endTime());

    assertEquals(
        10, java.time.Duration.between(responseDto.startTime(), responseDto.endTime()).toMinutes());
  }

  @Test
  void openNewVoteSession_withDefaultDuration_thenReturnNewOpenVoteSession() throws Exception {

    var requestBody =
        new OpenNewVoteSessionRequest("Election Title", "Description of the vote session", null);

    var request = executeRequestVoteSessionOpen_valid(requestBody);

    assertEquals(200, request.httpStatusCode());

    var responseDto = request.data().data();
    assertNotNull(responseDto);
    assertEquals(requestBody.title(), responseDto.title());
    assertEquals(requestBody.description(), responseDto.description());

    assertNotNull(responseDto.startTime());
    assertNotNull(responseDto.endTime());

    assertEquals(
        60, java.time.Duration.between(responseDto.startTime(), responseDto.endTime()).toMinutes());
  }

  @Test
  void openNewVoteSession_withDurationError_thenReturnError() throws Exception {

    var requestBody =
        new OpenNewVoteSessionRequest("Election Title", "Description of the vote session", -1);

    var request = executeRequestVoteSessionOpen_invalid(requestBody);

    assertEquals(400, request.httpStatusCode());

    var responseDto = request.data();
    assertNotNull(responseDto);
    assertEquals("duration", responseDto.details().getFirst().field());
  }

  private ResponseMockMvcRequest<SuccessResponse<OpenNewVoteSessionResponse>>
      executeRequestVoteSessionOpen_valid(OpenNewVoteSessionRequest body) throws Exception {
    var response = executeRequestVoteSessionOpen(body);

    SuccessResponse<OpenNewVoteSessionResponse> parsedData = null;
    if (!response.getContentAsString().isEmpty()) {
      parsedData = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
    }

    return new ResponseMockMvcRequest<>(response.getStatus(), parsedData);
  }

  private ResponseMockMvcRequest<ErrorDetailsFieldResponse> executeRequestVoteSessionOpen_invalid(
      OpenNewVoteSessionRequest body) throws Exception {
    var response = executeRequestVoteSessionOpen(body);

    ErrorDetailsFieldResponse parsedData = null;
    if (!response.getContentAsString().isEmpty()) {
      parsedData = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
    }

    return new ResponseMockMvcRequest<>(response.getStatus(), parsedData);
  }

  private MockHttpServletResponse executeRequestVoteSessionOpen(OpenNewVoteSessionRequest body)
      throws Exception {
    return mockMvc
        .perform(
            MockMvcRequestBuilders.post(V1_VOTE_SESSION + V1_VOTE_SESSION_OPEN)
                .content(objectMapper.writeValueAsString(body))
                .contentType("application/json"))
        .andReturn()
        .getResponse();
  }
}
