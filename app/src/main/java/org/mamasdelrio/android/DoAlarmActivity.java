package org.mamasdelrio.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.JsonHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.logic.WhatsappSender;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.LocationView;
import org.mamasdelrio.android.widget.SelectCommunityView;
import org.mamasdelrio.android.widget.SelectOneView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoAlarmActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.alarm_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.alarm_community) SelectCommunityView community;
  @Bind(R.id.alarm_alarm) SelectOneView alarm;
  @Bind(R.id.alarm_send) Button send;
  @Bind(R.id.alarm_location) LocationView location;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_alarm);
    ButterKnife.bind(this);

    alarm.initializeView(R.array.alarms_labels, R.array.alarms_values);
    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public void onResume() {
    super.onResume();
    location.setLaunchingActivity(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
    if (requestCode == Constants.RequestCodes.GET_LOCATION) {
      location.handleResultIntent(data.getExtras(), new BundleHelper());
    } else if (requestCode == Constants.RequestCodes.SEND_TO_WHATSAPP) {
      finish();
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
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
  public String getUserFriendlyMessage() {
    return getString(R.string.msg_alarm,
        community.getUserFriendlyCommunityName());
  }

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.ALARMS);
    jsonHelper.callAddValuesOnLocationView(map, location);

    dniOrName.addValuesToMap(map, JsonKeys.Alarms.HAS_DNI, JsonKeys.Alarms.DNI,
        JsonKeys.Alarms.NAME);
    community.addValuesToMap(map, JsonKeys.Alarms.COMMUNITY);
    alarm.addValuesToMap(map, JsonKeys.Alarms.ALARM);
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.alarm_send)
  public void onSendClick(View view) {
    WhatsappSender sender = new WhatsappSender();
    sender.sendMessage(this, this, WhatsappSender.MessageRecipient.GROUP);
  }
}
