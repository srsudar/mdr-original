package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.mamasdelrio.android.logic.DatePickerHelper;
import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.JsonHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class DoBirthActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.birth_enter_dni) EditText dni;
  @Bind(R.id.birth_send) Button send;
  @Bind(R.id.birth_date) DatePicker birthDate;
  private DatePickerHelper datePickerHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_birth);
    ButterKnife.bind(this);
    datePickerHelper = new DatePickerHelper();

    send.setEnabled(isReadyToBeSent());
  }

  public void setDatePickerHelper(DatePickerHelper datePickerHelper) {
    this.datePickerHelper = datePickerHelper;
  }

  @Override
  public boolean isReadyToBeSent() {
    return dni.getText().length() > 0;
  }

  @SuppressWarnings("unused")
  @OnTextChanged(R.id.birth_enter_dni)
  public void onTextChanged(CharSequence text) {
    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.BIRTHS);

    map.put(JsonKeys.Births.DNI, dni.getText().toString());
    map.put(JsonKeys.Births.BIRTH_DATE, datePickerHelper.getFriendlyString(
        birthDate));
  }
}
