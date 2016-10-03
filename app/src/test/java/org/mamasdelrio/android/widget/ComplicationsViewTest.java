package org.mamasdelrio.android.widget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.R;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Tests for {@link ComplicationsView}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ComplicationsViewTest {
  ComplicationsView view;
  String[] babyOptions;
  String[] motherOptions;

  @Before
  public void before() {
    view = new ComplicationsView(RuntimeEnvironment.application);
    babyOptions = RuntimeEnvironment.application.getResources().getStringArray(
        R.array.complications_baby_values);
    motherOptions = RuntimeEnvironment.application.getResources()
        .getStringArray(R.array.complications_mother_values);
  }

  @Test
  public void babyStateHasCorrectNumberOfOptions() {
    assertThat(view.getBabyState().getSpinner()).hasCount(babyOptions.length);
  }

  @Test
  public void motherStateHasCorrectNumberOfOptions() {
    assertThat(view.getMotherState().getSpinner()).hasCount(
        motherOptions.length);
  }

  @Test
  public void addValuesToMapCorrect() {
    Map<String, Object> map = new HashMap<>();
    String babyStateKey = "baby";
    String motherStateKey = "mother";
    int targetBabyIndex = 1;
    int targetMotherIndex = 0;
    view.babyState.getSpinner().setSelection(targetBabyIndex);
    view.motherState.getSpinner().setSelection(targetMotherIndex);
    view.addValuesToMap(map, babyStateKey, motherStateKey);

    assertThat(map).contains(
        entry(babyStateKey, targetBabyIndex),
        entry(motherStateKey, targetMotherIndex));
  }
}
