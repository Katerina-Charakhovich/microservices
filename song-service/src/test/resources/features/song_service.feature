Feature: Song Service

  Scenario: Add song
    Given new song
    When song is uploaded
    Then save song
    And return song id

  Scenario: Get song by id
    Given existed song
    When user requests song by id
    Then return song

  Scenario: Delete song by ids
    Given existed songs
    When user requests delete songs
    Then return deleted songs ids