package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.DoPregnancyActivity;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.testutil.AssertionHelper;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.LocationView;
import org.mamasdelrio.android.widget.SelectOneView;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
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

  @Test
  public void addValuesToMapCorrect() {
    Map<String, Object> map = new HashMap<>();
    TimeStamper tsMock = mock(TimeStamper.class);
    DniOrNameView dniOrNameViewMock = mock(DniOrNameView.class);
    SelectOneView selectOneViewMock = mock(SelectOneView.class);
    activity.dniOrName = dniOrNameViewMock;
    activity.alarm = selectOneViewMock;
    String targetTime = "target time";
    when(tsMock.getFriendlyDateTime()).thenReturn(targetTime);
    LocationView locationViewMock = mock(LocationView.class);
    activity.location = locationViewMock;

    activity.addValuesToMap(map, tsMock);

    // Things we expect to happen in the map:
    // 1. add the form key
    // 2. add the timestamp
    // 3. add the version
    // 4. call values on dniOrName
    // 5. call values on alarm
    // 6. call values on location

    // 1, 2, and 3
    assertThat(map).contains(
        entry(JsonKeys.SharedKeys.FORM, JsonValues.Forms.ALARMS),
        entry(JsonKeys.SharedKeys.DATETIME, targetTime),
        entry(JsonKeys.SharedKeys.VERSION, Constants.VERSION));

    // 4
    verify(dniOrNameViewMock, times(1)).addValuesToMap(eq(map),
        eq(JsonKeys.Alarms.HAS_DNI), eq(JsonKeys.Alarms.DNI),
        eq(JsonKeys.Alarms.NAME));

    // 5
    verify(selectOneViewMock, times(1)).addValuesToMap(eq(map),
        eq(JsonKeys.Alarms.ALARM));

    // 6
    AssertionHelper.assertAddValuesCalledOnLocationView(locationViewMock, map);
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
