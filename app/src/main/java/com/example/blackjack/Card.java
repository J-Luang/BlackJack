package com.example.blackjack;

public class Card
{
    private String value;
    private String suit;

    Card(String value, String suit)
    {
        this.suit = suit;
        this.value = value;
    }

    public int getBlackJackValue()
    {
        switch(this.value)
        {
            case "Ace":
                return 11;
            case "Jack":
            case "Queen":
            case "King":
                return 10;
            default:
                return 	Integer.parseInt(this.value);
        }
    }

    public String toString()
    {
        return value + " of " + suit + "";
    }
}
