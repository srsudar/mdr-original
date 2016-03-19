package org.mamasdelrio.android.logic;

import android.app.Activity;

import java.util.Map;

/**
 * An interface shared by {@link Activity}s that represent a form.
 */
public interface IFormActivity {
  /** Returns true if the information is completed such that it can be sent. */
  boolean isReadyToBeSent();

  /**
   * Returns a {@link java.util.Map} with the values of the form being
   * completed.
   * @param map
   * @param timeStamper the object to generate date times
   */
  void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper);
}
