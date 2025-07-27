Feature: FMEA

  # --- User zura happy path ---
  Scenario: Happy path
    # --- Sign up and log in as zura ---
    When I sign up with username "zura" and password "toor"
    Then the status should be 200
    When I log in using username "zura" and password "toor"
    Then the status should be 200
    Then the response should match the json "user_login_response.json"

    # --- Submit riddles as zura ---
    When I submit a new riddle using the json "riddle1.json"
    Then the status should be 200
    Then the response should match the json "submit_riddle1_response.json"
    When I submit a new riddle using the json "riddle2.json"
    Then the status should be 200
    Then the response should match the json "submit_riddle2_response.json"

    # --- List and get riddles ---
    When I list riddles
    Then the status should be 200
    Then the response should match the json "list_riddles_response.json"
    When I get the riddle with id 1
    Then the status should be 200
    Then the response should match the json "get_riddle_1_response.json"
    When I get the riddle with id 2
    Then the status should be 200
    Then the response should match the json "get_riddle_2_response.json"

    # --- User solves a riddle ---
    When I log in using username "zura" and password "toor"
    Then the status should be 200
    When I solve riddle with id 1 using code "print('solution')"
    Then the status should be 200
    When I get my solved riddles
    Then the status should be 200
    Then the response should match the json 'user1_solved_riddles.json'
    When I get the riddle with id 1
    Then the status should be 200
    Then the response should match the json 'get_riddle_1_response_solved.json'

    # --- Listing riddles after solving ---
    When I list riddles
    Then the status should be 200
    Then the response should match the json "list_riddles_solved_response.json"

    # --- Second user solves the same riddle ---
    When I sign up with username "user2" and password "user2"
    Then the status should be 200
    When I log in using username "user2" and password "user2"
    Then the status should be 200
    When I solve riddle with id 1 using code "print('user2 solution')"
    Then the status should be 200
    When I get the riddle with id 1
    Then the status should be 200
    Then the response should match the json 'get_riddle_1_response_solved_twice.json'

    # --- User can override their previous solution ---
    When I solve riddle with id 1 using code "print('user 2 second solution')"
    Then the status should be 200
    When I get my solved riddles
    Then the status should be 200
    Then the response should match the json 'user2_solved_riddles.json'
    When I get the riddle with id 1
    Then the status should be 200
    Then the response should match the json 'get_riddle_1_response_solved_twice.json'

    # --- Unauthorized delete attempts ---
    When I sign out
    When I try to delete the riddle with id 1
    Then the status should be 403
    Then the response should match the json "delete_riddle_unauthorized_response.json"

    # --- User1 attempts unauthorized delete ---
    When I sign up with username "user1" and password "user1"
    When I log in using username "user1" and password "user1"
    When I try to delete the riddle with id 1
    Then the status should be 403
    Then the response should match the json "delete_riddle_unauthorized_response.json"

    # --- zura deletes riddle ---
    When I sign out
    When I log in using username "zura" and password "toor"
    When I try to delete the riddle with id 1
    Then the status should be 200
    When I get the riddle with id 1
    Then the status should be 404
    When I get the riddle with id 2
    Then the status should be 200

    # --- Moderator role assignment and riddle deletion ---
    When I sign up with username "moderator" and password "moderator"
    When the user "moderator" is given the role "MODERATOR"
    When I log in using username "moderator" and password "moderator"
    When I try to delete the riddle with id 2
    Then the status should be 200
    When I get the riddle with id 2
    Then the status should be 404
