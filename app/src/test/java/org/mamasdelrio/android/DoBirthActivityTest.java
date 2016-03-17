package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.testutil.AssertionHelper;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    assertThat(activity.birthDate)
        .isEnabled()
        .isVisible();
    //The Button should start disabled.
    assertThat(activity.send)
        .hasText(R.string.send_to_whatsapp_group)
        .isDisabled();
  }

  @Test
  public void buttonEnablesDisablesAsAppropriate() {
    // The button should be disabled when the DNI and year fields are empty.
    assertSendButtonIsDisabled();
    activity.dni.setText("182849191");
    assertSendButtonIsEnabled();
    // Now remove some text and make sure it re-disables.
    activity.dni.setText("");
    assertSendButtonIsDisabled();
  }

  @Test
  public void getMapContentCorrect() {
    String targetDni = "8173613";
    int targetYear = 2005;
    int targetMonth = 12;
    int targetMonthIndex = 11; // updateDate months start from 0
    int targetDay = 31;
    String targetDateTime = "test datetime";
    TimeStamper timeStamperMock = mock(TimeStamper.class);
    when(timeStamperMock.getFriendlyDateTime()).thenReturn(targetDateTime);

    // Set up the inputs.
    activity.dni.setText(targetDni);
    activity.birthDate.updateDate(targetYear, targetMonthIndex, targetDay);

    Map<String, Object> actual = activity.getMapContent(timeStamperMock);
    AssertionHelper.assertCommonKeysPresent(actual, targetDateTime);
    assertThat(actual).contains(
        entry(JsonKeys.SharedKeys.FORM, JsonValues.Forms.BIRTHS),
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
