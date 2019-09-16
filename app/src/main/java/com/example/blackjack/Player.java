package com.example.blackjack;

import java.util.*;

public class Player
{
    List<Card> hand;
    Deck deck;

    Player(Deck deck)
    {
        hand = new ArrayList<>();
        this.deck = deck;
    }

    public void drawCards(int numCards)
    {
        for(int cardNum = 0; cardNum < numCards; cardNum++)
        {
            hand.add(deck.dealCard());
        }
    }

    public void discardHand()
    {
        int handSize = hand.size();
        for(int i = 0; i < handSize; i++)
        {
            deck.discardCard(hand.remove(0));
        }
    }

    public List<Card> getHand()
    {
        return hand;
    }
}
