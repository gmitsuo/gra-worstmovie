package com.gra.worstmovieapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gra.worstmovieapp.controllers.entities.MovieBody;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    void getMovie() throws Exception {

        var resource = resourceLoader.getResource("classpath:controller/movie/get-movie.json");
        var expected = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

        mockMvc.perform(get("/movie/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
    }

    @Test
    void getMovieNotFound() throws Exception {

        mockMvc.perform(get("/movie/9999"))
        .andExpect(status().isNotFound());
    }

    @Test
    void createMovie() throws Exception {

        var resource = resourceLoader.getResource("classpath:controller/movie/create-movie.json");
        var requestBody = StreamUtils.copyToByteArray(resource.getInputStream());

        var responseBody = mockMvc.perform(post("/movie")
            .contentType(APPLICATION_JSON)
            .content(requestBody)
        )
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

        assertThat(new ObjectMapper().readValue(responseBody, MovieBody.class))
        .matches(actual ->
            actual.getId() != null &&
            actual.getYear().equals(2000) &&
            actual.getTitle().equals("Just A Movie Title") &&
            actual.getStudios().equals(List.of("A Studio")) &&
            actual.getProducers().equals(List.of("John Doe"))
        );
    }

    @Test
    void deleteMovieShouldReturnDeletedMovie() throws Exception {

        var responseBody = mockMvc.perform(delete("/movie/2"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        var movieBody = new ObjectMapper().readValue(responseBody, MovieBody.class);

        assertThat(movieBody).matches(actual ->
            actual.getId().equals(2L) &&
            actual.getYear().equals(1980) &&
            actual.getTitle().equals("Cruising") &&
            actual.getStudios().equals(List.of("Lorimar Productions","United Artists")) &&
            actual.getProducers().equals(List.of("Jerry Weintraub")) &&
            !actual.isWinner()
        );

        mockMvc.perform(delete("/movie/2"))
        .andExpect(status().isNoContent());
    }

    @Test
    void createMovieWithoutYearShouldGiveBadRequest() throws Exception {

        var mapper = new ObjectMapper();
        var movieBody = new MovieBody(null, null, "Movie Title", List.of("Studio"), List.of("Producer"), false);

        mockMvc.perform(post("/movie")
            .contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(movieBody))
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void createMovieWithoutTitleShouldGiveBadRequest() throws Exception {

        var mapper = new ObjectMapper();
        var movieBody = new MovieBody(null, 2002, null, List.of("Studio"), List.of("Producer"), false);

        mockMvc.perform(post("/movie")
            .contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(movieBody))
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void createMovieWithoutStudioShouldGiveBadRequest() throws Exception {

        var mapper = new ObjectMapper();
        var movieBody = new MovieBody(null, 2002, "Movie Title", null, List.of("Producer"), false);

        mockMvc.perform(post("/movie")
            .contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(movieBody))
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void createMovieWithoutProducerShouldGiveBadRequest() throws Exception {

        var mapper = new ObjectMapper();
        var movieBody = new MovieBody(null, 2002, "Movie Title", List.of("Studio"), null, false);

        mockMvc.perform(post("/movie")
            .contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(movieBody))
        )
        .andExpect(status().isBadRequest());
    }
}