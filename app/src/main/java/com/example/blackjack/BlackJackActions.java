package com.example.blackjack;

import android.util.Log;
import android.widget.ImageView;

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

    public void deal(Player player1, Player dealer)
    {
        player1.drawCards(2);
        dealer.drawCards(2);
    }

    public void stand(Player player)
    {
        player.playerStand();
    }

    public void hit(Player player)
    {
        player.drawCards(1);
    }

    public void doubleDown()
    {

    }

    public int bet(int betAmount, int chip, boolean switchState)
    {
        int[] betChipsArray = new int[]{R.id.whiteChip, R.id.redChip, R.id.blueChip, R.id.greenChip, R.id.blackChip};
        int[] betAmountArray = new int[]{1, 5, 10, 25, 100};
        for (int i = 0; i < betChipsArray.length; i += 1)
        {
            Log.i("Switch","state: " + switchState);
            if(chip == betChipsArray[i])
            {
                if(!switchState)
                {
                    betAmount += betAmountArray[i];
                }
                else
                {
                    betAmount -= betAmountArray[i];
                }
            }
        }
        return betAmount;
    }
}
