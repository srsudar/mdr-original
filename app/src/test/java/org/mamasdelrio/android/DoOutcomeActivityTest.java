package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.logic.DatePickerHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.testutil.AssertionHelper;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.AbortionView;
import org.mamasdelrio.android.widget.ComplicationsView;
import org.mamasdelrio.android.widget.DeathView;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.LocationView;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    assertThat(activity.dniOrName)
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
    // Start by completing the DNI or name view. The send button should remain
    // gone.
    activity.dniOrName.getDniNo().toggle();
    activity.dniOrName.getNameEditText().setText("a test name");
    assertThat(activity.send)
        .isGone();

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

  @Test
  public void addValuesToMapCorrect() {
    String targetDateTime = "test date time";
    TimeStamper timeStamperMock = mock(TimeStamper.class);
    when(timeStamperMock.getFriendlyDateTime()).thenReturn(targetDateTime);
    DniOrNameView dniOrNameMock = mock(DniOrNameView.class);
    ComplicationsView compViewMock = mock(ComplicationsView.class);
    AbortionView abortionViewMock = mock(AbortionView.class);
    DeathView bDeathMock = mock(DeathView.class);
    DeathView mDeathMock = mock(DeathView.class);
    LocationView locationViewMock = mock(LocationView.class);

    activity.dniOrName = dniOrNameMock;
    activity.complications = compViewMock;
    activity.abortion = abortionViewMock;
    activity.babyDeath = bDeathMock;
    activity.motherDeath = mDeathMock;
    activity.location = locationViewMock;

    Map<String, Object> map = new HashMap<>();
    activity.addValuesToMap(map, timeStamperMock);
    AssertionHelper.assertCommonKeysPresent(map, targetDateTime,
        JsonValues.Forms.OUTCOMES);

    verify(dniOrNameMock, times(1)).addValuesToMap(map,
        JsonKeys.Outcomes.HAS_DNI, JsonKeys.Outcomes.DNI,
        JsonKeys.Outcomes.NAME);
    verify(compViewMock, times(1)).addValuesToMap(eq(map),
        eq(JsonKeys.Outcomes.COMPLICATION_BABY_STATE),
        eq(JsonKeys.Outcomes.COMPLICATION_MOTHER_STATE));
    verify(abortionViewMock, times(1)).addValuesToMap(eq(map),
        any(DatePickerHelper.class),
        eq(JsonKeys.Outcomes.ABORTION_DNI),
        eq(JsonKeys.Outcomes.ABORTION_DATE));
    verify(bDeathMock, times(1)).addValuesToMap(eq(map),
        any(DatePickerHelper.class),
        eq(JsonKeys.Outcomes.BDEATH_CAUSE),
        eq(JsonKeys.Outcomes.BDEATH_DNI),
        eq(JsonKeys.Outcomes.BDEATH_DATE));
    verify(mDeathMock, times(1)).addValuesToMap(eq(map),
        any(DatePickerHelper.class),
        eq(JsonKeys.Outcomes.MDEATH_CAUSE),
        eq(JsonKeys.Outcomes.MDEATH_DNI),
        eq(JsonKeys.Outcomes.MDEATH_DATE));
    AssertionHelper.assertAddValuesCalledOnLocationView(locationViewMock, map);
  }

  @Test
  public void addValuesToMapHandlesChoiceCorrect() {
    // need to make sure the values for the radio button are added in all cases
    Map<String, Object> map = new HashMap<>();
    TimeStamper timeStamper = new TimeStamper();
    activity.chooseComplications.toggle();
    activity.addValuesToMap(map, timeStamper);
    assertHasOutcome(map, JsonValues.Outcomes.COMPLICATION);

    activity.chooseAbortion.toggle();
    activity.addValuesToMap(map, timeStamper);
    assertHasOutcome(map, JsonValues.Outcomes.ABORTION);

    activity.chooseBabyDeath.toggle();
    activity.addValuesToMap(map, timeStamper);
    assertHasOutcome(map, JsonValues.Outcomes.BABY_DEATH);

    activity.chooseMotherDeath.toggle();
    activity.addValuesToMap(map, timeStamper);
    assertHasOutcome(map, JsonValues.Outcomes.MOTHER_DEATH);
  }

  private void assertHasOutcome(Map<String, Object> map, String outcomeValue) {
    assertThat(map).contains(
        entry(JsonKeys.Outcomes.OUTCOME_TYPE, outcomeValue));
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
