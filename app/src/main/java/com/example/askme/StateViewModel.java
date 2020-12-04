package com.example.askme;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.example.askme.Data.States;
import com.example.askme.Database.StatesRepository;

public class StateViewModel extends AndroidViewModel {
    private StatesRepository statesRepository;
    public LiveData<PagedList<States>> datas;

    public MutableLiveData<String> sortOrderChanged =  new MutableLiveData<>();
    public StateViewModel(@NonNull Application application) {
        super(application);

        statesRepository = StatesRepository.getStatesRepository(application);
        sortOrderChanged.setValue("statesId");
        datas = Transformations.switchMap(sortOrderChanged, new Function<String, LiveData<PagedList<States>>>() {
            @Override
            public LiveData<PagedList<States>> apply(String input) {
                return statesRepository.getallstates(input);
            }
        });

    }
    public void changesortingOrder(String sortBy){sortOrderChanged.postValue(sortBy);}
    public void inseretStates(States states) {
        statesRepository.insertStates(states);
    }
    public void deleteStates(States states){
        statesRepository.deleteStates(states);
    }
}
