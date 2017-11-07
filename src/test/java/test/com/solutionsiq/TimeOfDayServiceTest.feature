Feature: Time of day formatted as spoken words

  Scenario Outline: Check Spoken Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see spoken formatting of "<result>"

    Examples:
      |hour|minute|second|result                                   |
      |9   |3     |0     |a little after nine in the morning       |
      |20  |4     |45    |a little after eight in the evening      |
      |10  |11    |12    |about ten after ten in the morning       |
      |22  |12    |12    |about ten after ten in the evening       |
      |10  |14    |35    |about a quarter after ten in the morning |
      |5   |21    |18    |about twenty after five at night         |
      |23  |25    |25    |almost half past eleven in the evening   |
      |15  |31    |31    |about half past three in the afternoon   |
      |9   |34    |33    |almost twenty before ten in the morning  |
      |21  |46    |0     |about a quarter of ten in the evening    |
      |11  |51    |10    |about ten of twelve in the morning       |