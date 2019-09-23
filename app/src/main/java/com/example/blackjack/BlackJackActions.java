package com.example.blackjack;

public class BlackJackActions
{
    Deck deck = new Deck();
    Player player = new Player(deck);



    public void stand()
    {
        playerStand();
    }

    public void hit()
    {
        checkIfPlayerBusts();
    }

    public void doubleDown()
    {

    }

    public void split()
    {

    }
}
