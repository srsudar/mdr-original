package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.mamasdelrio.android.R;
import org.mamasdelrio.android.logic.DatePickerHelper;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudars on 3/17/16.
 */
public class AbortionView extends LinearLayout {
  @Bind(R.id.outcome_abortion_motherdni) EditText motherDni;
  @Bind(R.id.outcome_abortion_date) DatePicker date;

  public AbortionView(Context context) {
    super(context);
    init();
  }

  public AbortionView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AbortionView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_abortion, this,
        true);
    ButterKnife.bind(this);
  }

  /** Add mother dni and date to map with the provided keys. */
  public void addValuesToMap(Map<String, Object> map,
      DatePickerHelper datePickerHelper,
      String motherDniKey,
      String dateKey) {
    map.put(motherDniKey, motherDni.getText().toString());
    map.put(dateKey, datePickerHelper.getFriendlyString(date));
  }

  public EditText getMotherDni() {
    return motherDni;
  }

  public DatePicker getDate() {
    return date;
  }
}
