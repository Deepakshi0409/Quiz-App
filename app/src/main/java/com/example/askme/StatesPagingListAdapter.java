package com.example.askme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.askme.Data.States;

public class StatesPagingListAdapter extends PagedListAdapter<States,StatesViewHolder> {
    private ClickListener clickListener;
    protected StatesPagingListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public StatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.activity_list,parent,false);
        return new StatesViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull StatesViewHolder holder, final int position) {
        States currentStates = getItem(position);
        if(currentStates!=null){
            holder.bind(currentStates);
            if(clickListener!=null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.OnitemClick(position,v);
                    }
                });
            }
        }
    }
    public void setOnItemClickListner(ClickListener clickListner){
        this.clickListener=clickListner;
    }

    public interface ClickListener{
        void OnitemClick(int position, View view);
    }
    public States getStatesAtPosition(int position){
        return getItem(position);
    }

    private static DiffUtil.ItemCallback<States> DIFF_CALLBACK =new DiffUtil.ItemCallback<States>() {
        @Override
        public boolean areItemsTheSame(@NonNull States oldItem, @NonNull States newItem) {
            return oldItem.getStateName().equals(newItem.getStateName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull States oldItem, @NonNull States newItem) {
            return oldItem.isStatesEqual(newItem);
        }
    };
}
