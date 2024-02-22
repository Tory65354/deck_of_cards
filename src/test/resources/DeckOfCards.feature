Feature: Checking the card count in the deck and card types

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

    Scenario: Creating a new deck containing only Aces and validating that player can only get aces from it
      Given a new shuffled deck with 6 decks containing only aces
      When we drawing all cards from deck
      Then all drawn cards should be aces



