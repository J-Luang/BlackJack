package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private ImageView whiteChip;
    private ImageView redChip;
    private ImageView blueChip;
    private ImageView greenChip;
    private ImageView blackChip;

    private TextView winLoss;
    private TextView betText;
    private int betAmount;
    private int blackJackValue;
    private TextView userChips;
    private int amountUserChips;
    private String textUserAmount;
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
        amountUserChips = 500;
        blackJackValue = 21;
        userChips = findViewById(R.id.userChips);
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
        textUserAmount = "Amount of chips: " + amountUserChips;
        userChips.setText(textUserAmount);
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
            betAmount = actions.bet(betAmount, view.getId(), getSwitchState());
            bet.setText("Bet: " + Integer.toString(betAmount));
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
        textUserAmount = "Amount of chips: " + (amountUserChips - betAmount);
        userChips.setText(textUserAmount);
        setImagesInvisible();
        hit.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);
        doubleDown.setVisibility(View.VISIBLE);
        dealer.getHand().get(1).turnFaceDown();

        if (player.calculateBlackjackHandValue() == blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            doubleDown.setVisibility(View.INVISIBLE);
            winLoss.setText(R.string.blackjack);
            clearTableDelay();
        }
        if(amountUserChips <= betAmount * 2)
        {
            doubleDown.setVisibility(View.INVISIBLE);
        }
    }

    public void hitButtonClick(View view)
    {
        actions.hit(player);
        Log.i("PlayerHand", player.hand.getCards().toString());
        playerHandDisplay.display();
        if(player.getHand().size() == 2 && player.calculateBlackjackHandValue() == blackJackValue)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
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
        if ((betAmount * 2) <= amountUserChips)
        {
            betAmount = actions.doubleDown(betAmount);
            actions.hit(player);
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
        winLoss.setVisibility(View.INVISIBLE);
        whiteChip.setVisibility(View.VISIBLE);
        redChip.setVisibility(View.VISIBLE);
        blueChip.setVisibility(View.VISIBLE);
        greenChip.setVisibility(View.VISIBLE);
        blackChip.setVisibility(View.VISIBLE);
        hit.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.VISIBLE);
        player.discardHand();
        doubleDown.setVisibility(View.INVISIBLE);
        dealer.discardHand();
        amountUserChips = amountUserChips - betAmount;
        betAmount = 0;
        bet.setText(R.string.bet);
    }

    public void gameConditions()
    {
        int playerHandValue = player.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        if(player.calculateBlackjackHandValue() > blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.player_bust);
            clearTableDelay();

        }
        else if(playerHandValue < blackJackValue && dealerHandValue > blackJackValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            amountUserChips += betAmount * 2;
            winLoss.setText(R.string.dealer_bust);
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && playerHandValue == dealerHandValue)
        {
            winLoss.setVisibility(View.VISIBLE);
            amountUserChips += betAmount;
            winLoss.setText(R.string.push);
            clearTableDelay();
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > playerHandValue)
        {

            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.dealer_won);
            clearTableDelay();
        }
        else
        {
            winLoss.setVisibility(View.VISIBLE);
            amountUserChips += betAmount * 2;
            winLoss.setText(R.string.player_won);
            clearTableDelay();
        }
    }

    private void gameOver()
    {
        if(amountUserChips == 0)
        {

        }
    }

    public ImageView createNewCard(List<ImageView> arrayToBeAdded)
    {
        final ImageView newCard = new ImageView(this);
        arrayToBeAdded.add(newCard);
        return newCard;
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
