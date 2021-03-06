package org.mamasdelrio.android;

/*
 * Copyright (C) 2009 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


import java.text.DecimalFormat;
import java.util.List;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.util.LocationStruct;

/**
 * This is taken and lightly modified from ODK Collect's GeoPointActivity. It is
 * essentially an Activity that immediately throws up a dialog, gets the GPS
 * coordinates, and then returns.
 * <p>
 * Original source at:
 * https://github.com/opendatakit/collect/blob/master/collect_app%2Fsrc%2Fmain%2Fjava%2Forg%2Fodk%2Fcollect%2Fandroid%2Factivities%2FGeoPointActivity.java
 *
 */
public class LocationFetcherActivity extends Activity implements
    LocationListener {
  private static final String TAG =
      LocationFetcherActivity.class.getSimpleName();
  private static final String LOCATION_COUNT = "locationCount";
  private static final double DEFAULT_LOCATION_ACCURACY = 10.0;

  private ProgressDialog mLocationDialog;
  private LocationManager mLocationManager;
  private Location mLocation;
  private boolean mGPSOn = false;
  private boolean mNetworkOn = false;
  private double mLocationAccuracy;
  private int mLocationCount = 0;
  private BundleHelper bundleHelper;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState != null) {
      mLocationCount = savedInstanceState.getInt(LOCATION_COUNT);
    }

    mLocationAccuracy = DEFAULT_LOCATION_ACCURACY;
    bundleHelper = new BundleHelper();

    setTitle(getString(R.string.app_name)
        + " > "
        + getString(R.string.get_location));

    mLocationManager = (LocationManager) getSystemService(
        Context.LOCATION_SERVICE);

    // make sure we have a good location provider before continuing
    List<String> providers = mLocationManager.getProviders(true);
    for (String provider : providers) {
      if (provider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
        mGPSOn = true;
      }
      if (provider.equalsIgnoreCase(LocationManager.NETWORK_PROVIDER)) {
        mNetworkOn = true;
      }
    }
    if (!mGPSOn && !mNetworkOn) {
      Toast.makeText(getBaseContext(),
          getString(R.string.provider_disabled_error),
          Toast.LENGTH_SHORT).show();
      finish();
    }

    if (mGPSOn) {
      Location loc = mLocationManager.getLastKnownLocation(
          LocationManager.GPS_PROVIDER);
      if (loc != null) {
        if (BuildConfig.DEBUG) {
          Log.i(TAG, "GeoPointActivity: " + System.currentTimeMillis() +
              " lastKnownLocation(GPS) lat: " +
              loc.getLatitude() + " long: " +
              loc.getLongitude() + " acc: " +
              loc.getAccuracy());
        }
      } else {
        Log.i(TAG, "GeoPointActivity: " + System.currentTimeMillis() +
            " lastKnownLocation(GPS) null location");
      }
    }

    if (mNetworkOn) {
      Location loc = mLocationManager.getLastKnownLocation(
          LocationManager.NETWORK_PROVIDER);
      if (loc != null && BuildConfig.DEBUG) {
        Log.i(TAG, "GeoPointActivity: " + System.currentTimeMillis() +
            " lastKnownLocation(Network) lat: " +
            loc.getLatitude() + " long: " +
            loc.getLongitude() + " acc: " +
            loc.getAccuracy());
      } else {
        if (BuildConfig.DEBUG) {
          Log.i(TAG, "GeoPointActivity: " + System.currentTimeMillis() +
              " lastKnownLocation(Network) null location");
        }
      }
    }

    setupLocationDialog();

  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(LOCATION_COUNT, mLocationCount);
  }

  @Override
  protected void onPause() {
    super.onPause();

    // stops the GPS. Note that this will turn off the GPS if the screen goes to
    // sleep.
    mLocationManager.removeUpdates(this);

    // We're not using managed dialogs, so we have to dismiss the dialog to
    // prevent it from leaking memory.
    if (mLocationDialog != null && mLocationDialog.isShowing())
      mLocationDialog.dismiss();
  }


  @Override
  protected void onResume() {
    super.onResume();
    if (mGPSOn) {
      mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
          0, this);
    }
    if (mNetworkOn) {
      mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
          0, 0, this);
    }
    mLocationDialog.show();
  }

  /**
   * Sets up the look and actions for the progress dialog while the GPS is
   * searching.
   */
  private void setupLocationDialog() {
    // dialog displayed while fetching gps location
    mLocationDialog = new ProgressDialog(this);
    DialogInterface.OnClickListener geopointButtonListener =
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            switch (which) {
              case DialogInterface.BUTTON_POSITIVE:
                returnLocation();
                break;
              case DialogInterface.BUTTON_NEGATIVE:
                mLocation = null;
                finish();
                break;
            }
          }
        };

    // back button doesn't cancel
    mLocationDialog.setCancelable(false);
    mLocationDialog.setIndeterminate(true);
    mLocationDialog.setIcon(android.R.drawable.ic_dialog_info);
    mLocationDialog.setTitle(getString(R.string.getting_location));
    mLocationDialog.setMessage(getString(R.string.please_wait_long));
    mLocationDialog.setButton(DialogInterface.BUTTON_POSITIVE,
        getString(R.string.accept_location),
        geopointButtonListener);
    mLocationDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
        getString(R.string.cancel_location),
        geopointButtonListener);
  }


  private void returnLocation() {
    if (mLocation != null) {
      LocationStruct locationStruct = new LocationStruct(
          mLocation.getLatitude(), mLocation.getLongitude(),
          mLocation.getAltitude(), mLocation.getAccuracy());
      Bundle bundle = new Bundle();
      bundleHelper.addLocationStructToBundle(bundle, locationStruct);
      Intent intent = new Intent();
      intent.putExtras(bundle);
      setResult(RESULT_OK, intent);
    }
    finish();
  }

  public void setBundleHelper(BundleHelper bundleHelper) {
    this.bundleHelper = bundleHelper;
  }


  @Override
  public void onLocationChanged(Location location) {
    mLocation = location;
    if (mLocation != null) {
      // Bug report: cached GeoPoint is being returned as the first value.
      // Wait for the 2nd value to be returned, which is hopefully not cached?
      ++mLocationCount;
      if (BuildConfig.DEBUG) {
        Log.i(TAG, "GeoPointActivity: " + System.currentTimeMillis() +
            " onLocationChanged(" + mLocationCount + ") lat: " +
            mLocation.getLatitude() + " long: " +
            mLocation.getLongitude() + " acc: " +
            mLocation.getAccuracy());
      }

      if (mLocationCount > 1) {
        mLocationDialog.setMessage(
            getString(R.string.location_provider_accuracy,
                mLocation.getProvider(),
                truncateDouble(mLocation.getAccuracy())));

        if (mLocation.getAccuracy() <= mLocationAccuracy) {
          returnLocation();
        }
      }
    } else {
      if (BuildConfig.DEBUG) {
        Log.i(TAG, "GeoPointActivity: " + System.currentTimeMillis() +
            " onLocationChanged(" + mLocationCount + ") null location");
      }
    }
  }


  private String truncateDouble(float number) {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(number);
  }


  @Override
  public void onProviderDisabled(String provider) {

  }


  @Override
  public void onProviderEnabled(String provider) {

  }


  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    switch (status) {
      case LocationProvider.AVAILABLE:
        if (mLocation != null) {
          mLocationDialog.setMessage(getString(R.string.location_accuracy,
              mLocation.getAccuracy()));
        }
        break;
      case LocationProvider.OUT_OF_SERVICE:
        break;
      case LocationProvider.TEMPORARILY_UNAVAILABLE:
        break;
    }
  }

}