package org.mamasdelrio.android.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Generates and manipulates date times.
 */
public class TimeStamper {
  /**
   * The format we are expecting to be both user friendly for Mamás del Río
   * staffers as well as machine readable.
   * <p>
   * More discussion here:
   * http://developer.android.com/reference/java/text/SimpleDateFormat.html
   */
  private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mmZ";

  /**
   * Return a datetime String of the current moment that is both user friendly
   * and machine readable.
   */
  public String getFriendlyDateTime() {
    SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
    Calendar gregorianCalendar = GregorianCalendar.getInstance();
    String result = format.format(gregorianCalendar.getTime());
    return result;
  }
}
