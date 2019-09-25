package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView playerCardTextView;
    private ImageView playerCardImage1;
    private ImageView playerCardImage2;
    private ImageView playerCardImage3;
    private ImageView dealerCardImage1;
    private ImageView dealerCardImage2;
    private Button deal;
    private Button hit;
    private Deck blackjackDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        playerCardImage3 = (ImageView) findViewById(R.id.playerCardImage3);
        dealerCardImage1 = (ImageView) findViewById(R.id.dealerCardImage1);
        dealerCardImage2 = (ImageView) findViewById(R.id.dealerCardImage2);
        deal = (Button) findViewById(R.id.deal);
        hit = (Button) findViewById(R.id.hit);
    }

    public void dealButtonClick(View view)
    {
        blackjackDeck = new Deck(1);
        blackjackDeck.shuffle();
        Card playerCard1 = blackjackDeck.dealCard();
        Card playerCard2 = blackjackDeck.dealCard();
        Card dealerCard1 = blackjackDeck.dealCard();
        Card dealerCard2 = blackjackDeck.dealCard();

        int playerCardImage1ID = getResources().getIdentifier(playerCard1.toString(), "drawable", "com.example.blackjack");
        int playerCardImage2ID = getResources().getIdentifier(playerCard2.toString(), "drawable", "com.example.blackjack");
        int dealerCardImage1ID = getResources().getIdentifier(dealerCard1.toString(), "drawable", "com.example.blackjack");
        int dealerCardImage2ID = getResources().getIdentifier(dealerCard2.toString(), "drawable", "com.example.blackjack");
        playerCardImage1.setImageResource(playerCardImage1ID);
        playerCardImage2.setImageResource(playerCardImage2ID);
        dealerCardImage1.setImageResource(dealerCardImage1ID);
        dealerCardImage2.setImageResource(R.drawable.blue_back);
        playerCardImage1.setVisibility(View.VISIBLE);
        playerCardImage2.setVisibility(View.VISIBLE);
        dealerCardImage1.setVisibility(View.VISIBLE);
        dealerCardImage2.setVisibility(View.VISIBLE);
    }

    public void hitButtonClick(View view)
    {
        Card playerCard3 = blackjackDeck.dealCard();
        int playerCardImage3ID = getResources().getIdentifier(playerCard3.toString(), "drawable", "com.example.blackjack");
        playerCardImage3.setImageResource(playerCardImage3ID);
        playerCardImage3.setVisibility(View.VISIBLE);
    }
}
