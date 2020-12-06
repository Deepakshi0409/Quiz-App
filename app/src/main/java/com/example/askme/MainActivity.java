package com.example.askme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.askme.Custom.QuizView;
import com.example.askme.Custom.QuizViewModel;
import com.example.askme.Data.States;
import com.example.askme.Settings.settingsActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuizView quizView;
    private QuizViewModel quizViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        quizView = findViewById(R.id.quizView);
        quizViewModel.data.observe(this, new Observer<List<States>>() {
            @Override
            public void onChanged(List<States> states) {
                if (states!=null){
                    if (states.size()==4){
                        quizView.setData(states);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Add more States",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        quizView.setOptionsClickListner(new QuizView.OptionsClickListner() {
            @Override
            public void optionsClick(Boolean result) {
                updateResult(result);
            }
        });

    }

    private void updateResult(Boolean result) {
        if (result){
            Toast.makeText(MainActivity.this,"Correct Answer",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(MainActivity.this,"InCorrect Answer",Toast.LENGTH_SHORT).show();
        }
        quizViewModel.refreshGame();
        quizView.reset();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.list:
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                return true;

            case R.id.settings:
                Intent intent1 = new Intent(MainActivity.this, settingsActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

}