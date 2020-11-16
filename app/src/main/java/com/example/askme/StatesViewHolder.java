package com.example.askme;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.askme.Data.States;

public class StatesViewHolder extends RecyclerView.ViewHolder {

    TextView stateTV,capitalTv;
    public StatesViewHolder(@NonNull View itemView) {
        super(itemView);
        stateTV = itemView.findViewById(R.id.stateTV);
        capitalTv = itemView.findViewById(R.id.capitalTV);
    }
    public void bind(States states){
        stateTV.setText(states.getStateName());
        capitalTv.setText(states.getCapitalName());
    }
}
