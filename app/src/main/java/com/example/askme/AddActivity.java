package com.example.askme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.askme.Data.States;

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_DATA_ID = "extra_notes_id";
    public static final String EXTRA_DATA_STATE = "extra_notes_state";
    public static final String EXTRA_DATA_CAPITAL = "extra_notes_capital";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Bundle extras = getIntent().getExtras();
        final AddViewModel viewModel = new ViewModelProvider(this).get(AddViewModel.class);

        final EditText stateET = findViewById(R.id.StateET);
        final EditText capitalET = findViewById(R.id.CapitalET);
        Button saveBtn = findViewById(R.id.saveBTN);

        if(extras != null){
            String statesState = extras.getString(EXTRA_DATA_STATE,"");
            String statesCapital = extras.getString(EXTRA_DATA_CAPITAL,"");
            if(statesState!=null){
                stateET.setText(statesState);

            }
            if (statesCapital!=null){
                capitalET.setText(statesCapital);
            }
            saveBtn.setText("UPDATE");

        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String STATE = stateET.getText().toString();
                String CAPITAL = capitalET.getText().toString();
                if(STATE.isEmpty()&&CAPITAL.isEmpty()){
                    if (extras!=null){
                        Long id = extras.getLong(EXTRA_DATA_ID);
                        States states = new States(id,STATE,CAPITAL);
                        viewModel.updateState(states);
                    }
                    else{
                        States states = new States(0L,STATE,CAPITAL);
                        viewModel.insertState(states);

                    }

                }
                else{
                    Toast.makeText(AddActivity.this,"Missed inputs",Toast.LENGTH_SHORT).show();
                }
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}