package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
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
    private TextView userChips;
    private int amountUserChips;
    private String textUserAmount;
    private ImageView playerCardImage1;
    private ImageView playerCardImage2;
    private ImageView dealerCardImage1;
    private ImageView dealerCardImage2;
    ImageView playerLastCard;
    ImageView dealerLastCard;
    private BlackJackActions actions;
    private Button bet;
    private Button hit;
    private Button stand;
    private Deck blackjackDeck;
    private Player player1;
    private Player dealer;
    private Timer timer;
    private List<ImageView> playerCardImageArray;
    private List<ImageView> dealerCardImageArray;
    private int dealerMinLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dealerMinLimit = 17;
        betAmount = 0;
        amountUserChips = 500;
        betText = findViewById(R.id.betText);
        userChips = findViewById(R.id.userChips);
        actions = new BlackJackActions(blackjackDeck, player1, dealer);
        blackjackDeck = new Deck(1);
        player1 = new Player(blackjackDeck);
        dealer = new Player(blackjackDeck);
        timer = new Timer();
        playerCardImageArray = new ArrayList<>();
        dealerCardImageArray = new ArrayList<>();

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
        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
        bet = (Button) findViewById(R.id.bet);
        hit = (Button) findViewById(R.id.hit);
        stand = (Button) findViewById(R.id.standButton);
        textUserAmount = "Amount of chips: " + amountUserChips;
        userChips.setText(textUserAmount);
    }

    private View.OnClickListener chipButtonListener = new View.OnClickListener()
    {
        public void onClick(View view)
        {
            betAmount = actions.bet(betAmount, view.getId());
            betText.setText(Integer.toString(betAmount));
        }
    };

    public void dealButtonClick(View view)
    {
        textUserAmount = "Amount of chips: " + (amountUserChips - betAmount);
        userChips.setText(textUserAmount);
        whiteChip.setVisibility(View.INVISIBLE);
        redChip.setVisibility(View.INVISIBLE);
        blueChip.setVisibility(View.INVISIBLE);
        greenChip.setVisibility(View.INVISIBLE);
        blackChip.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.VISIBLE);
        stand.setVisibility(View.VISIBLE);
        actions.deal(player1, dealer);
        dealer.getHand().get(1).turnFaceDown();
        playerCardImageArray.add(playerCardImage1);
        playerCardImageArray.add(playerCardImage2);
        dealerCardImageArray.add(dealerCardImage1);
        dealerCardImageArray.add(dealerCardImage2);
        playerLastCard = findViewById(R.id.playerCardImage2);
        dealerLastCard = findViewById(R.id.dealerCardImage2);

        for(int i = 0; i < 2; i++)
        {
            displayCard(playerCardImageArray.get(i), player1.getHand().get(i));
            displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
        }
    }

    public void hitButtonClick(View view)
    {
        actions.hit(player1);
        final ConstraintLayout layout = findViewById(R.id.myLayout);

        moveNewCard(layout, playerLastCard, player1);
        if(player1.getHand().size() == 2 && player1.calculateBlackjackHandValue() == 21)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            gameConditions();
        }
        else if (player1.calculateBlackjackHandValue() > 21)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            gameConditions();
        }
    }

    public void standButton(View view)
    {
        actions.stand(player1);
        stand.setVisibility(View.INVISIBLE);
        hit.setVisibility(View.INVISIBLE);
        dealer.getHand().get(1).turnFaceUp();
        displayCard(dealerCardImage2, dealer.getHand().get(1));
        dealerPlay();
    }

    public void dealerPlay()
    {
        while(dealer.calculateBlackjackHandValue() < dealerMinLimit)
        {
            actions.hit(dealer);
            final ConstraintLayout layout = findViewById(R.id.myLayout);
            moveNewCard(layout, dealerLastCard, dealer);

            for(int i = 0; i < dealerCardImageArray.size(); i++)
            {
                displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
            }
        }
        if(dealer.calculateBlackjackHandValue() >= dealerMinLimit)
        {
            gameConditions();
        }
    }

    public void displayCard(ImageView imageView, Card card)
    {
        int cardImageID = getResources().getIdentifier(card.toString(), "drawable", "com.example.blackjack");
        if(card.getFaceUp()) imageView.setImageResource(cardImageID);
        else imageView.setImageResource(R.drawable.blue_back);
        imageView.setVisibility(View.VISIBLE);
    }

    public void clearTable()
    {
        winLoss.setVisibility(View.INVISIBLE);
        playerCardImage1.setVisibility(View.INVISIBLE);
        playerCardImage2.setVisibility(View.INVISIBLE);
        dealerCardImage1.setVisibility(View.INVISIBLE);
        dealerCardImage2.setVisibility(View.INVISIBLE);
        whiteChip.setVisibility(View.VISIBLE);
        redChip.setVisibility(View.VISIBLE);
        blueChip.setVisibility(View.VISIBLE);
        greenChip.setVisibility(View.VISIBLE);
        blackChip.setVisibility(View.VISIBLE);
        hit.setVisibility(View.INVISIBLE);
        stand.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.VISIBLE);
        player1.discardHand();
        dealer.discardHand();
        amountUserChips = amountUserChips - betAmount;
        betAmount = 0;
        betText.setText(Integer.toString(betAmount));

        for(ImageView imageView : playerCardImageArray) {
            imageView.setVisibility(View.INVISIBLE);
        }
        for(ImageView imageView : dealerCardImageArray) {
            imageView.setVisibility(View.INVISIBLE);
        }

        playerCardImageArray.clear();
        dealerCardImageArray.clear();
    }

    public void gameConditions()
    {
        int playerHandValue = player1.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        if(player1.calculateBlackjackHandValue() > 21)
        {
            winLoss.setVisibility(View.VISIBLE);
            winLoss.setText(R.string.player_bust);
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
            }, 5000);
        }
        else if(playerHandValue < 21 && dealerHandValue > 21)
        {
                    winLoss.setVisibility(View.VISIBLE);
                    winLoss.setText(R.string.dealer_bust);
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
            }, 5000);
        }
        else if(playerHandValue < 21 && playerHandValue == dealerHandValue)
        {
                    winLoss.setVisibility(View.VISIBLE);
                    winLoss.setText(R.string.push);
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
            }, 5000);
        }
        else if(playerHandValue < 21 && dealerHandValue > playerHandValue)
        {

                    winLoss.setVisibility(View.VISIBLE);
                    winLoss.setText(R.string.dealer_won);
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
            }, 5000);
        }
        else
        {
                winLoss.setVisibility(View.VISIBLE);
                winLoss.setText(R.string.player_won);
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
            }, 5000);
        }
    }

    private void gameOver()
    {
        if(amountUserChips == 0)
        {

        }
    }


    private void moveNewCard(ConstraintLayout layout, ImageView lastCard, Player player)
    {
        ImageView image;
        if(player == player1)
        {
            image = createNewCard(playerCardImageArray);
        }
        else
        {
            image = createNewCard(dealerCardImageArray);
        }
        final float currentX = lastCard.getX();
        final float currentY = lastCard.getY();

        final int currentHeight = lastCard.getHeight();
        final int currentWidth = lastCard.getWidth();
        final float separation = 160;

        image.requestLayout();

        image.setX(currentX + separation);
        image.setY(currentY);

        image.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        image.setLayoutParams(new LinearLayout.LayoutParams(currentWidth, currentHeight));
        if(player == player1)
        {
            displayCard(image, player1.getHand().getCards().get(playerCardImageArray.size() - 1));
        }
        else
        {
            displayCard(image, dealer.getHand().getCards().get(dealerCardImageArray.size() - 1));
        }
        layout.addView(image);
        if(player == player1)
        {
            playerLastCard = image;
        }
        else
        {
            dealerLastCard = image;
        }
    }

    public ImageView createNewCard(List<ImageView> arrayToBeAdded)
    {
        final ImageView newCard = new ImageView(this);
        arrayToBeAdded.add(newCard);
        return newCard;
    }
}
