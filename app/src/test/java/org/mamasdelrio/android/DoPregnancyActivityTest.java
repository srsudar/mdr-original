package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DoPregnancyActivity}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DoPregnancyActivityTest {
  private DoPregnancyActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoPregnancyActivity.class);
  }

  @Test
  public void uiElementsVisible() {
    assertThat(activity.dniOrName)
        .isVisible()
        .isEnabled();
    assertThat(activity.birthDate)
        .isVisible()
        .isEnabled();
    assertThat(activity.lastPeriodKnown)
        .isVisible()
        .isEnabled();
    assertThat(activity.takeControl)
        .isVisible()
        .isEnabled();
    assertThat(activity.controlMonth)
        .isVisible()
        .isEnabled();
    assertThat(activity.lastPeriodDateLabel)
        .isVisible()
        .isDisabled();
    // starts enabled because yes is the 0th position
    assertThat(activity.lastPeriodDate)
        .isVisible()
        .isEnabled();
    assertThat(activity.send)
        .isVisible()
        .isDisabled();
  }

  @Test
  public void selectOnesRightSize() {
    String[] labels = activity.getResources().getStringArray(
        R.array.yes_no_dk_na_labels);
    assertThat(activity.lastPeriodKnown.getSpinner()).hasCount(labels.length);
    assertThat(activity.takeControl.getSpinner()).hasCount(labels.length);
    assertThat(activity.controlMonth).hasCount(
        Constants.NUM_MONTH_IN_PREGNANCY);
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

  @Test
  public void lastPeriodDateDisables() {
    // starts enabled because yes is selected first
    assertThat(activity.lastPeriodDate).isEnabled();
    activity.lastPeriodKnown.getSpinner().setSelection(1);
    assertThat(activity.lastPeriodDate).isDisabled();
    activity.lastPeriodKnown.getSpinner().setSelection(2);
    assertThat(activity.lastPeriodDate).isDisabled();
    activity.lastPeriodKnown.getSpinner().setSelection(3);
    assertThat(activity.lastPeriodDate).isDisabled();
    activity.lastPeriodKnown.getSpinner().setSelection(0);
    assertThat(activity.lastPeriodDate).isEnabled();
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
