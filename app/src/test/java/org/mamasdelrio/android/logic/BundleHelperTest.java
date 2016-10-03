package org.mamasdelrio.android.logic;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.DoAlarmActivity;
import org.mamasdelrio.android.util.BundleKeys;
import org.mamasdelrio.android.util.LocationStruct;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BundleHelper}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class BundleHelperTest {
  BundleHelper bundleHelper;

  @Before
  public void before() {
    bundleHelper = new BundleHelper();
  }

  @Test
  public void addsLocationStructToBundle() {
    double lat = 128.2;
    double lng = 18.1;
    double alt = 11871.0;
    float acc = 5;
    LocationStruct locationStruct = new LocationStruct(lat, lng, alt, acc);
    Bundle bundle = new Bundle();

    bundleHelper.addLocationStructToBundle(bundle, locationStruct);

    assertThat(bundle).hasKey(BundleKeys.Location.LAT);
    assertThat(bundle).hasKey(BundleKeys.Location.LNG);
    assertThat(bundle).hasKey(BundleKeys.Location.ALTITUDE);
    assertThat(bundle).hasKey(BundleKeys.Location.ACCURACY);

    double actualLat = bundle.getDouble(BundleKeys.Location.LAT);
    double actualLng = bundle.getDouble(BundleKeys.Location.LNG);
    double actualAlt = bundle.getDouble(BundleKeys.Location.ALTITUDE);
    double actualAcc = bundle.getFloat(BundleKeys.Location.ACCURACY);

    assertThat(actualLat).isEqualTo(lat);
    assertThat(actualLng).isEqualTo(lng);
    assertThat(actualAlt).isEqualTo(alt);
    assertThat(actualAcc).isEqualTo(acc);
  }

  @Test
  public void retrievesLocationStructFromBundle() {
    LocationStruct target = new LocationStruct(1912.0, 18871.912, 1881.12,
        2.5f);
    Bundle bundle = new Bundle();
    // Going to just ask the helper to add them first rather than implement the
    // add code twice.
    bundleHelper.addLocationStructToBundle(bundle, target);
    LocationStruct actual = bundleHelper.getLocationStructFromBundle(bundle);
    assertThat(actual).isEqualTo(target);
  }
}
