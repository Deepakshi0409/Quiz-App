package com.example.askme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.askme.Data.States;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListActivity extends AppCompatActivity {
    public static final int NEW_DATA_REQUEST_CODE = 1;
    public static final int UPDATE_DATA_REQUEST_CODE = 2;
    public static final String EXTRA_DATA_ID = "extra_notes_id";
    public static final String EXTRA_DATA_STATE = "extra_notes_state";
    public static final String EXTRA_DATA_CAPITAL = "extra_notes_capital";

    private StateViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        FloatingActionButton addbtn = findViewById(R.id.floatingButton);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListActivity.this, AddActivity.class);
                startActivityForResult(i,NEW_DATA_REQUEST_CODE);
            }
        });

        viewModel = new ViewModelProvider(this).get(StateViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final StatesPagingListAdapter statesPagingListAdapter = new StatesPagingListAdapter();
        recyclerView.setAdapter(statesPagingListAdapter);

        viewModel.datas.observe(this, new Observer<PagedList<States>>() {
            @Override
            public void onChanged(PagedList<States> states) {
                statesPagingListAdapter.submitList(states);
            }

        });
        statesPagingListAdapter.setOnItemClickListner(new StatesPagingListAdapter.ClickListener() {
            @Override
            public void OnitemClick(int position, View view) {
                States currentStates = statesPagingListAdapter.getStatesAtPosition(position);
                LaunchUpdateActivity(currentStates);

            }
        });
    }
    private void LaunchUpdateActivity(States states){
        Intent intent = new Intent(ListActivity.this,AddActivity.class);
        intent.putExtra(EXTRA_DATA_ID,states.getStatesId());
        intent.putExtra(EXTRA_DATA_STATE,states.getStateName());
        intent.putExtra(EXTRA_DATA_CAPITAL,states.getCapitalName());
        startActivityForResult(intent,UPDATE_DATA_REQUEST_CODE);
    }
}