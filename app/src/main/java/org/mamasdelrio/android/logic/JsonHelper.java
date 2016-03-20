package org.mamasdelrio.android.logic;

import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.widget.LocationView;

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
  public void addCommonEntries(Map<String, Object> map, String formId) {
    map.put(JsonKeys.SharedKeys.VERSION, Constants.VERSION);
    map.put(JsonKeys.SharedKeys.DATETIME, timeStamper.getFriendlyDateTime());
    map.put(JsonKeys.SharedKeys.FORM, formId);
  }

  public void callAddValuesOnLocationView(Map<String, Object> map,
      LocationView locationView) {
    locationView.addValuesToMap(map, JsonKeys.SharedKeys.Location.LAT,
        JsonKeys.SharedKeys.Location.LNG, JsonKeys.SharedKeys.Location.ALT,
        JsonKeys.SharedKeys.Location.ACC);
  }
}
