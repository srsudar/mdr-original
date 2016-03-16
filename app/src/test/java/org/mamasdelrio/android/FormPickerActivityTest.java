package org.mamasdelrio.android;

import android.content.Intent;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import butterknife.ButterKnife;

import static org.assertj.android.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class FormPickerActivityTest {
  FormPickerActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(FormPickerActivity.class);
  }

  @Test
  public void hasAllButtonsWithText() {
    assertThat(activity.doPregnancy)
        .isEnabled()
        .isVisible()
        .hasText(R.string.pregnancies);
    assertThat(activity.doAlarm)
        .isEnabled()
        .isVisible()
        .hasText(R.string.alarms);
    assertThat(activity.doBirth)
        .isEnabled()
        .isVisible()
        .hasText(R.string.births);
    assertThat(activity.doRisk)
        .isEnabled()
        .isVisible()
        .hasText(R.string.risks);
    assertThat(activity.doOutcome)
        .isEnabled()
        .isVisible()
        .hasText(R.string.outcomes);
  }

  @Test
  public void doPregnancyStartsActivity() {
    activity.doPregnancy.performClick();
    Intent intent = getNextStartedActivity();
  }

  @Test
  public void doAlarmStartsActivity() {

  }

  @Test
  public void doBirthStartsActivity() {

  }

  @Test
  public void doRiskStartsActivity() {

  }

  @Test
  public void doOutcomeStartsActivity() {

  }

  /**
   * Returns the next started {@link Intent}. Performs an assertion to ensure
   * that it is not null before returning.
   */
  private Intent getNextStartedActivity() {
    Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
    assertThat(intent).isNotNull();
    return intent;
  }
}
