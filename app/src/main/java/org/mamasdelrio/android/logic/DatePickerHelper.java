package org.mamasdelrio.android.logic;

import android.widget.DatePicker;

import org.mamasdelrio.android.util.Constants;

/**
 * Helps extract a user friendly String from a
 * {@link android.widget.DatePicker}.
 */
public class DatePickerHelper {
  private static final String DELIMITER = "-";
  public String getFriendlyString(DatePicker datePicker) {
    // + 1 b/c months are 0 indexed
    String monthStr = padIfNecessary(datePicker.getMonth() + 1);
    String dayStr = padIfNecessary(datePicker.getDayOfMonth());
    String result = datePicker.getYear()
        + DELIMITER
        + monthStr
        + DELIMITER
        + dayStr;
    return result;
  }

  /**
   * Set the defaults for the birth date.
   * @param datePicker
   */
  public void setToBirthDateDefaults(DatePicker datePicker) {
    // Get the existing data before updating.
    int month = datePicker.getMonth();
    int day = datePicker.getDayOfMonth();
    datePicker.updateDate(Constants.DEFAULT_BIRTH_YEAR, month, day);
  }

  private String padIfNecessary(int val) {
    if (val < 10) {
      return "0" + Integer.toString(val);
    }
    return Integer.toString(val);
  }
}
