Feature: Checking the card count in the deck

  Scenario: Checking card count in deck after drawing one card from it
    Given a new shuffled deck with 6 deck
    When we drawing 1 cards from the deck
    Then the remaining card count in the deck 311

  Scenario: Checking card count in deck after drawing 2 cards from it
    Given a new shuffled deck with 6 deck
    When we drawing 2 cards from the deck
    Then the remaining card count in the deck 310


  Scenario: Checking card count in deck after drawing 5 cards from it
    Given a new shuffled deck with 6 deck
    When we drawing 5 cards from the deck
    Then the remaining card count in the deck 307



