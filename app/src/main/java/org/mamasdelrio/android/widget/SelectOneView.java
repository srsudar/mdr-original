package org.mamasdelrio.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.mamasdelrio.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A view that provides a {@link android.widget.Spinner}. It allows a list of
 * labels--to be shown to the user--and a list of values--to be exported as an
 * identifier--to be paired together.
 */
public class SelectOneView extends LinearLayout {
  // The int flag signifying that the resource should be read from XML.
  private static final int FLAG_RES_ID_IN_XML = -1;
  private String[] labels;
  private String[] values;
  @Bind(R.id.widget_selectone_spinner) Spinner spinner;
  private ArrayAdapter<String> adapter;

  public SelectOneView(Context context, int labelsResId, int valuesResid) {
    super(context);
    // This should never be called...I think?
    init(context, null, 0, labelsResId, valuesResid);
  }

  public SelectOneView(Context context, AttributeSet attrs) {
    super(context, attrs);
    // assuming 0 is ok?
    init(context, attrs, 0, FLAG_RES_ID_IN_XML, FLAG_RES_ID_IN_XML);
  }

  public SelectOneView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, FLAG_RES_ID_IN_XML, FLAG_RES_ID_IN_XML);
  }

  public String getValueForSelected() {
    int selectedPosition = spinner.getSelectedItemPosition();
    return values[selectedPosition];
  }

  /**
   * Initialize the view.
   * @param attrs can be null
   * @param defStyleAttr can be 0
   * @param labelsResId the ID of a string array or {@link #FLAG_RES_ID_IN_XML}
   * @param valuesResId the ID of a string array of {@link #FLAG_RES_ID_IN_XML}
   */
  private void init(Context context, AttributeSet attrs, int defStyleAttr,
      int labelsResId, int valuesResId) {
    LayoutInflater.from(context).inflate(R.layout.widget_selectone, this,
        true);

    TypedArray typedArray = context.obtainStyledAttributes(attrs,
        R.styleable.SelectOneView, defStyleAttr, defStyleAttr);
    ButterKnife.bind(this);

    if (labelsResId == FLAG_RES_ID_IN_XML) {
      labels = convertToStringArray(typedArray.getTextArray(
          R.styleable.SelectOneView_labels));
    } else {
      labels = context.getResources().getStringArray(labelsResId);
    }

    if (valuesResId == FLAG_RES_ID_IN_XML) {
      values = convertToStringArray(typedArray.getTextArray(
          R.styleable.SelectOneView_values));
    } else {
      values = context.getResources().getStringArray(valuesResId);
    }

    if (labels.length != values.length) {
      throw new IllegalStateException(
          "values and labels arrays must have same length");
    }

    adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
        labels);
    spinner.setAdapter(adapter);

    typedArray.recycle();
  }

  private String[] convertToStringArray(CharSequence[] chars) {
    String[] result = new String[chars.length];
    for (int i = 0; i < chars.length; i++) {
      result[i] = chars[i].toString();
    }
    return result;
  }
}
