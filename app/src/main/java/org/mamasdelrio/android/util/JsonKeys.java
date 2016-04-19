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

    public static class Location {
      public static final String LAT = "l_lat";
      public static final String LNG = "l_lng";
      public static final String ALT = "l_alt";
      public static final String ACC = "l_acc";
    }
  }

  /** Keys for the Births forms. */
  public static class Births {
    public static final String HAS_DNI = "t";
    public static final String NAME = "n";
    public static final String DNI = "d";
    public static final String BIRTH_DATE = "by";
    public static final String COMMUNITY = "com";
  }

  /** Keys for the Pregnancies forms. */
  public static class Pregnancies {
    public static final String HAS_DNI = "t";
    public static final String DNI = "d";
    public static final String NAMES = "n";
    public static final String COMMUNITY = "com";
    public static final String BIRTH_DATE = "b";
    public static final String PERIOD_KNOWN = "p";
    public static final String PERIOD_DATE = "pd";
    public static final String TAKE_CONTROLS = "c";
    public static final String CONTROL_MONTH = "cm";
  }

  /** Keys for the Alarms form. */
  public static class Alarms {
    public static final String HAS_DNI = "t";
    public static final String DNI = "d";
    public static final String NAME = "n";
    public static final String COMMUNITY = "com";
    public static final String ALARM = "a";
  }

  /** Keys for the Outcomes form. */
  public static class Outcomes {
    public static final String OUTCOME_TYPE = "t";
    public static final String HAS_DNI = "hd";
    public static final String DNI = "d";
    public static final String NAME = "n";
    public static final String COMPLICATION_BABY_STATE = "c_b";
    public static final String COMPLICATION_MOTHER_STATE = "c_m";
    public static final String ABORTION_DNI = "a_d";
    public static final String ABORTION_DATE = "a_f";
    public static final String BDEATH_CAUSE = "bd_c";
    public static final String BDEATH_DNI = "bd_d";
    public static final String BDEATH_DATE = "bd_f";
    public static final String MDEATH_CAUSE = "md_c";
    public static final String MDEATH_DNI = "md_d";
    public static final String MDEATH_DATE = "md_f";
  }

  public static class Risks {
    public static final String HAS_DNI = "t";
    public static final String DNI = "d";
    public static final String NAMES = "n";
    public static final String RISK = "r";
  }
}
