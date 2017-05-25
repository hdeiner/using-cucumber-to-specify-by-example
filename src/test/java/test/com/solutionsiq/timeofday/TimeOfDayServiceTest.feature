Feature: Time of day formatted as spoken words

  Scenario Outline: Check Spoken Formatted

    When the hour is "<hour>"
    And the minute is "<minute>"
    And the second is "<second>"
    And I should see spoken formatting of "<result>"

    Examples:
      |hour|minute|second|result                                 |
      |9   |0     |0     |nine in the morning                    |
      |20  |4     |45    |almost ten after eight in the evening  |
      |10  |11    |12    |ten after ten in the morning           |
      |22  |12    |12    |ten after ten in the evening           |
      |10  |14    |35    |a quarter after ten in the morning     |
      |5   |21    |18    |twenty after five at night             |
      |23  |25    |25    |almost half past eleven in the evening |
      |15  |31    |31    |half past three in the afternoon       |
      |9   |34    |33    |almost twenty before ten in the morning|
      |21  |46    |0     |a quarter of ten in the evening        |
      |11  |51    |10    |ten of twelve in the morning           |