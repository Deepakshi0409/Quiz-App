package com.example.askme.Database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.askme.Data.States;

import java.util.List;

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

    @RawQuery(observedEntities = States.class)
    DataSource.Factory<Integer,States> getAllSortedStates(SupportSQLiteQuery query);

    @Query("SELECT DISTINCT * FROM  State ORDER BY RANDOM() LIMIT 4")
    List<States> getQuizStates();
}
