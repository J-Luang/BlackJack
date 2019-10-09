package com.example.blackjack;

import java.util.*;

public class Player
{
    Hand hand;
    boolean isPlayerDone;
    Deck deck;

    Player(Deck deck)
    {
        hand = new Hand();
        isPlayerDone = false;
        this.deck = deck;
    }

    public void drawCards(int numCards)
    {
        for(int cardNum = 0; cardNum < numCards; cardNum++)
        {
            hand.add(this.deck.dealCard());
        }
    }

    public void discardHand()
    {
        int handSize = hand.size();
        for(int i = 0; i < handSize; i++)
        {
            this.deck.discardCard(hand.discard());
        }
    }

    public int calculateBlackjackHandValue()
    {
        return hand.value();
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

    public Hand getHand()
    {
        return hand;
    }
}
