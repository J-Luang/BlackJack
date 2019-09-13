package com.example.blackjack;

public class Card
{
    private int cardValue;
    private Suit cardSuit;

    public Card(Suit suit, int card)
    {
        this.cardSuit = suit;
        this.cardValue = card;
    }

    public String getSuitString()
    {
        if (cardSuit == Suit.Club)
        {
            return "Club";
        }
        else if (cardSuit == Suit.Diamond)
        {
            return "Diamond";
        }
        else if (cardSuit == Suit.Spade)
        {
            return "Spade";
        }
        else if (cardSuit == Suit.Heart)
        {
            return "Heart";
        }
        else
            {
                throw new RuntimeException("Cannot find suit");
            }
    }
}
