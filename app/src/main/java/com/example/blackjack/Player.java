package com.example.blackjack;

import java.util.*;

public class Player
{
    List<Card> hand;
    Deck deck;
    boolean isPlayerDone;

    Player(Deck deck)
    {
        hand = new ArrayList<>();
        this.deck = deck;
        isPlayerDone = false;
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

    private int calculateBlackjackHandValue()
    {
        int handValue = 0;

        for (Card card : hand)
        {
            handValue += card.getBlackJackValue();
        }

        return handValue;
    }

    public void checkIfPlayerBusts()
    {
        if(calculateBlackjackHandValue() > 21)
        {
            isPlayerDone = true;
        }
    }

    public void playerStand()
    {
        isPlayerDone = true;
    }

    public List<Card> getHand()
    {
        return hand;
    }
}
