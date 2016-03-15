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

public class FormPickerActivity extends AppCompatActivity {
  private Button alarmButton;
  private Button pregnancyButton;
  private Button birthButton;
  private Button deathButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_picker);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    alarmButton = (Button) findViewById(R.id.do_alarm);
    pregnancyButton = (Button) findViewById(R.id.do_pregnancy);
    birthButton = (Button) findViewById(R.id.do_birth);

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
