package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.IntegerArrayAdapter;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.SelectOneView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoPregnancyActivity extends AppCompatActivity implements
    IFormActivity {
  private static final int YES_POSITION = 0;

  @Bind(R.id.preg_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.birth_date) DatePicker birthDate;
  @Bind(R.id.preg_last_period) SelectOneView lastPeriodKnown;
  @Bind(R.id.preg_take_control) SelectOneView takeControl;
  @Bind(R.id.preg_control_month) Spinner controlMonth;
  @Bind(R.id.preg_last_period_date_label) TextView lastPeriodDateLabel;
  @Bind(R.id.preg_last_period_date) DatePicker lastPeriodDate;
  @Bind(R.id.preg_send) Button send;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_pregnancy);
    ButterKnife.bind(this);

    // the last period fields should only be enabled if it is known
    lastPeriodDateLabel.setEnabled(false);
    lastPeriodDate.setEnabled(false);
    send.setEnabled(isReadyToBeSent());

    lastPeriodKnown.initializeView(R.array.yes_no_dk_na_labels,
        R.array.yes_no_dk_na_values);
    takeControl.initializeView(R.array.yes_no_dk_na_labels,
        R.array.yes_no_dk_na_values);

    IntegerArrayAdapter adapter = new IntegerArrayAdapter(this,
        Constants.NUM_MONTH_IN_PREGNANCY);
    controlMonth.setAdapter(adapter);
    adapter.setDropDownViewResource(
        android.R.layout.simple_spinner_dropdown_item);

    lastPeriodKnown.getSpinner().setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position,
          long id) {
        if (position == YES_POSITION) {
          lastPeriodDate.setEnabled(true);
        } else {
          lastPeriodDate.setEnabled(false);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // no op
      }
    });
  }

  @OnClick({ R.id.shared_nameordni_yesno_group, R.id.shared_nameordni_yesno_yes,
      R.id.shared_nameordni_yesno_no })
  public void onDniOrNameClicked(View view) {
    send.setEnabled(isReadyToBeSent());
  }

  @SuppressWarnings("unused")
  @OnTextChanged({ R.id.shared_nameordni_enter_dni,
      R.id.shared_nameordni_enter_names })
  public void onTextChanged(CharSequence text) {
    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public boolean isReadyToBeSent() {
    return dniOrName.isComplete();
  }

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
  }
}
