package com.example.blackjack;

import java.util.*;

public class Deck
{
    private List<Card> cards;
    private List<Card> discardedCards;
    private final static String[] CARD_SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private final static String[] CARD_VALUES = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

    public Deck()
    {
        this(1);
    }

    public Deck(int numDecks)
    {
        cards = new ArrayList<>();
        discardedCards = new ArrayList<>();

        for(int deckNum = 0; deckNum < numDecks; deckNum++)
        {
            for(String cardSuit : CARD_SUITS)
            {
                for(String cardValue : CARD_VALUES)
                {
                    cards.add(new Card(cardValue, cardSuit));
                }
            }
        }
    }

    public List<Card> dealCards(int numCards)
    {
        List<Card> cardsToDeal = new ArrayList<>();

        for(int cardNum = 0; cardNum < numCards; cardNum++)
        {
            if(!cards.isEmpty()) cardsToDeal.add(cards.remove(0));
            else addDiscardsToDeck();
        }

        return cardsToDeal;
    }

    public void discardCards(List<Card> cardsToReturn)
    {
        for(Card card : cardsToReturn)
        {
            discardedCards.add(card);
        }
    }

    public void addDiscardsToDeck()
    {
        for(Card card : discardedCards)
        {
            cards.add(card);
        }
        shuffle();
    }

    private void shuffle()
    {

    }
}
