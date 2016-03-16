package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import org.mamasdelrio.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A {@link android.view.View} allowing input of a DNI or a name.
 */
public class DniOrNameView extends LinearLayout {
  @Bind(R.id.shared_nameordni_yesno_yes) RadioButton dniYes;
  @Bind(R.id.shared_nameordni_yesno_no) RadioButton dniNo;
  @Bind(R.id.shared_nameordni_enter_container) FrameLayout editContainer;
  @Bind(R.id.shared_nameordni_dni_container) LinearLayout dniContainer;
  @Bind(R.id.shared_nameordni_name_container) LinearLayout nameContainer;
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
  }
}
