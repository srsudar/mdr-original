package org.mamasdelrio.android.util;

/**
 * JSON keys for the app.
 */
public class JsonKeys {
  /** Keys shared across forms. */
  public static class SharedKeys {
    public static final String VERSION = "s_v";
    public static final String FORM = "s_f";
    public static final String DATETIME = "s_d";
  }

  /** Keys for the Births forms. */
  public static class Births {
    public static final String DNI = "d";
    public static final String BIRTH_YEAR = "by";
    public static final String BIRTH_MONTH = "bm";
    public static final String BIRTH_DAY = "bd";
  }

  /** Keys for the Pregnancies forms. */
  public static class Pregnancies {
    public static final String HAS_DNI = "h";
    public static final String DNI = "d";
    public static final String NAMES = "n";
    public static final String BIRTH_YEAR = "by";
    public static final String BIRTH_MONTH = "bm";
    public static final String BIRTH_DAY = "bd";
    public static final String PERIOD_KNOWN = "p";
    public static final String PERIOD_DATE = "pd";
    public static final String TAKE_CONTROLS = "c";
    public static final String CONTROL_MONTH = "cm";
  }

}
