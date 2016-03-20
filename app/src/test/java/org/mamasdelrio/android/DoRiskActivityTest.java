package org.mamasdelrio.android;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.testutil.AssertionHelper;
import org.mamasdelrio.android.util.BundleKeys;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DoAlarmActivity}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DoRiskActivityTest {
  DoRiskActivity activity;
  String[] riskValues;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoRiskActivity.class);
    riskValues = activity.getResources().getStringArray(R.array.risks_values);
  }

  @Test
  public void uiElementsInitialized() {
    assertThat(activity.dniOrName)
        .isVisible()
        .isEnabled();
    assertThat(activity.risk)
        .isVisible()
        .isEnabled();
    assertThat(activity.send)
        .isVisible()
        .isDisabled();
    assertThat(activity.location).isVisible();
  }
  @Test
  public void riskSelectOneCorrectSize() {
    assertThat(activity.risk.getSpinner()).hasCount(riskValues.length);
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
    DniOrNameView dniOrNameMock = mock(DniOrNameView.class);
    activity.dniOrName = dniOrNameMock;
    SelectOneView riskMock = mock(SelectOneView.class);
    activity.risk = riskMock;
    String targetDateTime = "test stamp";
    TimeStamper timeStamperMock = mock(TimeStamper.class);
    when(timeStamperMock.getFriendlyDateTime()).thenReturn(targetDateTime);
    LocationView locationViewMock = mock(LocationView.class);
    activity.location = locationViewMock;

    Map<String, Object> map = new HashMap<>();
    activity.addValuesToMap(map, timeStamperMock);

    AssertionHelper.assertCommonKeysPresent(map, targetDateTime,
        JsonValues.Forms.RISKS);
    verify(dniOrNameMock, times(1)).addValuesToMap(map, JsonKeys.Risks.HAS_DNI,
        JsonKeys.Risks.DNI, JsonKeys.Risks.NAMES);
    verify(riskMock, times(1)).addValuesToMap(map, JsonKeys.Risks.RISK);
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
