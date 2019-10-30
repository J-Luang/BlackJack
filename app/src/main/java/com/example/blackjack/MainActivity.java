package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private ImageView whiteChip;
    private ImageView redChip;
    private ImageView blueChip;
    private ImageView greenChip;
    private ImageView blackChip;
    private TextView winLoss;
    private TextView userBet;
    private TextView addBet;
    private TextView subtractBet;
    private double betAmount;
    private double amountWon;
    private int blackJackValue;
    private TextView userChips;
    private double amountUserChips;
    private String textUserAmount;
    private String textBetAmount;
    private BlackJackActions actions;
    private Button bet;
    private Button hit;
    private Button stand;
    private Deck blackjackDeck;
    private Player player;
    private Player dealer;
    private Timer timer;
    private HandDisplay playerHandDisplay;
    private HandDisplay dealerHandDisplay;
    private int dealerMinLimit;
    private Switch plusMinusBet;
    private ConstraintLayout layout;
    private Button doubleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dealerMinLimit = 17;
        betAmount = 0;
        amountWon = 0;
        amountUserChips = 500;
        blackJackValue = 21;
        addBet =  findViewById(R.id.addBet);
        subtractBet = findViewById(R.id.subtractBet);
        userChips = findViewById(R.id.userChips);
        userBet = findViewById(R.id.userBet);
        actions = new BlackJackActions(blackjackDeck, player, dealer);
        blackjackDeck = new Deck(1);
        player = new Player(blackjackDeck);
        dealer = new Player(blackjackDeck);
        timer = new Timer();
        playerHandDisplay = new HandDisplay(this);
        dealerHandDisplay = new HandDisplay(this);
        layout = findViewById(R.id.myLayout);
        doubleDown = findViewById(R.id.doubleDown);

        whiteChip = (ImageView) findViewById(R.id.whiteChip);
        redChip = (ImageView) findViewById(R.id.redChip);
        blueChip = (ImageView) findViewById(R.id.blueChip);
        greenChip = (ImageView) findViewById(R.id.greenChip);
        blackChip = (ImageView) findViewById(R.id.blackChip);
        whiteChip.setOnClickListener(chipButtonListener);
        redChip.setOnClickListener(chipButtonListener);
        blueChip.setOnClickListener(chipButtonListener);
        greenChip.setOnClickListener(chipButtonListener);
        blackChip.setOnClickListener(chipButtonListener);
        winLoss = (TextView) findViewById(R.id.gameWinCheck);
        bet = (Button) findViewById(R.id.bet);
        hit = (Button) findViewById(R.id.hit);
        stand = (Button) findViewById(R.id.standButton);
        textUserAmount = "Amount of chips: $" + Double.toString(amountUserChips).format("%.2f", amountUserChips);
        userChips.setText(textUserAmount);
        textBetAmount = "Bet: $" + Double.toString(betAmount).format("%.2f", betAmount);
        userBet.setText(textBetAmount);
        plusMinusBet = findViewById(R.id.plusMinusBet);
    }

    public Boolean getSwitchState()
    {
        return plusMinusBet.isChecked();
    }

    private View.OnClickListener chipButtonListener = new View.OnClickListener()
    {
        public void onClick(View view)
        {
            betAmount = actions.bet(betAmount, view.getId(), getSwitchState(), amountUserChips);
            if (betAmount > 0)
            {
                bet.setEnabled(true);
                bet.setText("Bet: $" + Double.toString(betAmount).format("%.2f", betAmount));
            }
            if (betAmount == 0)
            {
                bet.setEnabled(false);
                bet.setText(R.string.bet_button_text);
            }
        }
    };

    public void dealButtonClick(View view)
    {
        // ------------------------------- New Stuff -----------------------------------
        actions.deal(player, dealer);
        //0.44861, 0.33988
        playerHandDisplay.create(0.33988, 0.55, player.hand, layout);
        playerHandDisplay.display();
        dealerHandDisplay.create(0.33988, 0.1, dealer.hand, layout);
        dealerHandDisplay.flipSecondCard();
        dealerHandDisplay.display();
        // ----------------------------- End New Stuff ---------------------------------
        amountUserChips -= betAmount;
        textUserAmount = "Amount of chips: $" + String.format(Locale.US, "%.2f", (amountUserChips));
        userChips.setText(textUserAmount);
        textBetAmount = "Bet: $" + String.format(Locale.US, "%.2f", betAmount);
        userBet.setText(textBetAmount);
        plusMinusBet.setVisibility(View.INVISIBLE);
        whiteChip.setVisibility(View.INVISIBLE);
        redChip.setVisibility(View.INVISIBLE);
        blueChip.setVisibility(View.INVISIBLE);
        greenChip.setVisibility(View.INVISIBLE);
        blackChip.setVisibility(View.INVISIBLE);
        addBet.setVisibility(View.INVISIBLE);
        subtractBet.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.INVISIBLE);
        setImagesInvisible();
        hit.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);
        doubleDown.setVisibility(View.VISIBLE);
        //actions.deal(player, dealer);
        dealer.getHand().get(1).turnFaceDown();

        if (player.calculateBlackjackHandValue() == blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            doubleDown.setVisibility(View.INVISIBLE);
            winLoss.setTextColor(getResources().getColor(R.color.winColor));
            winLoss.setText(R.string.blackjack);
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            amountWon = (betAmount * 2.5);
                            clearTable();

                        }
                    });
                }
            }, 5000);
        }
    }

    public void hitButtonClick(View view)
    {
        actions.hit(player);
        Log.i("PlayerHand", player.hand.getCards().toString());
        playerHandDisplay.display();
        //final ConstraintLayout layout = findViewById(R.id.myLayout);

        //moveNewCard(layout, playerLastCard, player);
        if(player.calculateBlackjackHandValue() == blackJackValue)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            doubleDown.setVisibility(View.INVISIBLE);
            gameConditions();
        }
        else if(player.calculateBlackjackHandValue() == blackJackValue)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            doubleDown.setVisibility(View.INVISIBLE);
            dealerPlay();
        }
        else if (player.calculateBlackjackHandValue() > blackJackValue)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            doubleDown.setVisibility(View.INVISIBLE);
            gameConditions();
        }
    }

    public void standButton(View view)
    {
        actions.stand(player);
        stand.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.INVISIBLE);
        doubleDown.setVisibility(View.INVISIBLE);
        dealerPlay();
    }

    public void doubleDownButton(View view)
    {

        if ((betAmount * 2) <= amountUserChips && player.getHand().size() == 2)
        {
            amountUserChips -= betAmount;
            textUserAmount = "Amount of chips: $" + String.format(Locale.US, "%.2f", (amountUserChips));
            userChips.setText(textUserAmount);
            betAmount = actions.doubleDown(betAmount);
            textBetAmount = "Bet: $" + String.format(Locale.US,"%.2f", betAmount);
            userBet.setText(textBetAmount);
            actions.hit(player);
            Log.i("PlayerHand - Double", player.hand.getCards().toString());
            playerHandDisplay.display();
            standButton(view);
        }
    }

    public void dealerPlay()
    {
        while(dealer.calculateBlackjackHandValue() < dealerMinLimit)
        {
            actions.hit(dealer);
            dealerHandDisplay.display();
        }
        if(dealer.calculateBlackjackHandValue() >= dealerMinLimit)
        {
            gameConditions();
        }
    }

    public void clearTable()
    {
        playerHandDisplay.clear();
        dealerHandDisplay.clear();
        bet.setEnabled(false);
        plusMinusBet.setChecked(false);
        winLoss.setVisibility(View.INVISIBLE);
        whiteChip.setVisibility(View.VISIBLE);
        redChip.setVisibility(View.VISIBLE);
        blueChip.setVisibility(View.VISIBLE);
        greenChip.setVisibility(View.VISIBLE);
        blackChip.setVisibility(View.VISIBLE);
        plusMinusBet.setVisibility(View.VISIBLE);
        addBet.setVisibility(View.VISIBLE);
        subtractBet.setVisibility(View.VISIBLE);
        hit.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.VISIBLE);
        player.discardHand();
        doubleDown.setVisibility(View.INVISIBLE);
        dealer.discardHand();
        Log.i("amountWon", Double.toString(amountWon));
        amountUserChips += amountWon;
        textUserAmount = "Amount of chips: $" + String.format(Locale.US, "%.2f", amountUserChips);
        userChips.setText(textUserAmount);
        betAmount = 0;
        textBetAmount = "Bet: $" + String.format(Locale.US, "%.2f", betAmount);
        userBet.setText(textBetAmount);
        bet.setText(R.string.bet_button_text);
        bet.setText(R.string.bet);
    }

    public void gameConditions()
    {
        int playerHandValue = player.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        if(player.calculateBlackjackHandValue() > blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setTextColor(getResources().getColor(R.color.colorAccent));
            winLoss.setText(R.string.player_bust);
            amountWon = 0;
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setTextColor(getResources().getColor(R.color.winColor));
            winLoss.setText(R.string.dealer_bust);
            amountWon = betAmount * 2;
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && playerHandValue == dealerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setTextColor(getResources().getColor(R.color.tieColor));
            winLoss.setText(R.string.push);
            amountWon = betAmount;
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > playerHandValue)
        {

            winLoss.setVisibility(View.VISIBLE);
            winLoss.setTextColor(getResources().getColor(R.color.colorAccent));
            winLoss.setText(R.string.dealer_won);
            amountWon = 0;
            clearTableDelay();
        }
        else
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setTextColor(getResources().getColor(R.color.winColor));
            winLoss.setText(R.string.player_won);
            amountWon = betAmount * 2;
            clearTableDelay();
        }
    }

    private void gameOver()
    {
        if(amountUserChips == 0)
        {

        }
    }

    public void setImagesInvisible()
    {
        plusMinusBet.setVisibility(View.INVISIBLE);
        whiteChip.setVisibility(View.INVISIBLE);
        redChip.setVisibility(View.INVISIBLE);
        blueChip.setVisibility(View.INVISIBLE);
        greenChip.setVisibility(View.INVISIBLE);
        blackChip.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.INVISIBLE);
        plusMinusBet.setVisibility(View.INVISIBLE);
    }

    public void clearTableDelay()
    {
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                MainActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        clearTable();
                    }
                });
            }
        }, 2000);
    }
}
