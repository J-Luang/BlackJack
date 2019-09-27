package com.example.blackjack;

public class DealerController
{

    Player dealer;
    BlackJackActions game;

    public DealerController(Player dealer, BlackJackActions game)
    {
        this.dealer = dealer;
    }

    public void begin()
    {
        dealer.getHand().get(1).turnFaceup();
//        while(!dealer.isPlayerDone) handleHitOrStand();
    }

    public void handleHitOrStand()
    {
        if(dealer.calculateBlackjackHandValue() < 17) game.hit(dealer);
    }
}
