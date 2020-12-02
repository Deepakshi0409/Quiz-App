package com.example.askme.Settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.askme.Notifications.NotificationWorker;
import com.example.askme.R;

import java.security.Key;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SettingsFragment extends PreferenceFragmentCompat {
    private String NOTIFICATION_WORK = "Work";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings1,rootKey);

        SwitchPreference switchPreference = findPreference("notification_pref");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Long current = System.currentTimeMillis();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,16);
                calendar.set(Calendar.MINUTE,30);
                calendar.set(Calendar.SECOND,0);

                if (calendar.getTimeInMillis() < current){
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }

                final WorkManager manager = WorkManager.getInstance(requireActivity());
                final PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(
                        NotificationWorker.class,
                        1,
                        TimeUnit.DAYS
                );
                workRequestBuilder.setInitialDelay(calendar.getTimeInMillis()-current, TimeUnit.MILLISECONDS);
                Boolean notificationStatus =  (Boolean) newValue;
                if (notificationStatus){
                    manager.enqueueUniquePeriodicWork(NOTIFICATION_WORK, ExistingPeriodicWorkPolicy.REPLACE,workRequestBuilder.build());
                }
                else{
                    manager.cancelUniqueWork(NOTIFICATION_WORK);
                }
                return true;
            }
        });
    }
}
