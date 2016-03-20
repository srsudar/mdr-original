package org.mamasdelrio.android.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mamasdelrio.android.LocationFetcherActivity;
import org.mamasdelrio.android.R;
import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.LocationStruct;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A {@link android.view.View} for getting a user's location.
 */
public class LocationView extends LinearLayout {
  @Bind(R.id.location_lat) TextView lat;
  @Bind(R.id.location_lng) TextView lng;
  @Bind(R.id.location_alt) TextView altitude;
  @Bind(R.id.location_acc) TextView accuracy;
  @Bind(R.id.location_acquire) Button acquire;
  /** Activity that is used to launch the {@link LocationFetcherActivity}. */
  Activity launchingActivity;

  public LocationView(Context context) {
    super(context);
    init();
  }

  public LocationView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public LocationView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void updateViewWithLocation(LocationStruct locationStruct) {
    lat.setText(Double.toString(locationStruct.getLat()));
    lng.setText(Double.toString(locationStruct.getLng()));
    altitude.setText(Double.toString(locationStruct.getAltitude()));
    accuracy.setText(Float.toString(locationStruct.getAccuracy()));
  }

  /**
   * Should be called in {@link Activity#onActivityResult(int, int, Intent)} if
   * the result is RESULT_OK.
   */
  public void handleResultIntent(Bundle bundle, BundleHelper bundleHelper) {
    LocationStruct locationStruct = bundleHelper.getLocationStructFromBundle(
        bundle);
    updateViewWithLocation(locationStruct);
  }

  /**
   * Set the activity that will be used to launch the
   * {@link LocationFetcherActivity}. This activity will be invoked using
   * {@link Activity#startActivityForResult(Intent, int)} and should call
   * {@link #handleResultIntent(Bundle, BundleHelper)} in
   * {@link Activity#onActivityResult(int, int, Intent)}.
   * @param activity
   */
  public void setLaunchingActivity(Activity activity) {
    this.launchingActivity = activity;
  }

  /**
   * Add the values in this view to the map.
   */
  public void addValuesToMap(Map<String, Object> map, String latKey,
      String lngKey, String altKey, String accKey) {
    map.put(latKey, lat.getText().toString());
    map.put(lngKey, lng.getText().toString());
    map.put(altKey, altitude.getText().toString());
    map.put(accKey, accuracy.getText().toString());
  }

  @SuppressWarnings("unused")
  @OnClick(R.id.location_acquire)
  public void acquireLocation() {
    if (launchingActivity == null) {
      // if this hasn't been set, we can't do anything
      return;
    }
    Intent intent = new Intent(getContext(), LocationFetcherActivity.class);
    launchingActivity.startActivityForResult(intent,
        Constants.RequestCodes.GET_LOCATION);
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_location, this,
        true);
    ButterKnife.bind(this);
  }
}
