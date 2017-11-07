package com.example.john.puzzled;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

class ButtonTag {
    public ButtonTag(int widthPosition, int heightPosition) {
        this.widthPosition = widthPosition;
        this.heightPosition = heightPosition;
    }

    public int getWidthPosition() {
        return widthPosition;
    }

    public int getHeightPosition() {
        return heightPosition;
    }

    private int heightPosition;

    private int widthPosition;
}

public class MainActivity extends AppCompatActivity {

    private static final int[][] idArray = {{R.id.button00,R.id.button01,R.id.button02},
            {R.id.button10,R.id.button11,R.id.button12},
            {R.id.button20,R.id.button21,R.id.button22}};
    int boardHeight = 3;
    int boardWidth = 3;
    private ImageButton[][] bt = new ImageButton[boardWidth][boardHeight];
    ImageButton blank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int i = 0;
        int j = 0;
        for (i = 0; i<boardWidth; i++) {
            for(j = 0; j<boardHeight;j++) {
                bt[i][j] = (ImageButton) findViewById(idArray[i][j]);
                bt[i][j].setTag(new ButtonTag(i, j));
                bt[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton viewButton = (ImageButton) view;
                        Drawable drawable = viewButton.getDrawable();
                        viewButton.setImageDrawable(blank.getDrawable());
                        blank.setImageDrawable(drawable);
                        int viewButtonWidthPosition = ((ButtonTag)viewButton.getTag()).getWidthPosition();
                        int viewButtonHeightPosition =((ButtonTag)viewButton.getTag()).getHeightPosition();
                        blank = bt[viewButtonWidthPosition][viewButtonHeightPosition];
                        updateClickable(viewButtonWidthPosition,viewButtonHeightPosition);
                    }
                });
            }
        }
        blank =  bt[i-1][j-1];
    }

    public ArrayList<ButtonTag> getAdjoiningButtons(int widthtPosition, int heightPosition) {

        ArrayList<ButtonTag> positions = new ArrayList<>();

        //to the right
        if(widthtPosition < boardWidth - 1) {
            positions.add(new ButtonTag(widthtPosition +1,heightPosition));
        }
        //to the left
        if(widthtPosition > 0) {
            positions.add(new ButtonTag(widthtPosition - 1,heightPosition));
        }
        //up
        if(heightPosition > 0) {
            positions.add(new ButtonTag(widthtPosition,heightPosition - 1));
        }
        //down
        if(heightPosition < boardHeight - 1) {
            positions.add(new ButtonTag(widthtPosition,heightPosition + 1));
        }

        return positions;

    }

    public void updateClickable(int widthtPosition, int heightPosition) {
        for (int i = 0; i<boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                bt[i][j].setClickable(false);
            }
        }

        ArrayList<ButtonTag> positions = getAdjoiningButtons(widthtPosition, heightPosition);

        for(ButtonTag buttonTag: positions) {
            bt[buttonTag.getWidthPosition()][buttonTag.getHeightPosition()].setClickable(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
