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
    private Button deal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerCardTextView = (TextView) findViewById(R.id.playerCardTextView);
        playerCardImage1 = (ImageView) findViewById(R.id.playerCardImage1);
        deal = (Button) findViewById(R.id.deal);
        playerCardImage1.setImageResource(R.drawable.blue_back);
    }

    public void dealButtonClick(View view)
    {
        Card threeOfHearts = new Card("3", "Hearts");
        playerCardTextView.setText(threeOfHearts.toString());
        int image1ID = getResources().getIdentifier(threeOfHearts.toString(), "drawable", "com.example.blackjack");
        playerCardImage1.setImageResource(image1ID);
    }
}
