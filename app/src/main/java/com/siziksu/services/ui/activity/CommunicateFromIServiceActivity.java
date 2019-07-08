package com.siziksu.services.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.siziksu.services.R;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.Commons;
import com.siziksu.services.commons.DeviceManager;
import com.siziksu.services.commons.mock.Mock;
import com.siziksu.services.data.service.CommunicateFromIService;

public class CommunicateFromIServiceActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Button buttonStart;

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null && intent.getExtras()
                    .containsKey(Constants.EXTRAS_MESSAGE)) {
                String message = intent.getStringExtra(Constants.EXTRAS_MESSAGE);
                Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, message);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_button);
        ((TextView) findViewById(R.id.activityTitle)).setText(
                getIntent().getStringExtra(Constants.EXTRAS_TITLE));
        ((TextView) findViewById(R.id.activitySummary)).setText(
                getIntent().getStringExtra(Constants.EXTRAS_SUMMARY));
        buttonStart = findViewById(R.id.btnStartService);
        buttonStart.setOnClickListener(this);
        DeviceManager.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_FILES_DOWNLOADED);
        registerReceiver(intentReceiver, filter);
        if (DeviceManager.getInstance().isServiceRunning(Constants.TAG_COMMUNICATE_FROM_SERVICE)) {
            buttonStart.setEnabled(false);
            Toast.makeText(this, Constants.SERVICE_RUNNING, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                buttonStart.setEnabled(false);
                startService();
                break;
        }
    }

    private void startService() {
        Commons.log(Constants.TAG_COMMUNICATE_FROM_SERVICE, Constants.SERVICE_STARTING);
        startService(Mock.getInstance()
                .putUrls(new Intent(getBaseContext(), CommunicateFromIService.class)));
    }
}
