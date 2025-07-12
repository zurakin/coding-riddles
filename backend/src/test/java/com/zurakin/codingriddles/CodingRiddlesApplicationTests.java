package com.zurakin.codingriddles;

import com.zurakin.codingriddles.controller.RiddlesController;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import com.zurakin.codingriddles.service.RiddlesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CodingRiddlesApplication.class)
class CodingRiddlesApplicationTests {

    @Autowired
    private RiddlesController riddleController;


    @Autowired
    private RiddlesService riddleService;

    @Autowired
    private RiddlesRepository riddleRepository;

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
}
