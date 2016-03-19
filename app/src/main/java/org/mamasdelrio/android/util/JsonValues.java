package org.mamasdelrio.android.util;

/**
 * Constant JSON values shared across the app.
 */
public class JsonValues {
  public static class Forms {
    public static final String BIRTHS = "b";
    public static final String ALARMS = "a";
    public static final String PREGNANCIES = "p";
    public static final String RISKS = "r";
    public static final String OUTCOMES = "o";
  }

  public static class Outcomes {
    public static final String COMPLICATION = "o_c";
    public static final String ABORTION = "o_a";
    public static final String BABY_DEATH = "o_bd";
    public static final String MOTHER_DEATH = "o_md";
  }

  public static class HasDni {
    public static final String YES = "s";
    public static final String NO = "n";
    public static final String UNSET = "u";
  }
}
