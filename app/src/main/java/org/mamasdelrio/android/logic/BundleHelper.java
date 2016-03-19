package org.mamasdelrio.android.logic;

import android.os.Bundle;

import org.mamasdelrio.android.util.BundleKeys;
import org.mamasdelrio.android.util.LocationStruct;

/**
 * Manages adding and retrieving key value pairs to/from {@link Bundle}s.
 */
public class BundleHelper {
  public void addLocationStructToBundle(Bundle bundle,
      LocationStruct locationStruct) {
    bundle.putDouble(BundleKeys.Location.LAT, locationStruct.getLat());
    bundle.putDouble(BundleKeys.Location.LNG, locationStruct.getLng());
    bundle.putDouble(BundleKeys.Location.ALTITUDE,
        locationStruct.getAltitude());
    bundle.putFloat(BundleKeys.Location.ACCURACY, locationStruct.getAccuracy());
  }

  public LocationStruct getLocationStructFromBundle(Bundle bundle) {
    double lat = bundle.getDouble(BundleKeys.Location.LAT);
    double lng = bundle.getDouble(BundleKeys.Location.LNG);
    double alt = bundle.getDouble(BundleKeys.Location.ALTITUDE);
    float acc = bundle.getFloat(BundleKeys.Location.ACCURACY);
    LocationStruct result = new LocationStruct(lat, lng, alt, acc);
    return result;
  }
}
