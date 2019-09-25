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
    private Button deal;
    private Button hit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        playerCardImage2 = (ImageView) findViewById(R.id.playerCardImage2);
        deal = (Button) findViewById(R.id.deal);
        hit = (Button) findViewById(R.id.hit);
    }

    public void dealButtonClick(View view)
    {
        Deck blackjackDeck = new Deck(1);
        Card playerCard1 = blackjackDeck.dealCard();
        Card playerCard2 = blackjackDeck.dealCard();

        int image1ID = getResources().getIdentifier(playerCard1.toString(), "drawable", "com.example.blackjack");
        int image2ID = getResources().getIdentifier(playerCard2.toString(), "drawable", "com.example.blackjack");
        playerCardImage1.setImageResource(image1ID);
        playerCardImage2.setImageResource(image2ID);
        playerCardImage1.setVisibility(View.VISIBLE);
        playerCardImage2.setVisibility(View.VISIBLE);
    }
}
