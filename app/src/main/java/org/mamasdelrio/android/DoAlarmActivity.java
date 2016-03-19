package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.SelectOneView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoAlarmActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.alarm_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.alarm_alarm) SelectOneView alarm;
  @Bind(R.id.alarm_send) Button send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_alarm);
    ButterKnife.bind(this);

    alarm.initializeView(R.array.alarms_labels, R.array.alarms_values);
    send.setEnabled(isReadyToBeSent());
  }

  @OnClick({ R.id.shared_nameordni_yesno_group, R.id.shared_nameordni_yesno_yes,
      R.id.shared_nameordni_yesno_no })
  public void onDniOrNameClicked(View view) {
    send.setEnabled(isReadyToBeSent());
  }

  @SuppressWarnings("unused")
  @OnTextChanged({ R.id.shared_nameordni_enter_dni,
      R.id.shared_nameordni_enter_names })
  public void onTextChanged(CharSequence text) {
    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public boolean isReadyToBeSent() {
    return dniOrName.isComplete();
  }

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
    map.put(JsonKeys.SharedKeys.FORM, JsonValues.Forms.ALARMS);
    map.put(JsonKeys.SharedKeys.DATETIME, timeStamper.getFriendlyDateTime());
    map.put(JsonKeys.SharedKeys.VERSION, Constants.VERSION);

    dniOrName.addValuesToMap(map, JsonKeys.Alarms.HAS_DNI, JsonKeys.Alarms.DNI,
        JsonKeys.Alarms.NAME);
    alarm.addValuesToMap(map, JsonKeys.Alarms.ALARM);
  }
}
