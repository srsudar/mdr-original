package org.mamasdelrio.android.widget;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.R;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SelectOneViewTest}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SelectOneViewTest {
  private static final int LABEL_RES_ID = R.array.abcd;
  private static final int VALUE_RES_ID = R.array.a1234;
  private SelectOneView view;

  @Before
  public void before() {
    view = new SelectOneView(RuntimeEnvironment.application, LABEL_RES_ID,
        VALUE_RES_ID);
  }

  @Test
  public void hasCorrectSize() {
    String[] labels = view.getResources().getStringArray(LABEL_RES_ID);
    assertThat(view.spinner.getAdapter()).hasCount(labels.length);
  }

  @Test
  public void getValueForSelectedCorrect() {
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
