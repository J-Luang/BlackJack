package com.example.blackjack;
import android.util.Log;

import java.util.*;

public class Deck
{
    private List<Card> cards;
    private List<Card> discardedCards;

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
            for(Card.Suit cardSuit : Card.Suit.values())
            {
                for(Card.Rank cardRank : Card.Rank.values())
                {
                    cards.add(new Card(cardRank, cardSuit));
                }
            }
        }
        shuffle();
    }

    public Card dealCard()
    {
        if (!cards.isEmpty())
        {
            return cards.remove(0);
        }
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

    public void shuffle()
    {
        Collections.shuffle(cards);
    }
}
