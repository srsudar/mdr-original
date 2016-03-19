package org.mamasdelrio.android.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.R;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * A {@link android.view.View} allowing input of a DNI or a name.
 */
public class DniOrNameView extends LinearLayout {
  private static final String TAG = DniOrNameView.class.getSimpleName();
  /** According to android docs, the id of no selection. */
  private static final int UNCHECKED_RADIO_ID = -1;

  @Bind(R.id.shared_nameordni_yesno_group) RadioGroup dniGroup;
  @Bind(R.id.shared_nameordni_yesno_yes) RadioButton dniYes;
  @Bind(R.id.shared_nameordni_yesno_no) RadioButton dniNo;
  @Bind(R.id.shared_nameordni_dnilabel) TextView dniLabel;
  @Bind(R.id.shared_nameordni_namelabel) TextView nameLabel;
  @Bind(R.id.shared_nameordni_enter_dni) EditText dni;
  @Bind(R.id.shared_nameordni_enter_names) EditText name;

  public DniOrNameView(Context context) {
    super(context);
    init();
  }

  public DniOrNameView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DniOrNameView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  protected void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_nameordni, this,
        true);
    ButterKnife.bind(this);

    // The edit fields should start disabled.
    setDniEnabled(false);
    setNameEnabled(false);

    initRadioGroupListener();
  }

  private void initRadioGroupListener() {
    dniGroup.setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
              case R.id.shared_nameordni_yesno_yes:
                setDniEnabled(true);
                setNameEnabled(false);
                break;
              case R.id.shared_nameordni_yesno_no:
                setDniEnabled(false);
                setNameEnabled(true);
                break;
              case UNCHECKED_RADIO_ID:
                setDniEnabled(false);
                setNameEnabled(false);
                break;
              default:
                if (BuildConfig.DEBUG) {
                  Log.e(TAG, "unrecognized checkedId: " + checkedId);
                }
            }
          }
        });

  }

  /**
   * Returns true if sufficient user input has been created to complete the
   * form.
   */
  public boolean isComplete() {
    if (isDni()) {
      return dni.getText().length() > 0;
    }
    if (isName()) {
      return name.getText().length() > 0;
    }
    return false;
  }

  /**
   * Add the values picked in this view to map.
   */
  public void addValuesToMap(Map<String, Object> map, String hasDniKey,
      String dniKey, String nameKey) {
    String hasDniValue;
    int selectedId = dniGroup.getCheckedRadioButtonId();
    if (selectedId == R.id.shared_nameordni_yesno_yes) {
      hasDniValue = JsonValues.HasDni.YES;
    } else if (selectedId == R.id.shared_nameordni_yesno_no) {
      hasDniValue = JsonValues.HasDni.NO;
    } else {
      hasDniValue = JsonValues.HasDni.UNSET;
    }
    map.put(hasDniKey, hasDniValue);
    map.put(dniKey, dni.getText().toString());
    map.put(nameKey, name.getText().toString());
  }

  /**
   * Returns true if DNI mode has been selected.
   * @return
   */
  public boolean isDni() {
    return dniYes.isChecked();
  }

  /**
   * Returns true if DNI has been selected to not be present.
   * @return
   */
  public boolean isName() {
    return dniNo.isChecked();
  }

  /**
   * Returns the entered name if {@link #isName()} is true. Otherwise returns
   * an empty string.
   */
  public String getName() {
    return name.getText().toString();
  }

  /**
   * Returns the entered DNI if {@link #isDni()} is true. Otherwise returns an
   * empty string.
   */
  public String getDni() {
    return dni.getText().toString();
  }

  /**
   * Enable or disable the DNI relevant views.
   */
  private void setDniEnabled(boolean enabled) {
    dni.setEnabled(enabled);
    dniLabel.setEnabled(enabled);
  }

  /**
   * Enable or disable the name relevant views.
   * @param enabled
   */
  private void setNameEnabled(boolean enabled) {
    name.setEnabled(enabled);
    nameLabel.setEnabled(enabled);
  }
}
