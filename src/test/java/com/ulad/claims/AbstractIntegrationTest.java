package com.ulad.claims;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
// Abstract base class for integration tests in the Claims API application.
public abstract class AbstractIntegrationTest {
}