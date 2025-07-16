package com.zurakin.codingriddles;

import com.zurakin.codingriddles.controller.RiddlesController;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import com.zurakin.codingriddles.service.JwtService;
import com.zurakin.codingriddles.service.RiddlesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
class CodingRiddlesApplicationTests {

    @Autowired
    private RiddlesController riddleController;


    @Autowired
    private RiddlesService riddleService;

    @Autowired
    private RiddlesRepository riddleRepository;

    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {
        // Check if the application starts correctly
    }

    @Test
    void riddlesControllerBeanShouldBeLoaded() {
        assertThat(riddleController).isNotNull();
    }

    @Test
    void serviceBeanShouldBeLoaded() {
        assertThat(riddleService).isNotNull();
    }

    @Test
    void repositoryBeanShouldBeLoaded() {
        assertThat(riddleRepository).isNotNull();
    }

    @Test
    void jwtServiceBeanShouldBeLoaded() {
        assertThat(jwtService).isNotNull();
    }
}
