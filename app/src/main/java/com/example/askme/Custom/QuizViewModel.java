package com.example.askme.Custom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.askme.Data.States;
import com.example.askme.Database.StatesRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuizViewModel extends AndroidViewModel {

    public StatesRepository statesRepository;
    public LiveData<List<States>> data;
    public MutableLiveData<List<States>> quizdata;
    public QuizViewModel(@NonNull Application application) {
        super(application);
        statesRepository = StatesRepository.getStatesRepository(application);
        loadGame();
    }

    private void loadGame() {
        try{
            quizdata.postValue(statesRepository.getQuizStates().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void refreshGame(){
        loadGame();

    }
}
