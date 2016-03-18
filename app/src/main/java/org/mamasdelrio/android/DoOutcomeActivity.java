package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_outcome);
    ButterKnife.bind(this);
    
    complications.setVisibility(View.GONE);
    abortion.setVisibility(View.GONE);
    babyDeath.setVisibility(View.GONE);
    motherDeath.setVisibility(View.GONE);
  }
}
