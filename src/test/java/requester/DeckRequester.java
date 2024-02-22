package requester;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

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

    public String createAceDeck(int deckCount) {
        StringBuilder cards = new StringBuilder();
        for (int i = 0; i < deckCount; i++) {
            if (i > 0) {
                cards.append(",");
            }
            cards.append("AS,AD,AC,AH");
        }
        Response response = RestAssured.get("https://deckofcardsapi.com/api/deck/new/shuffle/?cards=" + cards.toString());
        return response.jsonPath().getString("deck_id");
    }

    public boolean verifyAllDrawnCardsAreAces(String deckId) {
        Response response = RestAssured.get("https://deckofcardsapi.com/api/deck/" + deckId + "/pile/discard/list/");
        List<Map<String, String>> drawnCards = response.jsonPath().getList("piles.discard.cards");

        for (Map<String, String> card : drawnCards) {
            String value = card.get("value");
            if (!value.equals("ACE")) {
                return false;
            }
        }
        return true;
    }
}

