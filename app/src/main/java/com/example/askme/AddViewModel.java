package com.example.askme;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.askme.Data.States;
import com.example.askme.Database.StatesRepository;

public class AddViewModel extends AndroidViewModel {
    private StatesRepository statesRepository;
    private LiveData<PagedList<States>> data;
    public AddViewModel(@NonNull Application application) {
        super(application);

        statesRepository = StatesRepository.getStatesRepository(application);
        data = statesRepository.getAllStates();
    }
    public void insertState(States states){
        statesRepository.insertStates(states);
    }
    public void updateState(States states){
        statesRepository.updateStates(states);
    }
}
