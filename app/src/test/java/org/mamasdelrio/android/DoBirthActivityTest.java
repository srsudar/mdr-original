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
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.LocationView;
import org.mamasdelrio.android.widget.SelectCommunityView;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    assertThat(activity.dniOrName)
        .isEnabled()
        .isVisible();
    assertThat(activity.community)
        .isEnabled()
        .isVisible();
    assertThat(activity.birthDate)
        .isEnabled()
        .isVisible();
    //The Button should start disabled.
    assertThat(activity.send)
        .hasText(R.string.send_to_whatsapp_group)
        .isDisabled();
    assertThat(activity.location).isVisible();
  }

  @Test
  public void buttonEnablesDisablesAsAppropriate() {
    // We're going to swap this out
    DniOrNameView dniOrNameView = mock(DniOrNameView.class);
    when(dniOrNameView.isComplete()).thenReturn(false);
    activity.dniOrName = dniOrNameView;
    activity.onDniOrNameClicked(null);
    // Should start out disabled.
    assertSendButtonIsDisabled();

    when(dniOrNameView.isComplete()).thenReturn(true);
    activity.dniOrName = dniOrNameView;
    activity.onDniOrNameClicked(null);
    assertSendButtonIsEnabled();
  }

  @Test
  public void getUserFriendlyMessageCorrect() {
    String actual = activity.getUserFriendlyMessage();
    assertThat(actual)
        .contains("Hola Mamás del Río. Tenemos un recién nacido en")
        .doesNotContain("$");
  }

  @Test
  public void addValuesToMapCorrect() {
    String targetDateTime = "test datetime";
    String targetBirthDate = "happy birthdate";
    TimeStamper timeStamperMock = mock(TimeStamper.class);
    when(timeStamperMock.getFriendlyDateTime()).thenReturn(targetDateTime);
    DatePickerHelper dphMock = mock(DatePickerHelper.class);
    when(dphMock.getFriendlyString(activity.birthDate)).thenReturn(
        targetBirthDate);
    DniOrNameView dniOrNameMock = mock(DniOrNameView.class);
    activity.dniOrName = dniOrNameMock;
    SelectCommunityView communityMock = mock(SelectCommunityView.class);
    activity.community = communityMock;
    activity.setDatePickerHelper(dphMock);
    LocationView locationViewMock = mock(LocationView.class);
    activity.location = locationViewMock;

    Map<String, Object> map = new HashMap<>();
    activity.addValuesToMap(map, timeStamperMock);
    AssertionHelper.assertCommonKeysPresent(map, targetDateTime,
        JsonValues.Forms.BIRTHS);
    verify(dniOrNameMock, times(1)).addValuesToMap(map,
        JsonKeys.Births.HAS_DNI, JsonKeys.Births.DNI, JsonKeys.Births.NAME);
    verify(communityMock, times(1)).addValuesToMap(map,
        JsonKeys.Births.COMMUNITY);
    assertThat(map).contains(
        entry(JsonKeys.Births.BIRTH_DATE, targetBirthDate));
    AssertionHelper.assertAddValuesCalledOnLocationView(locationViewMock, map);
  }

  private void assertSendButtonIsDisabled() {
    assertThat(activity.send).isDisabled();
  }

  private void assertSendButtonIsEnabled() {
    assertThat(activity.send).isEnabled();
  }
}
