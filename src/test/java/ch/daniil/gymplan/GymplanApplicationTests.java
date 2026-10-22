package ch.daniil.gymplan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GymplanApplicationTests {

    @Autowired(required = false)
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        System.out.println("DEBUG objectMapper bean = " + objectMapper);
    }

}

