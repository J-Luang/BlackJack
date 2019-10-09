package com.example.blackjack;

import java.util.*;

public class Hand {
    private List<Card> cards;

    Hand()
    {
        cards = new ArrayList<>();
    }

    public void add(Card card)
    {
        cards.add(card);
    }

    public Card discard()
    {
        return cards.remove(0);
    }

    public int value()
    {
        if (numAces() == 0) return hardValue();
        else if (numAces() == 1)
        {
            if (softValue() > 21) return hardValue();
            else return softValue();
        }
        else if (numAces() > 1)
        {
            if (softValue() - 10 * (numAces() - 1) > 21) return hardValue();
            else return softValue() - 10 * (numAces() - 1);
        }
        else {
            return hardValue();
        }
    }

    private int softValue()
    {
        int softValue = 0;

        for (Card card : cards)
        {
            softValue += card.softValue();
        }

        return softValue;
    }

    private int hardValue()
    {
        int handValue = 0;

        for (Card card : cards)
        {
            handValue += card.hardValue();
        }

        return handValue;
    }

    private int numAces()
    {
        int numAces = 0;

        for(Card card : cards)
        {
            if(card.isAce()) numAces++;
        }

        return numAces;
    }

    public List<Card> getCards()
    {
        return cards;
    }

    public int size()
    {
        return cards.size();
    }

    public Card get(int index)
    {
        return cards.get(index);
    }

    //For testing purposes only
    public void setHand(List<Card> cards)
    {
        this.cards = cards;
    }
}
