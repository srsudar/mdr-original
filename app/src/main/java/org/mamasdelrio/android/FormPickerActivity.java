package org.mamasdelrio.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FormPickerActivity extends AppCompatActivity {
  @Bind(R.id.do_pregnancy) Button doPregnancy;
  @Bind(R.id.do_alarm) Button doAlarm;
  @Bind(R.id.do_birth) Button doBirth;
  @Bind(R.id.do_risk) Button doRisk;
  @Bind(R.id.do_outcome) Button doOutcome;

  private Button pregnancyButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_form_picker);
    if (BuildConfig.DEBUG) {
      ButterKnife.setDebug(true);
    }
    ButterKnife.bind(this);

    pregnancyButton = (Button) findViewById(R.id.do_pregnancy);

    initListeners();
  }

  private void initListeners() {
    addNotImplementedListener(alarmButton);
    addNotImplementedListener(pregnancyButton);
//    addNotImplementedListener(deathButton);

//    birthButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent intent = new Intent();
//        intent.setClass(getApplicationContext(), DoBirthActivity.class);
//        startActivity(intent);
//      }
//    });
  }

  /**
   * Attach a {@link android.view.View.OnClickListener} saying not implemented.
   * @param view
   */
  private void addNotImplementedListener(View view) {
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(
            FormPickerActivity.this,
            R.string.msg_unimplemented,
            Toast.LENGTH_LONG).show();
      }
    });
  }
}
