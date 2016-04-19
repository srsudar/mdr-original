package org.mamasdelrio.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.logic.DatePickerHelper;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoBirthActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.birth_community) SelectCommunityView community;
  @Bind(R.id.birth_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.birth_send) Button send;
  @Bind(R.id.birth_date) DatePicker birthDate;
  @Bind(R.id.birth_location) LocationView location;
  private DatePickerHelper datePickerHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_birth);
    ButterKnife.bind(this);
    datePickerHelper = new DatePickerHelper();

    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public void onResume() {
    super.onResume();
    // override the default text for the name or dni view
    String labelText = getString(R.string.mother_has_dni);
    dniOrName.setLabelText(labelText);
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
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

  public void setDatePickerHelper(DatePickerHelper datePickerHelper) {
    this.datePickerHelper = datePickerHelper;
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
    return getString(R.string.msg_birth,
        community.getUserFriendlyCommunityName());
  }

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.BIRTHS);
    jsonHelper.callAddValuesOnLocationView(map, location);

    dniOrName.addValuesToMap(map, JsonKeys.Births.HAS_DNI, JsonKeys.Births.DNI,
        JsonKeys.Births.NAME);
    community.addValuesToMap(map, JsonKeys.Births.COMMUNITY);

    map.put(JsonKeys.Births.BIRTH_DATE, datePickerHelper.getFriendlyString(
        birthDate));
  }


  @SuppressWarnings("unused")
  @OnClick(R.id.birth_send)
  public void onSendClick(View view) {
    WhatsappSender sender = new WhatsappSender();
    sender.sendMessage(this, this, WhatsappSender.MessageRecipient.GROUP);
  }
}
