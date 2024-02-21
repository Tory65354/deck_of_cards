package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import requester.DeckRequester;

public class CardCountStepDef {

    private DeckRequester deckRequester;
    private String deckId;
    private int initialCardCount;

    public CardCountStepDef(DeckRequester deckRequester) {
        this.deckRequester = deckRequester;
    }

    @Given("a new shuffled deck with {int} deck")
    public void create_new_shuffled_deck(Integer deckCount) {
        deckId = deckRequester.createShuffledDeck(deckCount);
        initialCardCount = deckRequester.getRemainingCardCount(deckId);
    }

    @When("we drawing {int} cards from the deck")
    public void draw_one_card_from_deck(Integer cardCount) {
        deckRequester.drawCardsFromDeck(deckId, cardCount);
    }

    @Then("the remaining card count in the deck {int}")
    public void verify_remaining_card_count(int expectedRemainingCardCount) {
        int actualRemainingCardCount = deckRequester.getRemainingCardCount(deckId);
        Assert.assertEquals("Unexpected remaining card count", expectedRemainingCardCount, actualRemainingCardCount);
    }
}
