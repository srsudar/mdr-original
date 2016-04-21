package org.mamasdelrio.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.logic.DatePickerHelper;
import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.JsonHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.logic.WhatsappSender;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.AbortionView;
import org.mamasdelrio.android.widget.ComplicationsView;
import org.mamasdelrio.android.widget.DeathView;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.LocationView;
import org.mamasdelrio.android.widget.SelectCommunityView;
import org.mamasdelrio.android.widget.SelectOneView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoOutcomeActivity extends AppCompatActivity implements
    IFormActivity {
  private static final String TAG = DoOutcomeActivity.class.getSimpleName();

  @Bind(R.id.outcome_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.outcome_community) SelectCommunityView community;

  // Choose outcome
  @Bind(R.id.outcome_select_outcome) RadioGroup selectOutcome;
  @Bind(R.id.outcome_outcome_complications) RadioButton chooseComplications;
  @Bind(R.id.outcome_outcome_abortion) RadioButton chooseAbortion;
  @Bind(R.id.outcome_outcome_motherdeath) RadioButton chooseMotherDeath;
  @Bind(R.id.outcome_outcome_babydeath) RadioButton chooseBabyDeath;

  @Bind(R.id.outcome_complications) ComplicationsView complications;
  @Bind(R.id.outcome_abortion) AbortionView abortion;
  @Bind(R.id.outcome_babydeath) DeathView babyDeath;
  @Bind(R.id.outcome_motherdeath) DeathView motherDeath;

  @Bind(R.id.outcome_location) LocationView location;
  @Bind(R.id.outcome_send) Button send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_outcome);
    ButterKnife.bind(this);

    babyDeath.getProbableCause().initializeView(R.array.bdeath_cause_labels,
        R.array.bdeath_cause_values);
    motherDeath.getProbableCause().initializeView(R.array.mdeath_cause_labels,
        R.array.mdeath_cause_values);

    complications.setVisibility(View.GONE);
    abortion.setVisibility(View.GONE);
    babyDeath.setVisibility(View.GONE);
    motherDeath.setVisibility(View.GONE);
    send.setVisibility(View.GONE);

    initRadioGroupListener();
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

  private void initRadioGroupListener() {
    selectOutcome.setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
              case R.id.outcome_outcome_complications:
                setAllOutcomeWidgetsGone();
                complications.setVisibility(View.VISIBLE);
                makeSendVisible();
                break;
              case R.id.outcome_outcome_abortion:
                setAllOutcomeWidgetsGone();
                abortion.setVisibility(View.VISIBLE);
                makeSendVisible();
                break;
              case R.id.outcome_outcome_motherdeath:
                setAllOutcomeWidgetsGone();
                motherDeath.setVisibility(View.VISIBLE);
                makeSendVisible();
                break;
              case R.id.outcome_outcome_babydeath:
                setAllOutcomeWidgetsGone();
                babyDeath.setVisibility(View.VISIBLE);
                makeSendVisible();
                break;
              default:
                if (BuildConfig.DEBUG) {
                  Log.e(TAG, "unrecognized checkedId: " + checkedId);
                }
            }
          }
        });
  }

  private void setAllOutcomeWidgetsGone() {
    complications.setVisibility(View.GONE);
    abortion.setVisibility(View.GONE);
    babyDeath.setVisibility(View.GONE);
    motherDeath.setVisibility(View.GONE);
  }

  private void makeSendVisible() {
    if (isReadyToBeSent()) {
      send.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public boolean isReadyToBeSent() {
    return dniOrName.isComplete();
  }

  @Override
  public String getUserFriendlyMessage() {
    int selectedId = selectOutcome.getCheckedRadioButtonId();
    switch (selectedId) {
      case R.id.outcome_outcome_complications:
        return getString(R.string.msg_outcome_complication,
            community.getUserFriendlyCommunityName(),
            complications.getMotherState().getLabelForSelected(),
            complications.getBabyState().getLabelForSelected());
      case R.id.outcome_outcome_abortion:
        return getString(R.string.msg_outcome_abortion,
            community.getUserFriendlyCommunityName());
      case R.id.outcome_outcome_babydeath:
        return getString(R.string.msg_outcome_bdeath,
            community.getUserFriendlyCommunityName());
      case R.id.outcome_outcome_motherdeath:
        return getString(R.string.msg_outcome_mdeath,
            community.getUserFriendlyCommunityName());
      default:
        if (BuildConfig.DEBUG) {
          Log.d(TAG, "unrecognized radio button id: " + selectedId);
        }
        return "";
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
  public void addValuesToMap(Map<String, Object> map,
      TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.OUTCOMES);
    jsonHelper.callAddValuesOnLocationView(map, location);
    addOutcomeChoiceToMap(map);
    DatePickerHelper datePickerHelper = new DatePickerHelper();

    dniOrName.addValuesToMap(map, JsonKeys.Outcomes.HAS_DNI,
        JsonKeys.Outcomes.DNI, JsonKeys.Outcomes.NAME);

    community.addValuesToMap(map, JsonKeys.Outcomes.COMMUNITY);

    complications.addValuesToMap(map, JsonKeys.Outcomes.COMPLICATION_BABY_STATE,
        JsonKeys.Outcomes.COMPLICATION_MOTHER_STATE);
    abortion.addValuesToMap(map, datePickerHelper,
        JsonKeys.Outcomes.ABORTION_DATE);
    babyDeath.addValuesToMap(map, datePickerHelper,
        JsonKeys.Outcomes.BDEATH_CAUSE, JsonKeys.Outcomes.BDEATH_DATE);
    motherDeath.addValuesToMap(map, datePickerHelper,
        JsonKeys.Outcomes.MDEATH_CAUSE, JsonKeys.Outcomes.MDEATH_DATE);
  }

  private void addOutcomeChoiceToMap(Map<String, Object> map) {
    int selectedId = selectOutcome.getCheckedRadioButtonId();
    String outcomeKey = JsonKeys.Outcomes.OUTCOME_TYPE;
    switch (selectedId) {
      case R.id.outcome_outcome_complications:
        map.put(outcomeKey, JsonValues.Outcomes.COMPLICATION);
        break;
      case R.id.outcome_outcome_abortion:
        map.put(outcomeKey, JsonValues.Outcomes.ABORTION);
        break;
      case R.id.outcome_outcome_babydeath:
        map.put(outcomeKey, JsonValues.Outcomes.BABY_DEATH);
        break;
      case R.id.outcome_outcome_motherdeath:
        map.put(outcomeKey, JsonValues.Outcomes.MOTHER_DEATH);
        break;
      default:
        if (BuildConfig.DEBUG) {
          Log.d(TAG, "unrecognized radio button id: " + selectedId);
        }
    }
  }


  @SuppressWarnings("unused")
  @OnClick(R.id.outcome_send)
  public void onSendClick(View view) {
    WhatsappSender sender = new WhatsappSender();
    sender.sendMessage(this, this, WhatsappSender.MessageRecipient.DOCTORS);
  }
}
