package com.example.mapme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class jobSchedulerActivity extends AppCompatActivity {
    EditText editText; Button scheduleJobButton; Button cancelJobButton;
    private static final String TAG = "jobSchedulerActivity"; Integer elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);

        editText = findViewById(R.id.editText);
        scheduleJobButton = findViewById(R.id.button5);
        cancelJobButton = findViewById(R.id.button6);


        scheduleJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                elapsedTime = Integer.parseInt(s);

                scheduleJob();
                Toast.makeText(jobSchedulerActivity.this, "Job is Scheduled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        cancelJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJob();
                Toast.makeText(jobSchedulerActivity.this, "Job is Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void scheduleJob(){
        //Using AlarmManager for scheduling a task at elapsed time
        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long alarmExecuteInterval = elapsedTime*1000;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmExecuteInterval, pendingIntent);

    }

    private  void cancelJob(){
        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, employee_list_activity.class);
        startActivity(intent);
    }
}
