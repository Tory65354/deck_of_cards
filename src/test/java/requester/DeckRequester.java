package requester;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeckRequester {

    public String createShuffledDeck(int deckCount) {

        Response response = RestAssured.get("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=" + deckCount);
        return response.jsonPath().getString("deck_id");
    }

    public void drawCardsFromDeck(String deckId, int count) {
        RestAssured.get("https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count" + count);
    }

    public int getRemainingCardCount(String deckId) {
        Response response = RestAssured.get("https://deckofcardsapi.com/api/deck/" + deckId + "/");
        return response.jsonPath().getInt("remaining");
    }
}
