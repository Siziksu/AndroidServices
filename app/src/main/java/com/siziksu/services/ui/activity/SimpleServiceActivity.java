package com.siziksu.services.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.siziksu.services.R;
import com.siziksu.services.common.Commons;
import com.siziksu.services.common.Constants;
import com.siziksu.services.common.DeviceManager;
import com.siziksu.services.service.SimpleService;

public class SimpleServiceActivity extends AppCompatActivity implements View.OnClickListener {

  private Button buttonStart;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_one_button);
    ((TextView) findViewById(R.id.activityTitle)).setText(getIntent().getStringExtra(Constants.EXTRAS_TITLE));
    ((TextView) findViewById(R.id.activitySummary)).setText(getIntent().getStringExtra(Constants.EXTRAS_SUMMARY));
    buttonStart = (Button) findViewById(R.id.btnStartService);
    buttonStart.setOnClickListener(this);
    DeviceManager.init(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (DeviceManager.getInstance().isServiceRunning(Constants.TAG_SIMPLE_SERVICE)) {
      buttonStart.setEnabled(false);
      Toast.makeText(this, Constants.SERVICE_RUNNING, Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
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
    Commons.log(Constants.TAG_SIMPLE_SERVICE, Constants.SERVICE_STARTING);
    startService(new Intent(getBaseContext(), SimpleService.class));
  }
}
