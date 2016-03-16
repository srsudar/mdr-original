package org.mamasdelrio.android.testutil;

import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

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
      String targetDateTime) {
    assertThat(map).contains(
        entry(JsonKeys.SharedKeys.VERSION, Constants.VERSION),
        entry(JsonKeys.SharedKeys.DATETIME, targetDateTime));
  }
}
