package com.gra.worstmovieapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gra.worstmovieapp.controllers.entities.MinMaxWinningInterval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class AwardsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    void getMinAndMaxWinningInterval() throws Exception {

        var responseBody = mockMvc.perform(get("/awards/min-max-winning-interval"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        var mapper = new ObjectMapper();
        var resource = resourceLoader.getResource("classpath:controller/awards/get-min-max-winning-interval.json");

        var actual = mapper.readValue(responseBody, MinMaxWinningInterval.class);
        var expected = mapper.readValue(resource.getFile(), MinMaxWinningInterval.class);

        assertThat(actual)
        .isEqualTo(expected);
    }
}