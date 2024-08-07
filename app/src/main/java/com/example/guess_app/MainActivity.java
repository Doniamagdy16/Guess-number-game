package com.example.guess_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView wrongText,countText,guessText;
    Button startButton;
    int quest;
    TableLayout tableLayout;
    Random r=new Random();
    List<TextView> data=new ArrayList<>();

    ArrayList<Integer> randoms=new ArrayList<>();
    int num;
    byte count;
    boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wrongText=findViewById(R.id.wrongText);
        countText=findViewById(R.id.countText);
        startButton=findViewById(R.id.startButton);
        tableLayout=(TableLayout) findViewById(R.id.tableLayout);
        guessText=findViewById(R.id.guessText);

    }
    public void start(View view) {
        start=true;
        wrongText.setText("");
        countText.setText("");
        Toast.makeText(this, "the game is started", Toast.LENGTH_SHORT).show();
        for (TextView datum : data) {
            if (datum.getParent() instanceof TableRow) {
                TableRow tableRow = (TableRow) datum.getParent();
                int index = tableRow.indexOfChild(datum);
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.question_icon);
            //    imageView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));  
                // Set layout parameters

                tableRow.addView(imageView, index);
                TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) imageView.getLayoutParams();
                layoutParams.weight=1.0f;
                layoutParams.gravity= Gravity.CENTER;
                tableRow.removeView(datum);
            }
        }
        data.clear();
        count=0;
        quest=r.nextInt(9)+1;
        guessText.setText("Guess Where The Number "+quest);
        randoms.clear();
        int x=9;
        for (int i = 0; i < x; i++) {
            num=r.nextInt(9)+1;
            if(!randoms.contains(num)) randoms.add(num);
            else x++;
        }
    }

    public void guess(View view) {
//        if(start==false){
//            YoYo.with(Techniques.Shake).duration(500).repeat(2).playOn(startButton);
//            Toast.makeText(this, "press start", Toast.LENGTH_SHORT).show();
//       return;
//        }
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View child=tableLayout.getChildAt(i);
            if(child instanceof TableRow){
                TableRow row=(TableRow) child;
                for (int j = 0; j < row.getChildCount(); j++) {
                    View view2 =row.getChildAt(j);
                    view2.setOnClickListener(this);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {

        if(start==false){
            YoYo.with(Techniques.Shake).duration(500).repeat(2).playOn(startButton);

            Toast.makeText(this, "press start", Toast.LENGTH_SHORT).show();
            return;
        }
         TableRow tableRow = (TableRow) v.getParent();
        int row = tableLayout.indexOfChild((View)tableRow);
        int col =tableRow.indexOfChild(v);
        TextView textView = new TextView(this);
        int randomIndex = row * 3 + col; // Calculate the correct index for randoms
        textView.setText("" + randoms.get(randomIndex));
        textView.setTextSize(16);
        tableRow.removeView(v);
        tableRow.addView(textView, col);
        TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) textView.getLayoutParams();
        layoutParams.weight=1.0f;
        layoutParams.gravity= Gravity.CENTER;
        data.add(textView);
        if(quest== Integer.parseInt(textView.getText().toString())){
            wrongText.setText("right");
            start=false;
            Toast.makeText(this, "you win the game", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.FadeIn).duration(200).repeat(2).playOn(textView);

        }
        else {
            wrongText.setText("wrong");
            YoYo.with(Techniques.Shake).duration(200).repeat(2).playOn(textView);
            count++;
            countText.setText(""+count);
        }
        if(count==3){
            Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();
            start=false;
            wrongText.setText("");
        }


    }
}