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
//    private ImageView playerCardImage1;
//    private ImageView playerCardImage2;
//    private ImageView dealerCardImage1;
//    private ImageView dealerCardImage2;
//    ImageView playerLastCard;
//    ImageView dealerLastCard;
    private BlackJackActions actions;
    private Button bet;
    private Button hit;
    private Button stand;
    private Deck blackjackDeck;
    private Player player;
    private Player dealer;
    private Timer timer;
//    private List<ImageView> playerCardImageArray;
//    private List<ImageView> dealerCardImageArray;
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
//        playerCardImageArray = new ArrayList<>();
//        dealerCardImageArray = new ArrayList<>();
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
//        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
//        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
//        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
//        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
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
        plusMinusBet.setVisibility(View.INVISIBLE);
        whiteChip.setVisibility(View.INVISIBLE);
        redChip.setVisibility(View.INVISIBLE);
        blueChip.setVisibility(View.INVISIBLE);
        greenChip.setVisibility(View.INVISIBLE);
        blackChip.setVisibility(View.INVISIBLE);
        bet.setVisibility(View.INVISIBLE);
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
                            clearTable();
                        }
                    });
                }
            }, 5000);
        }
//        playerCardImageArray.add(playerCardImage1);
//        playerCardImageArray.add(playerCardImage2);
//        dealerCardImageArray.add(dealerCardImage1);
//        dealerCardImageArray.add(dealerCardImage2);
//        playerLastCard = findViewById(R.id.playerCardImage2);
//        dealerLastCard = findViewById(R.id.dealerCardImage2);
//
//        for(int i = 0; i < 2; i++)
//        {
//            displayCard(playerCardImageArray.get(i), player.getHand().get(i));
//            displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
//        }
    }

    public void hitButtonClick(View view)
    {
        actions.hit(player);
        Log.i("PlayerHand", player.hand.getCards().toString());
        playerHandDisplay.display();
        //final ConstraintLayout layout = findViewById(R.id.myLayout);

        //moveNewCard(layout, playerLastCard, player);
        if(player.getHand().size() == 2 && player.calculateBlackjackHandValue() == blackJackValue)
        {
            stand.setVisibility(View.INVISIBLE);
            hit.setVisibility(View.INVISIBLE);
            gameConditions();
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
//        dealer.getHand().get(1).turnFaceUp();
//        displayCard(dealerCardImage2, dealer.getHand().get(1));
        dealerPlay();
    }

    public void doubleDownButton(View view)
    {
        //final ConstraintLayout layout = findViewById(R.id.myLayout);

        if ((betAmount * 2) <= amountUserChips)
        {
            betAmount = actions.doubleDown(betAmount);
            actions.hit(player);
            Log.i("PlayerHand - Double", player.hand.getCards().toString());
            playerHandDisplay.display();
//            moveNewCard(layout, playerLastCard, player);
            standButton(view);
        }
    }

    public void dealerPlay()
    {
//        for(int i = 0; i < 2; i++)
//        {
//            displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
//        }
//        final ConstraintLayout layout = findViewById(R.id.myLayout);
        while(dealer.calculateBlackjackHandValue() < dealerMinLimit)
        {
            actions.hit(dealer);
            dealerHandDisplay.display();
//            final ConstraintLayout layout = findViewById(R.id.myLayout);
//            moveNewCard(layout, dealerLastCard, dealer);
//
//            for(int i = 0; i < dealerCardImageArray.size(); i++)
//            {
//                displayCard(dealerCardImageArray.get(i), dealer.getHand().get(i));
//            }
        }
        if(dealer.calculateBlackjackHandValue() >= dealerMinLimit)
        {
            gameConditions();
        }
    }

   /* public void displayCard(ImageView imageView, Card card)
    {
        int cardImageID = getResources().getIdentifier(card.toString(), "drawable", "com.example.blackjack");
        if(card.getFaceUp()) imageView.setImageResource(cardImageID);
        else imageView.setImageResource(R.drawable.blue_back);
        imageView.setVisibility(View.VISIBLE);
    }
    */
    public void clearTable()
    {
        playerHandDisplay.clear();
        dealerHandDisplay.clear();
        winLoss.setVisibility(View.INVISIBLE);
//        playerCardImage1.setVisibility(View.INVISIBLE);
//        playerCardImage2.setVisibility(View.INVISIBLE);
//        dealerCardImage1.setVisibility(View.INVISIBLE);
//        dealerCardImage2.setVisibility(View.INVISIBLE);
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
        bet.setText("Bet");

//        for(ImageView imageView : playerCardImageArray) {
//            imageView.setVisibility(View.INVISIBLE);
//        }
//        for(ImageView imageView : dealerCardImageArray) {
//            imageView.setVisibility(View.INVISIBLE);
//        }
//
//        playerCardImageArray.clear();
//        dealerCardImageArray.clear();
    }

    public void gameConditions()
    {
        int playerHandValue = player.calculateBlackjackHandValue();
        int dealerHandValue = dealer.calculateBlackjackHandValue();
        if(player.calculateBlackjackHandValue() > blackJackValue)
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
                            amountUserChips += betAmount *= 2;
                        }
                    });
                }
            }, 1000);
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > blackJackValue)
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
            }, 1000);
        }
        else if(playerHandValue < blackJackValue && playerHandValue == dealerHandValue)
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
            }, 1000);
        }
        else if(playerHandValue < blackJackValue && dealerHandValue > playerHandValue)
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
            }, 1000);
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
            }, 1000);
        }
    }

    private void gameOver()
    {
        if(amountUserChips == 0)
        {

        }
    }


//    private void moveNewCard(ConstraintLayout layout, ImageView lastCard, Player player)
//    {
//        ImageView image;
//        if(player == this.player)
//        {
//            image = createNewCard(playerCardImageArray);
//        }
//        else
//        {
//            image = createNewCard(dealerCardImageArray);
//        }
//        final float currentX = lastCard.getX();
//        final float currentY = lastCard.getY();
//
//        final int currentHeight = lastCard.getHeight();
//        final int currentWidth = lastCard.getWidth();
//        final float separation = 160;
//
//        image.requestLayout();
//
//        image.setX(currentX + separation);
//        image.setY(currentY);
//        Log.i("carddisplay", "x: " + currentX + ", y: " + currentY);
//
//        image.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        image.setLayoutParams(new LinearLayout.LayoutParams(currentWidth, currentHeight));
//        if(player == this.player)
//        {
//            displayCard(image, this.player.getHand().getCards().get(playerCardImageArray.size() - 1));
//        }
//        else
//        {
//            displayCard(image, dealer.getHand().getCards().get(dealerCardImageArray.size() - 1));
//        }
//        layout.addView(image);
//        if(player == this.player)
//        {
//            playerLastCard = image;
//        }
//        else
//        {
//            dealerLastCard = image;
//        }
//    }

    public ImageView createNewCard(List<ImageView> arrayToBeAdded)
    {
        final ImageView newCard = new ImageView(this);
        arrayToBeAdded.add(newCard);
        return newCard;
    }
}
