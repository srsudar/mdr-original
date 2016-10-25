package org.mamasdelrio.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormPickerActivity extends AppCompatActivity {
  @Bind(R.id.do_pregnancy) Button doPregnancy;
  @Bind(R.id.do_alarm) Button doAlarm;
  @Bind(R.id.do_birth) Button doBirth;
  @Bind(R.id.do_risk) Button doRisk;
  @Bind(R.id.do_outcome) Button doOutcome;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_picker);
    ButterKnife.bind(this);
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.do_pregnancy)
  protected void doPregnancy() {
    Intent intent = new Intent();
    intent.setClass(this, DoPregnancyActivity.class);
    startActivity(intent);
    Log.d("FormPickerActivity", "pressed doPregnancy");
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.do_alarm)
  protected void doAlarm() {
    Intent intent = new Intent();
    intent.setClass(this, DoAlarmActivity.class);
    startActivity(intent);
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.do_birth)
  protected void doBirth() {
    Intent intent = new Intent();
    intent.setClass(this, DoBirthActivity.class);
    startActivity(intent);
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.do_risk)
  protected void doRisk() {
    Intent intent = new Intent();
    intent.setClass(this, DoRiskActivity.class);
    startActivity(intent);
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.do_outcome)
  protected void doOutcome() {
    Intent intent = new Intent();
    intent.setClass(this, DoOutcomeActivity.class);
    startActivity(intent);
  }
}
