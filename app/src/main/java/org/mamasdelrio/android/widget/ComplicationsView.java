package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.mamasdelrio.android.R;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudars on 3/17/16.
 */
public class ComplicationsView extends LinearLayout {
  @Bind(R.id.outcome_complication_baby_state) SelectOneView babyState;
  @Bind(R.id.outcome_complication_mother_state) SelectOneView motherState;

  public ComplicationsView(Context context) {
    super(context);
    init();
  }

  public ComplicationsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ComplicationsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_complications,
        this, true);
    ButterKnife.bind(this);
    babyState.initializeView(R.array.complications_baby_labels,
        R.array.complications_baby_values);
    motherState.initializeView(R.array.complications_mother_labels,
        R.array.complications_mother_values);
  }

  public void addValuesToMap(Map<String, Object> map, String babyStateKey,
      String motherStateKey) {
    map.put(babyStateKey, babyState.getSpinner().getSelectedItemPosition());
    map.put(motherStateKey, motherState.getSpinner().getSelectedItemPosition());
  }

  public SelectOneView getBabyState() {
    return babyState;
  }

  public SelectOneView getMotherState() {
    return motherState;
  }
}
