package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.DoPregnancyActivity;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DoAlarmActivity}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DoAlarmActivityTest {
  DoAlarmActivity activity;
  String[] alarmsValues;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoAlarmActivity.class);
    alarmsValues = activity.getResources().getStringArray(
        R.array.alarms_values);
  }

  @Test
  public void uiElementsVisible() {
    assertThat(activity.dniOrName)
        .isVisible()
        .isEnabled();
    assertThat(activity.alarm)
        .isVisible()
        .isEnabled();
    assertThat(activity.send)
        .isVisible()
        .isDisabled();
  }

  @Test
  public void alarmSelectOneCorrectSize() {
    assertThat(activity.alarm.getSpinner()).hasCount(alarmsValues.length);
  }

  @Test
  public void isReadyToBeSentCorrect() {
    // We're going to swap this out
    DniOrNameView dniOrNameView = mock(DniOrNameView.class);
    when(dniOrNameView.isComplete()).thenReturn(false);
    activity.dniOrName = dniOrNameView;
    activity.onDniOrNameClicked(null);
    // Should start out disabled.
    assertNotReadyToBeSent();

    when(dniOrNameView.isComplete()).thenReturn(true);
    activity.dniOrName = dniOrNameView;
    activity.onDniOrNameClicked(null);
    assertReadyToBeSent();
  }

  private void assertReadyToBeSent() {
    assertThat(activity.isReadyToBeSent()).isTrue();
    assertThat(activity.send).isEnabled();
  }

  private void assertNotReadyToBeSent() {
    assertThat(activity.isReadyToBeSent()).isFalse();
    assertThat(activity.send).isDisabled();
  }
}
