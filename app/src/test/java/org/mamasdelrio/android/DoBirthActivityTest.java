package org.mamasdelrio.android;

import android.widget.DatePicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.logic.DatePickerHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.testutil.AssertionHelper;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
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
  public void addValuesToMapCorrect() {
    String targetDni = "8173613";
    String targetDateTime = "test datetime";
    String targetBirthDate = "happy birthdate";
    TimeStamper timeStamperMock = mock(TimeStamper.class);
    when(timeStamperMock.getFriendlyDateTime()).thenReturn(targetDateTime);
    DatePickerHelper dphMock = mock(DatePickerHelper.class);
    when(dphMock.getFriendlyString(activity.birthDate)).thenReturn(
        targetBirthDate);
    activity.setDatePickerHelper(dphMock);
    activity.dni.setText(targetDni);

    Map<String, Object> map = new HashMap<>();
    activity.addValuesToMap(map, timeStamperMock);
    AssertionHelper.assertCommonKeysPresent(map, targetDateTime,
        JsonValues.Forms.BIRTHS);
    assertThat(map).contains(
        entry(JsonKeys.Births.DNI, targetDni),
        entry(JsonKeys.Births.BIRTH_DATE, targetBirthDate));
  }

  private void assertSendButtonIsDisabled() {
    assertThat(activity.send).isDisabled();
  }

  private void assertSendButtonIsEnabled() {
    assertThat(activity.send).isEnabled();
  }
}
