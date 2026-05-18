package com.ulad.claims;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc

// Integration test class for the CustomerController. This class tests the creation and retrieval of customers, as well as validating email uniqueness and input validation.
class CustomerControllerIT extends AbstractIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    // Test case to verify that a customer can be created and retrieved successfully.
    void shouldCreateAndFetchCustomer() throws Exception {
        String email = "john.doe." + UUID.randomUUID() + "@example.com";

        String createJson = """
            {
              "firstName": "John",
              "lastName": "Doe",
              "email": "%s",
              "phone": "+48123123123"
            }
            """.formatted(email);
    // Perform a POST request to create a new customer and verify the response contains the expected data.
        String createdBody = mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value(email))
                .andReturn()
                .getResponse()
                .getContentAsString();
        // Parse the created customer response to extract the customer ID for subsequent retrieval.
        JsonNode created = objectMapper.readTree(createdBody);
        long id = created.get("id").asLong();

        mvc.perform(get("/api/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    /// Test case to verify that creating a customer with a duplicate email address results in a Bad Request response.
    void shouldRejectDuplicateEmail() throws Exception {
        String email = "duplicate.customer." + UUID.randomUUID() + "@example.com";

        String createJson = """
            {
              "firstName": "Alice",
              "lastName": "Smith",
              "email": "%s",
              "phone": null
            }
            """.formatted(email);

        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated());

        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Customer with email already exists: " + email));
    }



    @Test
    /// Test case to verify that creating a customer with invalid input data results in a Bad Request response with appropriate validation error messages.
    void shouldRejectInvalidCustomerRequest() throws Exception {
        String invalidJson = """
            {
            "firstName": "",
            "lastName": "",
            "email": "not-an-email",
            "phone": "123"
            }
            """;

        mvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/api/customers"))
                .andExpect(jsonPath("$.fieldErrors").isArray());
    }
}