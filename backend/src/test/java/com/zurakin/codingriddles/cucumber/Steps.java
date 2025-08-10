package com.zurakin.codingriddles.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Steps {
    @Autowired
    private com.zurakin.codingriddles.repository.UserRepository userRepository;

    @When("the user {string} is given the role {string}")
    public void the_user_is_given_the_role(String username, String role) {
        com.zurakin.codingriddles.models.entity.UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        user.setRole(com.zurakin.codingriddles.models.entity.RoleEntity.valueOf(role));
        userRepository.save(user);
    }
    @When("I try to delete the riddle with id {int}")
    public void i_try_to_delete_the_riddle_with_id(int id) {
        String url = "/api/riddles/" + id;
        HttpEntity<String> entity = new HttpEntity<>(null, bearerHeaders());
        lastResponse = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
    }

    @Then("the status should not be {int}")
    public void the_status_should_not_be(int status) {
        assertThat(lastResponse.getStatusCode().value()).isNotEqualTo(status);
    }
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> lastResponse;
    private String token;

    @When("I submit a new riddle using the json {string}")
    public void i_submit_a_new_riddle_using_the_json(String jsonFile) throws Exception {
        String riddleJson = TestUtils.readResource(jsonFile);
        String riddleUrl = "/api/riddles";
        HttpHeaders headers = bearerHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> riddleRequest = new HttpEntity<>(riddleJson, headers);
        lastResponse = restTemplate.postForEntity(riddleUrl, riddleRequest, String.class);
    }

    @When("I sign up with username {string} and password {string}")
    public void i_sign_up_with_username_and_password(String username, String password) {
        String signupUrl = "/auth/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String signupBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        HttpEntity<String> signupRequest = new HttpEntity<>(signupBody, headers);
        lastResponse = restTemplate.postForEntity(signupUrl, signupRequest, String.class);
    }

    @When("I log in using username {string} and password {string}")
    public void i_log_in_using_username_and_password(String username, String password) throws JsonProcessingException {
        String loginUrl = "/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String loginBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        HttpEntity<String> loginRequest = new HttpEntity<>(loginBody, headers);
        lastResponse = restTemplate.postForEntity(loginUrl, loginRequest, String.class);
        JsonNode loginNode = new ObjectMapper().readTree(lastResponse.getBody());
        if (loginNode.has("token")) {
            token = loginNode.get("token").asText();
        }
    }

    @Then("the status should be {int}")
    public void the_status_should_be(int status) {
        assertThat(lastResponse.getStatusCode().value()).isEqualTo(status);
    }

    @Then("the response should match the json {string}")
    public void the_response_should_match_the_json(String jsonFile) throws Exception {
        String expected = TestUtils.readResource(jsonFile);
        JsonAssert.assertJsonEquals(lastResponse.getBody(), expected);
    }

    @When("I list riddles")
    public void i_list_riddles() {
        String url = "/api/riddles";
        HttpEntity<String> entity = new HttpEntity<>(null, bearerHeaders());
        lastResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @When("I get the riddle with id {int}")
    public void i_get_the_riddle_with_id(int id) {
        String url = "/api/riddles/" + id;
        HttpEntity<String> entity = new HttpEntity<>(null, bearerHeaders());
        lastResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @When("I solve riddle with id {int} using code {string}")
    public void i_solve_riddle_with_id_using_code(int riddleId, String code) throws JsonProcessingException {
        String url = "/api/riddle-solutions";
        HttpHeaders headers = bearerHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"riddleId\":%d,\"answerCode\":%s}", riddleId, new ObjectMapper().writeValueAsString(code));
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        lastResponse = restTemplate.postForEntity(url, entity, String.class);
    }

    @When("I get my solved riddles")
    public void i_get_my_solved_riddles() {
        String url = "/api/riddle-solutions";
        HttpEntity<String> entity = new HttpEntity<>(null, bearerHeaders());
        lastResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @When("I get the leaderboard")
    public void i_get_the_leaderboard() {
        String url = "/api/user/leaderboard";
        HttpEntity<String> entity = new HttpEntity<>(null, bearerHeaders());
        lastResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private HttpHeaders bearerHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    @When("I sign out")
    public void iSignOut() {
        token = null;
    }

    @When("I check the health of the service")
    public void iCheckTheHealthOfTheService() {
        String url = "/api/health";
        HttpEntity<String> entity = new HttpEntity<>(null, bearerHeaders());
        lastResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}
