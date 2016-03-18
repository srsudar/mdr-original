package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class DoOutcomeActivityTest {
  DoOutcomeActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoOutcomeActivity.class);
  }

  @Test
  public void uiElementsInitialized() {
    assertThat(activity.selectOutcome)
        .isVisible()
        .isEnabled();
    assertThat(activity.chooseComplications)
        .isVisible()
        .isEnabled();
    assertThat(activity.chooseAbortion)
        .isVisible()
        .isEnabled();
    assertThat(activity.chooseMotherDeath)
        .isVisible()
        .isEnabled();
    assertThat(activity.chooseBabyDeath)
        .isVisible()
        .isEnabled();

    assertThat(activity.complications).isGone();
    assertThat(activity.abortion).isGone();
    assertThat(activity.babyDeath).isGone();
    assertThat(activity.motherDeath).isGone();
  }
}
