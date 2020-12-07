package com.example.askme.Custom;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.askme.Data.States;

import java.util.List;
import java.util.Random;

public class QuizView extends LinearLayout {
    private States currentStates;
    private int correctOptoionId;
    private RadioGroup optionsRadio;
    private OptionsClickListner optionsClickListner;
    public QuizView(Context context) {
        super(context);

        intRadio();
    }

    private void intRadio() {
        optionsRadio = new RadioGroup(getContext());
        optionsRadio.setId(View.generateViewId());
    }

    public void setData(List<States> statesList) {
        Random random = new Random(System.currentTimeMillis());
        int correctOptions  = random.nextInt(4);

        currentStates = statesList.get(correctOptions);

        TextView questionTV = new TextView(getContext());
        String question = "What is the capital of" + currentStates.getStateName() + "?";
        questionTV.setText(question);
        this.addView(questionTV);
        this.addView(optionsRadio);

        RadioButton[] radios = new RadioButton[4];
        radios[correctOptions]= new RadioButton(getContext());
        radios[correctOptions].setId(View.generateViewId());
        radios[correctOptions].setText(currentStates.getCapitalName());

        correctOptoionId = radios[correctOptions].getId();

        for (int i = 0, j = 0; i<4 && j<4; i++,j++){
            if(i==correctOptions){
                optionsRadio.addView(radios[correctOptions]);
                continue;
            }
            else {
                radios[i] = new RadioButton(getContext());
                radios[i].setId(View.generateViewId());
                radios[i].setText(statesList.get(j).getCapitalName());
                optionsRadio.addView(radios[i]);
            }
            initListeners();

        }
    }

    private void initListeners() {
        optionsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if(optionsClickListner!=null){
                    if(i == correctOptoionId){
                        optionsClickListner.optionsClick(true);
                    }
                    else {
                        optionsClickListner.optionsClick(false);
                    }
                }
            }
        });
    }

    public interface OptionsClickListner{
        void optionsClick(Boolean result);
    }
    public void setOptionsClickListner(OptionsClickListner optionsClickListner){
        this.optionsClickListner = optionsClickListner;
    }
    public void reset(){
        optionsRadio.removeAllViews();
        this.removeAllViews();
    }
}
