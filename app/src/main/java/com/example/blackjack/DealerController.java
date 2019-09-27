package com.example.blackjack;

public class DealerController {

    Player dealer;
    BlackJackActions game;

    public DealerController(Player dealer, BlackJackActions game) {
        this.dealer = dealer;
    }

    public void begin() {
        while(!dealer.isPlayerDone) handleHitOrStand();
    }

    public void handleHitOrStand() {
        if(dealer.calculateBlackjackHandValue() < 17) game.hit(dealer);
        else(game.stand(dealer));
    }
}
