package com.example.blackjack;

public class Card
{
    private String value;
    private String suit;
    boolean faceUp;

    Card(String value, String suit)
    {
        this.suit = suit;
        this.value = value;
        faceUp = true;
    }

    public int getBlackJackValue()
    {
        switch (this.value) {
            case "a":
                return 11;
            case "j":
            case "q":
            case "k":
                return 10;
            default:
                return Integer.parseInt(this.value);
        }
    }

    public boolean checkIfSameValue(Card otherCard)
    {
        return otherCard.value == this.value;
    }

    public String toString()
    {
        String cardString = suit + value;
        return cardString;
    }

    public void turnFaceDown() {
        faceUp = false;
    }

    public void turnFaceup() {
        faceUp = true;
    }

    public boolean getFaceUp() {
        return faceUp;
    }
}