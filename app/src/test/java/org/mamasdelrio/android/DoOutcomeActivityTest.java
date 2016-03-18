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
  /** The view subtypes shown in the outcome activity. */
  public enum OutcomeView {
    COMPLICATIONS,
    ABORTION,
    BABY_DEATH,
    MOTHER_DEATH
  }

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
    assertThat(activity.send)
        .isGone()
        .isEnabled();

    assertThat(activity.motherDeath.getDniLabel())
        .hasText(R.string.enter_dni);
    assertThat(activity.babyDeath.getDniLabel())
        .hasText(R.string.enter_dni_of_mother);

    assertThat(activity.complications).isGone();
    assertThat(activity.abortion).isGone();
    assertThat(activity.babyDeath).isGone();
    assertThat(activity.motherDeath).isGone();
  }

  @Test
  public void viewsUpdateVisibility() {
    // The fact that they all start gone must be checked elsewhere.
    activity.chooseComplications.toggle();
    assertOnlyThisViewVisible(OutcomeView.COMPLICATIONS);

    activity.chooseAbortion.toggle();
    assertOnlyThisViewVisible(OutcomeView.ABORTION);

    activity.chooseMotherDeath.toggle();
    assertOnlyThisViewVisible(OutcomeView.MOTHER_DEATH);

    activity.chooseBabyDeath.toggle();
    assertOnlyThisViewVisible(OutcomeView.BABY_DEATH);
  }

  private void assertOnlyThisViewVisible(OutcomeView outcome) {
    if (outcome == OutcomeView.COMPLICATIONS) {
      assertThat(activity.complications).isVisible();
      assertThat(activity.send).isVisible();
    } else {
      assertThat(activity.complications).isGone();
    }

    if (outcome == OutcomeView.ABORTION) {
      assertThat(activity.abortion).isVisible();
      assertThat(activity.send).isVisible();
    } else {
      assertThat(activity.abortion).isGone();
    }

    if (outcome == OutcomeView.BABY_DEATH) {
      assertThat(activity.babyDeath).isVisible();
      assertThat(activity.send).isVisible();
    } else {
      assertThat(activity.babyDeath).isGone();
    }

    if (outcome == OutcomeView.MOTHER_DEATH) {
      assertThat(activity.motherDeath).isVisible();
      assertThat(activity.send).isVisible();
    } else {
      assertThat(activity.motherDeath).isGone();
    }
  }
}
