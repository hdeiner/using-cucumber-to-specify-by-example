Feature: Time of day, formated as text and formatted as spoken words

  Scenario Outline: Check Standard Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see standard formatting of "<result>"

    Examples:
      |hour|minute|second|result  |
      |10  |11    |12    |10:11:12|
      |23  |0     |5     |23:00:05|

  Scenario Outline: Check Spoken Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see spoken formatting of "<result>"

    Examples:
      |hour|minute|second|result                                        |
      |9   |0     |0     |nine o'clock in the morning                   |
      |10  |11    |12    |ten after ten o'clock in the morning          |
      |10  |14    |35    |a quarter after ten o'clock in the morning    |
      |22  |44    |35    |a quarter before eleven o'clock in the evening|

  Scenario: Close up Spring

    When I reach here, I'm done