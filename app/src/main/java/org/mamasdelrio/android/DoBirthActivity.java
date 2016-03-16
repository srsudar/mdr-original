package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.IntegerArrayAdapter;
import org.mamasdelrio.android.util.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class DoBirthActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.birth_enter_dni) EditText dni;
  @Bind(R.id.birth_enter_year) EditText year;
  @Bind(R.id.birth_enter_month) Spinner month;
  @Bind(R.id.birth_enter_day) Spinner day;
  @Bind(R.id.birth_send) Button send;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_birth);
    ButterKnife.bind(this);

    IntegerArrayAdapter monthAdapter = new IntegerArrayAdapter(
        this,
        Constants.MAX_MONTH);
    month.setAdapter(monthAdapter);

    IntegerArrayAdapter dayAdapter = new IntegerArrayAdapter(
        this,
        Constants.MAX_DAY);
    day.setAdapter(dayAdapter);

    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public boolean isReadyToBeSent() {
    return dni.getText().length() > 0 && year.getText().length() > 0;
  }

  @SuppressWarnings("unused")
  @OnTextChanged({ R.id.birth_enter_dni, R.id.birth_enter_year })
  public void onTextChanged(CharSequence text) {
    send.setEnabled(isReadyToBeSent());
  }
}
