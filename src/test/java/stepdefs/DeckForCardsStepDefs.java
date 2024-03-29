package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import org.junit.Assert;
import requester.DeckRequester;

import java.util.List;


@CucumberOptions(
        plugin = {"json:target/cucumber.json"}
)


public class DeckForCardsStepDefs {

    private DeckRequester deckRequester = new DeckRequester();
    private String deckId;
    private int initialCardCount;
    private List<String> drawnCards;

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

    @Given("a new shuffled deck with {int} decks containing only aces")
    public void create_new_shuffled_deck_with_aces(Integer deckCount) {
        deckId = deckRequester.createAceDeck(deckCount);
        initialCardCount = deckCount * 4;
    }

    @When("we drawing all cards from deck")
    public void draw_all_cards_from_aces_deck() {
        deckRequester.drawCardsFromDeck(deckId, initialCardCount);
    }

    @Then("all drawn cards should be aces")
    public void verify_all_drawn_cards_are_aces() {
        boolean allAces = deckRequester.verifyAllDrawnCardsAreAces(deckId);
        Assert.assertTrue("All drawn cards are aces", allAces);
    }

    @Given("a new shuffled {int} deck")
    public void create_new_shuffled_one_deck(Integer deckCount) {
        deckId = deckRequester.createShuffledDeck(deckCount);
        initialCardCount = deckRequester.getRemainingCardCount(deckId);
    }

    @When("we drawing 5 specific cards from the bottom of the deck")
    public void draw_specific_cards_from_bottom() {

        List<String> bottomCards = deckRequester.drawCardsFromBottom(deckId, 5);
        deckRequester.drawCardsFromDeck(deckId, 5);
        drawnCards = bottomCards;
    }

    @Then("the remaining card count in the deck should be {int}")
    public void verify_remaining_card_count_in_deck(int expectedRemainingCardCount) {
        int actualRemainingCardCount = deckRequester.getRemainingCardCount(deckId);
        Assert.assertEquals("Unexpected remaining card count", expectedRemainingCardCount, actualRemainingCardCount);

    }

    @Then("the drawn cards are no longer in the deck")
    public void verify_drawn_cards_in_deck() {
        if (drawnCards != null) {
            for (String card : drawnCards) {
                Assert.assertFalse("Drawn card in the deck", deckRequester.verifyCardInDeck(deckId, card));
            }
        }
    }
}

