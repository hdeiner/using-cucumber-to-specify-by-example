Feature: Credit cards

  Scenario Outline: Check Valid Credit Cards

    Given credit card digits of "<cardNumber>"
    Then the card issuer should be "<issuer>"

    Examples:
      |cardNumber      |issuer                     |
      |4024007194988563|Visa                       |
      |5194781069295814|MasterCard                 |
      |373367593257274 |American Express           |
      |6011469410813225|Discover                   |
      |3528867685167602|JCB                        |
      |5489473291516225|Diners Club - North America|
      |30037027840988  |Diners Club - Carte Blanche|
      |36992186931263  |Diners Club - International|
      |5020436825293112|Maestro                    |
      |6709180359676047|Laser                      |
      |4026530709288177|Visa Electron              |
      |6395535700384360|InstaPayment               |

  Scenario Outline: Check Invalid Credit Cards

    Given credit card digits of "<cardNumber>"
    Then the error message should be "<message>"

    Examples:
      |cardNumber      |message                                     |
      |4z24007194988563|Invalid credit card digits                  |
      |4124007194988563|Invalid Luhn validation                     |
      |402456789013    |Credit card digits are not from a known bank|