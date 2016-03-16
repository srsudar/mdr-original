package org.mamasdelrio.android.logic;

import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;

import java.util.Map;

/**
 * Assists with JSON manipulation.
 */
public class JsonHelper {
  private TimeStamper timeStamper;

  public JsonHelper(TimeStamper timeStamper) {
    this.timeStamper = timeStamper;
  }

  /**
   * Adds common values shared across JSON maps, including version and the
   * current timestamp.
   */
  public void addCommonEntries(Map<String, Object> map) {
    map.put(JsonKeys.SharedKeys.VERSION, Constants.VERSION);
    map.put(JsonKeys.SharedKeys.DATETIME, timeStamper.getFriendlyDateTime());
  }
}
