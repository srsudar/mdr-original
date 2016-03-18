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

import org.mamasdelrio.android.widget.AbortionView;
import org.mamasdelrio.android.widget.ComplicationsView;
import org.mamasdelrio.android.widget.DeathView;
import org.mamasdelrio.android.widget.SelectOneView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoOutcomeActivity extends AppCompatActivity {
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
}
