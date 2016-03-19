package org.mamasdelrio.android.util;

import android.location.Location;

/**
 * A simple struct for managing location data.
 */
public class LocationStruct {
  private double lat;
  private double lng;
  private double altitude;
  private float accuracy;

  public LocationStruct(double lat, double lng, double altitude,
      float accuracy) {
    this.lat = lat;
    this.lng = lng;
    this.altitude = altitude;
    this.accuracy = accuracy;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public double getAltitude() {
    return altitude;
  }

  public float getAccuracy() {
    return accuracy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LocationStruct that = (LocationStruct) o;

    if (Double.compare(that.lat, lat) != 0) return false;
    if (Double.compare(that.lng, lng) != 0) return false;
    if (Double.compare(that.altitude, altitude) != 0) return false;
    return Float.compare(that.accuracy, accuracy) == 0;

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(lat);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(lng);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(altitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (accuracy != +0.0f ? Float.floatToIntBits(accuracy) : 0);
    return result;
  }
}
