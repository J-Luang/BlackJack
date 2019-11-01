package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class SettingsActivity extends AppCompatActivity
{
    private Deck blackjackDeck;
    private Player player;
    private Player dealer;
    private int numberTestCase;
    private EditText numberTest;
    private int testNumber;
    private Button blackJackButton;
    private Button dealerBusts;
    private Button playerBusts;
    private Button playerWins;
    private Button dealerWins;
    private Button aceTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent settings = getIntent();
        numberTestCase = settings.getIntExtra(getString(R.string.number_of_test_cases), -1);

        blackjackDeck = new Deck(1);
        player = new Player(blackjackDeck);
        dealer = new Player(blackjackDeck);

        numberTestCase = 6;
    }




    public void backButtonClick(View view)
    {
        goToBackScreen();
    }

    private void goToBackScreen()
    {
        final Intent mainActivity = new Intent(this, MainActivity.class);
        boolean numberEntered = false;
        Intent settings = new Intent();

        try
        { // Check that the number of questions is valid.
            int newTestNumber = Integer.parseInt(String.valueOf(numberTest.getText()));

            if (!((1 <= newTestNumber) && newTestNumber <= numberTestCase))
            {  // INVALID numberOfQuestions was entered - DON'T return to quiz.
                //errorTextView.setTextColor(Color.RED);
                //errorTextView.setText("Error! Must be between 1 and " + numberTestCase + ". Enter again.");
                numberTest.setText("");
                numberEntered = true;
            }
            else
            {
                settings.putExtra(getString(R.string.number_of_test_cases), newTestNumber);
                setResult(Activity.RESULT_OK, settings);
                finish();
            }
        }
        catch (NumberFormatException exception)
        {
            numberEntered = false; // Nothing entered for numberOfQuestions.
        }

        if (!numberEntered)
        {
            setResult(Activity.RESULT_CANCELED, settings);
            finish();
        }
        startActivity(mainActivity);
    }

    public int getNumber(){return numberTestCase;}

    public void setNumberOfCases(int newTestNumber)
    {
        if (!((1 <= newTestNumber) && newTestNumber <= numberTestCase))
        {
            testNumber = newTestNumber;
        }
    }
}
