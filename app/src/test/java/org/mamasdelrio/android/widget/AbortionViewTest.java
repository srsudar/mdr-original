package org.mamasdelrio.android.widget;

/**
 * Created by sudars on 3/17/16.
 */

import android.widget.DatePicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.logic.DatePickerHelper;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AbortionView}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AbortionViewTest {
  AbortionView view;

  @Before
  public void before() {
    view = new AbortionView(RuntimeEnvironment.application);
  }

  @Test
  public void uiElementsInitialized() {
    assertThat(view.motherDni)
        .isVisible()
        .isEnabled();
    assertThat(view.date)
        .isVisible()
        .isEnabled();
  }

  @Test
  public void addValuesToMapCorrect() {
    String targetDate = "2014-02-27";
    String targetDni = "some dni";
    view.motherDni.setText(targetDni);
    DatePickerHelper dphMock = mock(DatePickerHelper.class);
    when(dphMock.getFriendlyString(any(DatePicker.class)))
        .thenReturn(targetDate);
    String dniKey = "dni";
    String dateKey = "date";
    Map<String, Object> map = new HashMap<>();

    view.addValuesToMap(map, dphMock, dniKey, dateKey);
    assertThat(map).contains(
        entry(dniKey, targetDni),
        entry(dateKey, targetDate));
  }
}
