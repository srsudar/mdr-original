package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import org.mamasdelrio.android.logic.IFormActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoBirthActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.birth_enter_dni) EditText dni;
  @Bind(R.id.birth_enter_year) EditText year;
  @Bind(R.id.birth_enter_month) Spinner month;
  @Bind(R.id.birth_enter_day) Spinner day;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_birth);
    ButterKnife.bind(this);
  }

  @Override
  public boolean isReadyToBeSent() {
    return false;
  }
}
