package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.mamasdelrio.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudars on 3/17/16.
 */
public class DeathView extends LinearLayout {
  @Bind(R.id.outcome_death_date) DatePicker deathDate;
  @Bind(R.id.outcome_death_probablecause) EditText probableCause;
  @Bind(R.id.outcome_death_dni) EditText dni;

  public DeathView(Context context) {
    super(context);
    init();
  }

  public DeathView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DeathView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_death, this,
        true);
    ButterKnife.bind(this);
  }
}
