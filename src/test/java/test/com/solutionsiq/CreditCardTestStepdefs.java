package test.com.solutionsiq;

import com.solutionsiq.creditcard.CreditCard;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreditCardTestStepdefs {

    private CreditCard creditCard;
    private String exceptionMessage;

    @Given("^credit card digits of \"([^\"]*)\"$")
    public void credit_card_digits_of(String cardNumber)  {
        try {
            creditCard = new CreditCard(cardNumber);
        } catch (Exception e) {
            exceptionMessage = e.getMessage();
        }
    }

    @Then("^the card issuer should be \"([^\"]*)\"$")
    public void the_card_issuer_should_be(String issuer)  {
        assertThat(creditCard.getCardIssuer(),is(issuer));
    }

    @Then("^the error message should be \"([^\"]*)\"$")
    public void the_error_message_should_be(String errorMessage) {
        assertThat(exceptionMessage,is(errorMessage));
    }
}
