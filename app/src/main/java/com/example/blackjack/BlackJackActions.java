package com.example.blackjack;

public class BlackJackActions
{
    private Deck deck;
    private Player player;
    private Player dealer;

    BlackJackActions(Deck deck, Player player, Player dealer)
    {
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
    }

    public void stand(Player player)
    {
        player.playerStand();
    }

    public void hit(Player player)
    {
        player.drawCards(1);
        player.checkIfPlayerBusts();
    }

    public void doubleDown()
    {

    }

    public void split()
    {

    }
}
