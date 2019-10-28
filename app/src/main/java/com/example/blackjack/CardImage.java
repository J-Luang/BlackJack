package com.example.blackjack;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

// A lot of the code in this class based on https://stackoverflow.com/questions/22115021/android-create-imageview-outside-mainactivity
public class CardImage
{
    ConstraintLayout Layout;
    Activity activity;
    DisplayMetrics displayMetrics;
    private Card card;
    private ImageView imageView;
    private static int HEIGHT; // Height and width are not final, but are constant after they are
    private static int WIDTH; // set based on the screen size
    private static int OFFSET; // The distance between each card in a hand; based on card width
    boolean faceUp;

    public CardImage(Activity activity)
    {
        this.activity = activity;
        ImageView cardTemplate = (ImageView) activity.findViewById(R.id.playerCardImage1);
        HEIGHT = cardTemplate.getMeasuredHeight();
        WIDTH = cardTemplate.getMeasuredWidth();
    }

    public void create(double xPercent, double yPercent, Card card, ConstraintLayout layout)
    {
        Layout = layout;
        imageView = new ImageView(activity);
        this.card = card;

        // Sets the position of each card based on screen size.
        // The parameters xPercent and yPercent represent the x and y position as a percentage of screen size
        // Taken from https://stackoverflow.com/questions/12780384/is-it-safe-to-use-getwidth-on-display-even-though-its-deprecated
        displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int x = (int) Math.round((double) displayMetrics.heightPixels * xPercent);
        int y = (int) Math.round((double) displayMetrics.widthPixels * yPercent);
        imageView.setX(x);
        imageView.setY(y);

        // Sets the height and width of the card.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = HEIGHT;
        params.width = WIDTH;
        imageView.setLayoutParams(params);

        Layout.addView(imageView);
        faceUp = true;
    }

    public void move(float x, float y)
    {
        imageView.setX(x);
        imageView.setY(y);
    }

    public void display()
    {
        int cardImageID = activity.getResources().getIdentifier(card.toString(), "drawable", "com.example.blackjack");
        if(faceUp) imageView.setImageResource(cardImageID);
        else imageView.setImageResource(R.drawable.blue_back);
        imageView.setVisibility(View.VISIBLE);
    }

    public void remove()
    {
        Layout.removeView(imageView);
    }

    public void flip()
    {
        faceUp = !faceUp;
    }

    public double getOFFSET()
    {
        return ((double) WIDTH / 2.0 ) / (double) displayMetrics.widthPixels;
    }

}
