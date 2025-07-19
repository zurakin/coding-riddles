Feature: FMEA

  Scenario: Happy path
    When I sign up with username "zura" and password "toor"
    Then the status should be 200
    When I log in using username "zura" and password "toor"
    Then the status should be 200
    Then the response should match the json "user_login_response.json"
    When I submit a new riddle using the json "riddle1.json"
    Then the status should be 200
    Then the response should match the json "submit_riddle1_response.json"
    When I list riddles
    Then the status should be 200
    Then the response should match the json "list_riddles1_response.json"
    When I get the riddle with id 1
    Then the status should be 200
    Then the response should match the json "get_riddle_1_response.json"
    When I sign out
    When I try to delete the riddle with id 1
    Then the status should be 403
    Then the response should match the json "delete_riddle_unauthorized_response.json"
    When I sign up with username "user1" and password "user1"
    When I log in using username "user1" and password "user1"
    When I try to delete the riddle with id 1
    Then the status should be 403
    Then the response should match the json "delete_riddle_unauthorized_response.json"
    When I sign out
    When I log in using username "zura" and password "toor"
    When I try to delete the riddle with id 1
    Then the status should be 200
    When I get the riddle with id 1
    Then the status should be 404
