package org.mamasdelrio.android.widget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.R;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;

/**
 * Tests for {@link ComplicationsView}.
 */
@RunWith(RobolectricGradleTestRunner.class)
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
}
