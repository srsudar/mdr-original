package org.mamasdelrio.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.logic.DatePickerHelper;
import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.IntegerArrayAdapter;
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

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoPregnancyActivity extends AppCompatActivity implements
    IFormActivity {
  private static final int YES_POSITION = 0;

  @Bind(R.id.preg_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.preg_community) SelectCommunityView community;
  @Bind(R.id.birth_date) DatePicker birthDate;
  @Bind(R.id.preg_last_period) SelectOneView lastPeriodKnown;
  @Bind(R.id.preg_control_month_label) TextView controlMonthLabel;
  @Bind(R.id.preg_take_control) SelectOneView takeControl;
  @Bind(R.id.preg_control_month) Spinner controlMonth;
  @Bind(R.id.preg_last_period_date_label) TextView lastPeriodDateLabel;
  @Bind(R.id.preg_last_period_date) DatePicker lastPeriodDate;
  @Bind(R.id.preg_location) LocationView location;
  @Bind(R.id.preg_send) Button send;
  private DatePickerHelper datePickerHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_pregnancy);
    ButterKnife.bind(this);
    datePickerHelper = new DatePickerHelper();
    datePickerHelper.setToBirthDateDefaults(birthDate);

    // the last period fields should only be enabled if it is known
    lastPeriodDateLabel.setEnabled(false);
    lastPeriodDate.setEnabled(false);
    send.setEnabled(isReadyToBeSent());

    lastPeriodKnown.initializeView(R.array.yes_no_dk_na_labels,
        R.array.yes_no_dk_na_values);
    takeControl.initializeView(R.array.yes_no_dk_na_labels,
        R.array.yes_no_dk_na_values);

    IntegerArrayAdapter adapter = new IntegerArrayAdapter(this,
        Constants.NUM_MONTH_IN_PREGNANCY);
    controlMonth.setAdapter(adapter);
    adapter.setDropDownViewResource(
        android.R.layout.simple_spinner_dropdown_item);

    lastPeriodKnown.getSpinner().setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view,
              int position, long id) {
            if (position == YES_POSITION) {
              lastPeriodDate.setEnabled(true);
            } else {
              lastPeriodDate.setEnabled(false);
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {
            // no op
          }
        });

    takeControl.getSpinner().setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view,
                int position, long id) {
            if (position == YES_POSITION) {
              controlMonthLabel.setEnabled(true);
              controlMonth.setEnabled(true);
            } else {
              controlMonthLabel.setEnabled(false);
              controlMonth.setEnabled(false);            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {
            // no op
          }
        });
  }

  @Override
  protected void onResume() {
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

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.PREGNANCIES);
    jsonHelper.callAddValuesOnLocationView(map, location);

    dniOrName.addValuesToMap(map, JsonKeys.Pregnancies.HAS_DNI,
        JsonKeys.Pregnancies.DNI, JsonKeys.Pregnancies.NAMES);
    community.addValuesToMap(map, JsonKeys.Pregnancies.COMMUNITY);
    map.put(JsonKeys.Pregnancies.BIRTH_DATE,
        datePickerHelper.getFriendlyString(birthDate));
    lastPeriodKnown.addValuesToMap(map, JsonKeys.Pregnancies.PERIOD_KNOWN);
    takeControl.addValuesToMap(map, JsonKeys.Pregnancies.TAKE_CONTROLS);
    map.put(JsonKeys.Pregnancies.CONTROL_MONTH, getControlMonth());
    map.put(JsonKeys.Pregnancies.PERIOD_DATE,
        datePickerHelper.getFriendlyString(lastPeriodDate));
  }

  /**
   * Get a user-friendly month of control, from 1 - 9.
   * @return
   */
  public int getControlMonth() {
    // We are just going off of position showing 1-9, but being 0 indexed.
    return controlMonth.getSelectedItemPosition() + 1;
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
    return getString(R.string.msg_pregnancy,
        community.getUserFriendlyCommunityName());
  }

  public void setDatePickerHelper(DatePickerHelper datePickerHelper) {
    this.datePickerHelper = datePickerHelper;
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.preg_send)
  public void onSendClick(View view) {
    WhatsappSender sender = new WhatsappSender();
    sender.sendMessage(this, this, WhatsappSender.MessageRecipient.GROUP);
  }
}
