package org.mamasdelrio.android.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.LocationFetcherActivity;
import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.util.LocationStruct;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link LocationView}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LocationViewTest {
  LocationView view;

  @Before
  public void before() {
    view = new LocationView(RuntimeEnvironment.application);
  }

  @Test
  public void uiElementsInitialized() {
    assertThat(view.lat).isVisible();
    assertThat(view.lng).isVisible();
    assertThat(view.altitude).isVisible();
    assertThat(view.accuracy).isVisible();
    assertThat(view.acquire).isVisible();
  }

  @Test
  public void updateViewWithLocationUpdatesViews() {
    double lat = 18712.2;
    double lng = 1878711.81;
    double alt = 9871.9;
    float acc = 11.2f;
    LocationStruct locationStruct = new LocationStruct(lat, lng, alt, acc);

    String latTarget = Double.toString(lat);
    String lngTarget = Double.toString(lng);
    String altTarget = Double.toString(alt);
    String accTarget = Float.toString(acc);

    view.updateViewWithLocation(locationStruct);

    assertThat(view.lat).hasText(latTarget);
    assertThat(view.lng).hasText(lngTarget);
    assertThat(view.altitude).hasText(altTarget);
    assertThat(view.accuracy).hasText(accTarget);
  }

  @Test
  public void addValuesToMapCorrect() {
    Map<String, Object> map = new HashMap<>();
    String latKey = "latKey";
    String lngKey = "lngKey";
    String altKey = "altKey";
    String accKey = "accKey";

    view.addValuesToMap(map, latKey, lngKey, altKey, accKey);
    assertThat(map).contains(
        entry(latKey, ""),
        entry(lngKey, ""),
        entry(altKey, ""),
        entry(accKey, ""));

    double lat = 712.2;
    double lng = 78711.81;
    double alt = 71.9;
    float acc = 1.2f;
    String latTarget = Double.toString(lat);
    String lngTarget = Double.toString(lng);
    String altTarget = Double.toString(alt);
    String accTarget = Float.toString(acc);

    view.lat.setText(latTarget);
    view.lng.setText(lngTarget);
    view.altitude.setText(altTarget);
    view.accuracy.setText(accTarget);

    map = new HashMap<>();
    view.addValuesToMap(map, latKey, lngKey, altKey, accKey);
    assertThat(map).contains(
        entry(latKey, latTarget),
        entry(lngKey, lngTarget),
        entry(altKey, altTarget),
        entry(accKey, accTarget));
  }

  @Test
  public void clickingAcquireLaunchesActivity() {
    // Clicking without an Activity shouldn't crash it.
    view.acquire.performClick();

    Activity activity = Robolectric.setupActivity(Activity.class);
    view.setLaunchingActivity(activity);

    view.acquire.performClick();
    Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
    Assertions.assertThat(intent).hasComponent(activity,
        LocationFetcherActivity.class);
  }

  @Test
  public void handleResultIntentUpdatesView() {
    LocationView viewSpy = spy(view);
    Bundle bundle = new Bundle();
    BundleHelper bundleHelperMock = mock(BundleHelper.class);
    LocationStruct locationStruct = new LocationStruct(1.0, 2.0, 3.0, 1f);
    when(bundleHelperMock.getLocationStructFromBundle(bundle))
        .thenReturn(locationStruct);

    viewSpy.handleResultIntent(bundle, bundleHelperMock);
    verify(viewSpy, times(1)).updateViewWithLocation(locationStruct);
  }
}
