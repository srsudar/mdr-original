package org.mamasdelrio.android.logic;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A basic {@link ArrayAdapter} to facilitate {@link Spinner}s from 1 to a max
 * value.
 */
public class IntegerArrayAdapter extends ArrayAdapter<Integer> {
  /**
   * Create an {@link ArrayAdapter} that houses integers from 1 to maxValue.
   * @param context
   * @param maxValue must be >= 1
   */
  public IntegerArrayAdapter(Context context, int maxValue) {
    super(context, android.R.layout.simple_spinner_item);
    this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    if (maxValue < 1) {
      throw new IllegalArgumentException("maxValue must be >= 1");
    }
    for (int i = 0; i < maxValue; i++) {
      this.add(i + 1);
    }
  }
}
