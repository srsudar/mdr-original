package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mamasdelrio.android.R;
import org.mamasdelrio.android.logic.DatePickerHelper;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudars on 3/17/16.
 */
public class DeathView extends LinearLayout {
  @Bind(R.id.outcome_death_date) DatePicker deathDate;
  @Bind(R.id.outcome_death_probablecause) EditText probableCause;
  @Bind(R.id.outcome_dni_label) TextView dniLabel;
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

  public TextView getDniLabel() {
    return dniLabel;
  }

  /**
   * Add the values being picked by this view to the map.
   */
  public void addValuesToMap(Map<String, Object> map,
      DatePickerHelper datePickerHelper,
      String probableCauseKey,
      String dniKey,
      String dateKey) {
    map.put(probableCauseKey, probableCause.getText().toString());
    map.put(dniKey, dni.getText().toString());
    map.put(dateKey, datePickerHelper.getFriendlyString(deathDate));
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_death, this,
        true);
    ButterKnife.bind(this);
  }
}
