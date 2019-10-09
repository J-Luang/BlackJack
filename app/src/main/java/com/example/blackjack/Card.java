package com.example.blackjack;

import androidx.annotation.NonNull;

public class Card
{
    enum Suit
    {
        Clubs("c"), Diamonds("d"), Hearts("h"), Spades("s");

        private String suit;

        Suit(String suit)
        {
            this.suit = suit;
        }

        @NonNull
        @Override
        public String toString()
        {
            return this.suit;
        }
    }

    enum Rank
    {
        Ace("a"), Two("2"), Three("3"), Four("4"), Five("5"), Six("6"), Seven("7"),
        Eight("8"), Nine("9"), Ten("10"), Jack("j"), Queen("q"), King("k");

        private String rank;

        Rank(String rank)
        {
            this.rank = rank;
        }

        @NonNull
        @Override
        public String toString()
        {
            return this.rank;
        }

    }

    private Rank rank;
    private Suit suit;
    boolean isFaceUp;

    Card(Rank rank, Suit suit)
    {
        this.suit = suit;
        this.rank = rank;
        isFaceUp = true;
    }

    public int softValue()
    {
        if(isAce()) return 11;
        else return nonAceValue();
    }

    public int hardValue()
    {
        if(isAce()) return 1;
        else return nonAceValue();
    }

    private int nonAceValue()
    {
        switch (this.rank)
        {
            case Jack:
            case Queen:
            case King:
                return 10;
            default:
                return Integer.parseInt(this.rank.toString());
        }
    }

    public boolean isAce()
    {
        return this.rank == Rank.Ace;
    }

    public boolean checkIfSameRank(Card otherCard) {
        return otherCard.rank == this.rank;
    }

    @NonNull
    @Override
    public String toString()
    {
        String cardString = this.suit.toString() + this.rank;
        return cardString;
    }

    public void turnFaceUp()
    {
        isFaceUp = true;
    }

    public void turnFaceDown()
    {
        isFaceUp = false;
    }

    public boolean getFaceUp()
    {
        return isFaceUp;
    }
}