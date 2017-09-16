package test.com.solutionsiq.creditcard;

import com.solutionsiq.creditcard.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreditCardUnitTest {
    private String expectedResult;
    private String actualResult;
    private CreditCard creditCard;

    public CreditCardUnitTest(String cardNumber, String expectedResult) {
        try {
            creditCard = new CreditCard(cardNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.expectedResult = expectedResult;
        this.actualResult = creditCard.getCardIssuer();
    }

    @Parameterized.Parameters(name = "{index}: creditCard.getIssuer()({0} {1}")
    public static Iterable<Object[]> cardsToTest() {
        return Arrays.asList(new Object[][] {
                { "4024007194988563", "Visa"                        },
                { "5194781069295814", "MasterCard"                  },
                { "373367593257274",  "American Express"            },
                { "6011469410813225", "Discover"                    },
                { "3528867685167602", "JCB"                         },
                { "5489473291516225", "Diners Club - North America" },
                { "30037027840988",   "Diners Club - Carte Blanche" },
                { "36992186931263",   "Diners Club - International" },
                { "5020436825293112", "Maestro"                     },
                { "6709180359676047", "Laser"                       },
                { "4026530709288177", "Visa Electron"               },
                { "6395535700384360", "InstaPayment"                }
        });
    }

    @Test
    public void issuerChecker() {
        assertEquals(expectedResult, actualResult);
    }

}
