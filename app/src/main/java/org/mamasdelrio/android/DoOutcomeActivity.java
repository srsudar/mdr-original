package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.mamasdelrio.android.widget.SelectOneView;

import butterknife.Bind;

public class DoOutcomeActivity extends AppCompatActivity {
  // Choose outcome
  @Bind(R.id.outcome_select_outcome) RadioGroup selectOutcome;
  @Bind(R.id.outcome_outcome_complications) RadioButton chooseComplications;
  @Bind(R.id.outcome_outcome_abortion) RadioButton chooseAbortion;
  @Bind(R.id.outcome_outcome_motherdeath) RadioButton chooseMotherDeath;
  @Bind(R.id.outcome_outcome_babydeath) RadioButton chooseBabyDeath;

  // Containers
  @Bind(R.id.outcome_complications_container)
  LinearLayout complicationsContainer;
  @Bind(R.id.outcome_abortion_container) LinearLayout abortionContainer;
  @Bind(R.id.outcome_maternaldeath_container)
  LinearLayout maternalDeathContainer;
  @Bind(R.id.outcome_babydeath_container) LinearLayout babyDeathContainer;

  // Complications
  @Bind(R.id.outcome_complication_baby_state) SelectOneView babyState;
  @Bind(R.id.outcome_complication_mother_state) SelectOneView motherState;

  // Abortion
  @Bind(R.id.outcome_abortion_motherdni) EditText abortionDni;
  @Bind(R.id.outcome_abortion_date) DatePicker abortionDate;

  // Maternal death
  @Bind(R.id.outcome_maternaldeath_date) DatePicker maternalDeathDate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_outcome);
  }
}
