package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.mamasdelrio.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A {@link android.view.View} allowing input of a DNI or a name.
 */
public class DniOrNameView extends LinearLayout {
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
  }

  /**
   * Returns true if sufficient user input has been created to complete the
   * form.
   */
  public boolean isComplete() {
    return false;
  }

  /**
   * Returns true if DNI mode has been selected.
   * @return
   */
  public boolean isDni() {
    return false;
  }

  /**
   * Returns true if DNI has been selected to not be present.
   * @return
   */
  public boolean isName() {
    return false;
  }

  /**
   * Returns the entered name if {@link #isName()} is true. Otherwise returns
   * an empty string.
   */
  public String getName() {
    return null;
  }

  /**
   * Returns the entered DNI if {@link #isDni()} is true. Otherwise returns an
   * empty string.
   */
  public String getDni() {
    return null;
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
