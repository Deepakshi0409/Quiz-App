package com.example.askme.Database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.askme.Data.States;

@Dao
public interface StatesDao {
    @Insert
    void insertStates (States states);

    @Update
    void updateStates (States states);

    @Delete
    void deleteStates (States states);

    @Query("Select * from State")
    DataSource.Factory<Integer, States> getAllStates();

    @Query("Select * from State ORDER BY RANDOM() LIMIT 1")
    States getStateForNotification();
}
