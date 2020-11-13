package com.example.askme.Database;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.askme.Data.States;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {States.class}, version = 1, exportSchema = false)
public abstract class StatesDatabase extends RoomDatabase {
    public abstract StatesDao statesDao();

    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static StatesDatabase INSTANCE = null;

    public static StatesDatabase getINSTANCE(final Context context) {
        if (INSTANCE == null) {
            synchronized (StatesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, StatesDatabase.class, "states_db").addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    prepopulateDB(context.getAssets(), INSTANCE.statesDao());
                                }
                            });
                        }
                    })
                            .fallbackToDestructiveMigration().build();
                }
            }
        } return INSTANCE;
    }
    private static void prepopulateDB(AssetManager assetManager, StatesDao statesDao) {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";

        try{
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("state-capital.json")));
            String mLine;
            while((mLine = bufferedReader.readLine())!=null);{
                stringBuilder.append(mLine);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bufferedReader!=null){
                try{
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try{
            JSONObject states = new JSONObject(json);
            JSONObject section = states.getJSONObject("sections");
            prepopulatefromJson(section.getJSONArray("States(A-L)"),statesDao);
            prepopulatefromJson(section.getJSONArray("States(M-Z)"),statesDao);
            prepopulatefromJson(section.getJSONArray("Union Territories"),statesDao);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void prepopulatefromJson(JSONArray states, StatesDao statesDao) {
        try{
            for (int i=0;i<states.length();i++){
                JSONObject stateData = states.getJSONObject(i);
                String stateName = stateData.getString("key");
                String capitalName = stateData.getString("val");
                statesDao.insertStates(new States(stateName,capitalName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
