package org.mamasdelrio.android.logic;

import android.app.Activity;

/**
 * An interface shared by {@link Activity}s that represent a form.
 */
public interface IFormActivity {
  /** Returns true if the information is completed such that it can be sent. */
  boolean isReadyToBeSent();
}
