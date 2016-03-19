package org.mamasdelrio.android;

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

import org.mamasdelrio.android.logic.DatePickerHelper;
import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.JsonHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.AbortionView;
import org.mamasdelrio.android.widget.ComplicationsView;
import org.mamasdelrio.android.widget.DeathView;
import org.mamasdelrio.android.widget.SelectOneView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoOutcomeActivity extends AppCompatActivity implements
    IFormActivity {
  private static final String TAG = DoOutcomeActivity.class.getSimpleName();

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

  @Bind(R.id.outcome_send) Button send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_outcome);
    ButterKnife.bind(this);

    complications.setVisibility(View.GONE);
    abortion.setVisibility(View.GONE);
    babyDeath.setVisibility(View.GONE);
    motherDeath.setVisibility(View.GONE);
    send.setVisibility(View.GONE);

    babyDeath.getDniLabel().setText(R.string.enter_dni_of_mother);
    motherDeath.getDniLabel().setText(R.string.enter_dni);

    initRadioGroupListener();
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
    send.setVisibility(View.VISIBLE);
  }

  @Override
  public boolean isReadyToBeSent() {
    return false;
  }

  @Override
  public void addValuesToMap(Map<String, Object> map,
      TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.OUTCOMES);
    addOutcomeChoiceToMap(map);
    DatePickerHelper datePickerHelper = new DatePickerHelper();

    complications.addValuesToMap(map, JsonKeys.Outcomes.COMPLICATION_BABY_STATE,
        JsonKeys.Outcomes.COMPLICATION_MOTHER_STATE);
    abortion.addValuesToMap(map, datePickerHelper,
        JsonKeys.Outcomes.ABORTION_DNI, JsonKeys.Outcomes.ABORTION_DATE);
    babyDeath.addValuesToMap(map, datePickerHelper,
        JsonKeys.Outcomes.BDEATH_CAUSE, JsonKeys.Outcomes.BDEATH_DNI,
        JsonKeys.Outcomes.BDEATH_DATE);
    motherDeath.addValuesToMap(map, datePickerHelper,
        JsonKeys.Outcomes.MDEATH_CAUSE, JsonKeys.Outcomes.MDEATH_DNI,
        JsonKeys.Outcomes.MDEATH_DATE);
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
}
