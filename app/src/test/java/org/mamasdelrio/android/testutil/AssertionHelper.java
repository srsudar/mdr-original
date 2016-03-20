package org.mamasdelrio.android.testutil;

import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.widget.LocationView;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Contains common assertions shared across tests.
 */
public class AssertionHelper {
  /**
   * Asserts that version and date time are present in the map. Version is that
   * in {@link org.mamasdelrio.android.util.Constants}.
   * @param targetDateTime the date time you expect
   */
  public static void assertCommonKeysPresent(Map<String, Object> map,
      String targetDateTime, String formId) {
    assertThat(map).contains(
        entry(JsonKeys.SharedKeys.VERSION, Constants.VERSION),
        entry(JsonKeys.SharedKeys.DATETIME, targetDateTime),
        entry(JsonKeys.SharedKeys.FORM, formId));
  }

  /**
   * Asserts that
   * {@link LocationView#addValuesToMap(Map, String, String, String, String)}
   * has been called on the mock. locationViewMock must be a mock, via
   * {@link org.mockito.Mockito#mock(Class)}!
   */
  public static void assertAddValuesCalledOnLocationView(
      LocationView locationViewMock, Map<String, Object> map) {
    verify(locationViewMock, times(1)).addValuesToMap(map,
        JsonKeys.SharedKeys.Location.LAT,
        JsonKeys.SharedKeys.Location.LNG,
        JsonKeys.SharedKeys.Location.ALT,
        JsonKeys.SharedKeys.Location.ACC);
  }
}
