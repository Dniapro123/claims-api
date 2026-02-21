package com.ulad.claims;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClaimControllerIT {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldCreateAndFetchClaim() throws Exception {
    // create
    String createJson = """
        {
          "title": "Broken laptop",
          "description": "Screen does not work",
          "amount": 1200
        }
        """;

    String createdBody = mvc.perform(post("/api/claims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.status").value("NEW"))
        .andReturn()
        .getResponse()
        .getContentAsString();

    JsonNode created = objectMapper.readTree(createdBody);
    long id = created.get("id").asLong();

    // get
    mvc.perform(get("/api/claims/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.title").value("Broken laptop"))
        .andExpect(jsonPath("$.status").value("NEW"));
  }

  @Test
  void shouldRejectInvalidStatusTransition() throws Exception {
    // create
    String createJson = """
        {
          "title": "Phone damage",
          "description": "Cracked screen",
          "amount": 200
        }
        """;

    String createdBody = mvc.perform(post("/api/claims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createJson))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    long id = objectMapper.readTree(createdBody).get("id").asLong();

    // invalid transition NEW -> APPROVED
    String updateJson = """
        { "status": "APPROVED" }
        """;

    mvc.perform(put("/api/claims/{id}/status", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateJson))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Invalid status transition: NEW -> APPROVED"));
  }
}