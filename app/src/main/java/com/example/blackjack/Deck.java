package com.example.blackjack;
import java.util.*;

public class Deck
{
    private List<Card> cards;
    private List<Card> discardedCards;
    private final static String[] CARD_SUITS = {"c", "d", "h", "s"};
    private final static String[] CARD_VALUES = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k"};

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

    public Card dealCard()
    {
        if (!cards.isEmpty()) return cards.remove(0);
        else
        {
            addDiscardsToDeck();
            return dealCard();
        }
    }

    public void discardCard(Card cardToReturn)
    {
        discardedCards.add(cardToReturn);
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
