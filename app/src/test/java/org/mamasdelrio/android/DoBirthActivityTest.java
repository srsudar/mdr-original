package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Tests for {@link DoBirthActivity}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DoBirthActivityTest {
  private DoBirthActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoBirthActivity.class);
  }

  @Test
  public void editableElementsInitialized() {
    assertThat(activity.dni)
        .isEnabled()
        .isVisible();
    assertThat(activity.year)
        .isEnabled()
        .isVisible();
    assertThat(activity.month)
        .isEnabled()
        .isVisible();
    assertThat(activity.day)
        .isEnabled()
        .isVisible();
    //The Button should start disabled.
    assertThat(activity.send)
        .hasText(R.string.send_to_whatsapp_group)
        .isDisabled();

    assertThat(activity.month.getAdapter()).hasCount(Constants.MAX_MONTH);
    assertThat(activity.day.getAdapter()).hasCount(Constants.MAX_DAY);
  }

  @Test
  public void buttonEnablesDisablesAsAppropriate() {
    // The button should be disabled when the DNI and year fields are empty.
    assertSendButtonIsDisabled();
    activity.dni.setText("182849191");
    assertSendButtonIsDisabled();
    activity.year.setText("1234");
    assertSendButtonIsEnabled();
    // Now remove some text and make sure it re-disables.
    activity.year.setText("");
    assertSendButtonIsDisabled();
    activity.year.setText("12");
    assertSendButtonIsEnabled();
    activity.dni.setText("");
    assertSendButtonIsDisabled();
    activity.dni.setText("1234");
    assertSendButtonIsEnabled();
  }

  @Test
  public void getMapContentCorrect() {
    String targetDni = "8173613";
    String targetYear = "2005";
    int targetMonth = 12;
    int targetMonthIndex = 11; // -1 b/c spinner is 0 indexed
    int targetDay = 31;
    int targetDayIndex = 30; // -1 b/c spinner is 0 indexed

    // Set up the inputs.
    activity.dni.setText(targetDni);
    activity.year.setText(targetYear);
    activity.month.setSelection(targetMonthIndex);
    activity.day.setSelection(targetDayIndex);

    Map<String, Object> actual = activity.getMapContent();
    assertThat(actual).contains(
        entry(JsonKeys.SharedKeys.FORM, JsonValues.Forms.BIRTHS),
        entry(JsonKeys.SharedKeys.VERSION, Constants.VERSION),
        entry(JsonKeys.Births.DNI, targetDni),
        entry(JsonKeys.Births.BIRTH_YEAR, targetYear),
        entry(JsonKeys.Births.BIRTH_MONTH, targetMonth),
        entry(JsonKeys.Births.BIRTH_DAY, targetDay));
  }

  private void assertSendButtonIsDisabled() {
    assertThat(activity.send).isDisabled();
  }

  private void assertSendButtonIsEnabled() {
    assertThat(activity.send).isEnabled();
  }
}
