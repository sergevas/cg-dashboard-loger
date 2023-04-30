package dev.sergevas.cg.grower.device;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static dev.sergevas.cg.grower.device.CgGrowerDeviceRpiApplication.buildApp;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CgGrowerDeviceRpiApplicationTests {

    private ConfigurableApplicationContext applicationContext;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        applicationContext = buildApp().run();
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @AfterEach
    void tearDown() {
        applicationContext.close();
    }

    @Test
    void contextLoads() {
    }

}
