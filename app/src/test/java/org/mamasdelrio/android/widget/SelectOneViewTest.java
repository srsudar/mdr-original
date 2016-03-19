package org.mamasdelrio.android.widget;

import android.view.LayoutInflater;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.R;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Tests for {@link SelectOneViewTest}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SelectOneViewTest {
  private static final int LABEL_RES_ID = R.array.abcd;
  private static final int VALUE_RES_ID = R.array.a1234;
  private SelectOneView view;
  private SelectOneView inflatedView;

  @Before
  public void before() {
    view = new SelectOneView(RuntimeEnvironment.application, LABEL_RES_ID,
        VALUE_RES_ID);
    View root = LayoutInflater.from(RuntimeEnvironment.application).inflate(
        R.layout.example_select_one_layout, null);
    inflatedView = (SelectOneView) root.findViewById(
        R.id.example_select_one_view);
    inflatedView.initializeView(LABEL_RES_ID, VALUE_RES_ID);
  }

  @Test
  public void hasCorrectSizeConstructor() {
    helperHasCorrectSize(view);
  }

  @Test
  public void getValueForSelectedCorrectConstructor() {
    helperGetValueForSelectedCorrect(view);
  }

  @Test
  public void hasCorrectSizeInflated() {
    helperHasCorrectSize(inflatedView);
  }

  @Test
  public void getValueForSelectedCorrectInflated() {
    helperGetValueForSelectedCorrect(inflatedView);
  }

  @Test
  public void addValuesToMapCorrect() {
    String[] values = RuntimeEnvironment.application.getResources()
        .getStringArray(VALUE_RES_ID);
    int index = 2;
    view.getSpinner().setSelection(index);
    String targetValue = values[index];
    String valueKey = "valKey";
    Map<String, Object> map = new HashMap<>();
    view.addValuesToMap(map, valueKey);
    assertThat(map).contains(entry(valueKey, targetValue));
  }

  private void helperHasCorrectSize(SelectOneView view) {
    String[] labels = view.getResources().getStringArray(LABEL_RES_ID);
    assertThat(view.spinner.getAdapter()).hasCount(labels.length);
  }

  private void helperGetValueForSelectedCorrect(SelectOneView view) {
    // The value array is 1, 2, 3, 4, as strings.
    assertThat(view.getValueForSelected()).isEqualTo("1");

    view.spinner.setSelection(1);
    assertThat(view.getValueForSelected()).isEqualTo("2");

    view.spinner.setSelection(2);
    assertThat(view.getValueForSelected()).isEqualTo("3");

    view.spinner.setSelection(3);
    assertThat(view.getValueForSelected()).isEqualTo("4");

    view.spinner.setSelection(0);
    assertThat(view.getValueForSelected()).isEqualTo("1");
  }
}
