package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.entity.UserEntity;
import com.zurakin.codingriddles.models.entity.RiddleEntity;
import com.zurakin.codingriddles.models.entity.TestCaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RiddlesRepository riddlesRepository;
    @Autowired
    private TestCasesRepository testCasesRepository;

    @Test
    void testUserRepositoryCrud() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setPassword("password");
        user = userRepository.save(user);
        assertThat(user.getId()).isNotNull();
        assertThat(userRepository.findByUsername("testuser")).isPresent();
        userRepository.delete(user);
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void testRiddlesRepositoryCrud() {
        RiddleEntity riddle = new RiddleEntity();
        riddle.setTitle("Test Riddle");
        riddle.setDescription("Description");
        riddle.setCode("code");
        riddle.setValidationCode("validation");
        riddle = riddlesRepository.save(riddle);
        assertThat(riddle.getId()).isNotNull();
        List<RiddleEntity> riddles = riddlesRepository.findAll();
        assertThat(riddles).isNotEmpty();
        riddlesRepository.delete(riddle);
        assertThat(riddlesRepository.findById(riddle.getId())).isEmpty();
    }

    @Test
    void testTestCasesRepositoryCrud() {
        TestCaseEntity testCase = new TestCaseEntity();
        testCase.setInput("input");
        testCase.setOutput("output");
        testCase = testCasesRepository.save(testCase);
        assertThat(testCase.getId()).isNotNull();
        List<TestCaseEntity> testCases = testCasesRepository.findAll();
        assertThat(testCases).isNotEmpty();
        testCasesRepository.delete(testCase);
        assertThat(testCasesRepository.findById(testCase.getId())).isEmpty();
    }

    @Test
    void testRiddleWithTestCasesAndAuthor() {
        // Create and save author
        UserEntity author = new UserEntity();
        author.setUsername("author1");
        author.setPassword("pass");
        author = userRepository.save(author);

        // Create test cases
        TestCaseEntity tc1 = new TestCaseEntity();
        tc1.setInput("input1");
        tc1.setOutput("output1");
        TestCaseEntity tc2 = new TestCaseEntity();
        tc2.setInput("input2");
        tc2.setOutput("output2");

        // Create riddle
        RiddleEntity riddle = new RiddleEntity();
        riddle.setTitle("Complex Riddle");
        riddle.setDescription("desc");
        riddle.setCode("code");
        riddle.setValidationCode("valcode");
        riddle.setAuthor(author);
        riddle.setTestCases(List.of(tc1, tc2));

        // Save riddle (should cascade test cases)
        riddle = riddlesRepository.save(riddle);
        assertThat(riddle.getId()).isNotNull();
        assertThat(riddle.getAuthor()).isNotNull();
        assertThat(riddle.getAuthor().getUsername()).isEqualTo("author1");
        assertThat(riddle.getTestCases()).hasSize(2);
        assertThat(riddle.getTestCases().get(0).getInput()).isEqualTo("input1");
        assertThat(riddle.getTestCases().get(1).getOutput()).isEqualTo("output2");

        // Fetch from repository and check
        RiddleEntity fetched = riddlesRepository.findById(riddle.getId()).orElseThrow();
        assertThat(fetched.getAuthor().getUsername()).isEqualTo("author1");
        assertThat(fetched.getTestCases()).hasSize(2);
        assertThat(fetched.getTestCases().get(0).getInput()).isEqualTo("input1");
        assertThat(fetched.getTestCases().get(1).getOutput()).isEqualTo("output2");
    }
}
