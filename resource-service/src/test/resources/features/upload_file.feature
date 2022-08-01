Feature: Upload file
  As a user
  I want save file and its metadata and get file by resource id
  Scenario: Upload file
    Given new file
    When user uploads file
    Then returned resource id
    And user can get file by resource id
