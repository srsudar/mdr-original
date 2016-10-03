package org.mamasdelrio.android;

import android.content.Intent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
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
    assertThat(intent).hasComponent(activity, DoPregnancyActivity.class);
  }

  @Test
  public void doAlarmStartsActivity() {
    activity.doAlarm.performClick();
    Intent intent = getNextStartedActivity();
    assertThat(intent).hasComponent(activity, DoAlarmActivity.class);
  }

  @Test
  public void doBirthStartsActivity() {
    activity.doBirth.performClick();
    Intent intent = getNextStartedActivity();
    assertThat(intent).hasComponent(activity, DoBirthActivity.class);
  }

  @Test
  public void doRiskStartsActivity() {
    activity.doRisk.performClick();
    Intent intent = getNextStartedActivity();
    assertThat(intent).hasComponent(activity, DoRiskActivity.class);
  }

  @Test
  public void doOutcomeStartsActivity() {
    activity.doOutcome.performClick();
    Intent intent = getNextStartedActivity();
    assertThat(intent).hasComponent(activity, DoOutcomeActivity.class);
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
