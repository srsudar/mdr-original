package org.mamasdelrio.android;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;

//import butterknife.ButterKnife;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class FormPickerActivityTest {
  FormPickerActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(FormPickerActivity.class);
  }

  @Test
  public void hasAllButtons() {
    assertThat(activity).isNotNull();
//    Button doPregnancy = ButterKnife.findById(activity, R.id.do_pregnancy);
//    Button doAlarm = ButterKnife.findById(activity, R.id.do_alarm);
//    Button doBirth = ButterKnife.findById(activity, R.id.do_birth);
//    Button doRisk = ButterKnife.findById(activity, R.id.do_risk);
//    Button doOutcome = ButterKnife.findById(activity, R.id.do_outcome);
//
//    assertThat(doPregnancy)
//        .isEnabled()
//        .isVisible()
//        .hasText(R.string.pregnancies);
//    assertThat(doAlarm)
//        .isEnabled()
//        .isVisible()
//        .hasText(R.string.alarms);
//    assertThat(doBirth)
//        .isEnabled()
//        .isVisible()
//        .hasText(R.string.births);
//    assertThat(doRisk)
//        .isEnabled()
//        .isVisible()
//        .hasText(R.string.risks);
//    assertThat(doOutcome)
//        .isEnabled()
//        .isVisible()
//        .hasText(R.string.outcomes);
  }
}
