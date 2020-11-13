package com.example.askme.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "State")
public class States {
    @PrimaryKey(autoGenerate = true)
    long statesId;
    @ColumnInfo(name = "State")
    String stateName;
    @ColumnInfo(name = "Capital")
    String capitalName;

    public States(long statesId, String stateName, String capitalName) {
        this.statesId = statesId;
        this.stateName = stateName;
        this.capitalName = capitalName;
    }

    @Ignore
    public States(String stateName, String capitalName) {
        this.stateName = stateName;
        this.capitalName = capitalName;
    }

    public long getStatesId() {
        return statesId;
    }

    public void setStatesId(long statesId) {
        this.statesId = statesId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public boolean isStatesEqual(States newStates) {
        return (statesId == newStates.getStatesId() && stateName.equals(newStates.getStateName()) && capitalName.equals(newStates.getCapitalName()));
    }

}
