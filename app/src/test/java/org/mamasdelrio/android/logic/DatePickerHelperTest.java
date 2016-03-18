package org.mamasdelrio.android.logic;

import android.widget.DatePicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.widget.AbortionView;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DatePickerHelper}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatePickerHelperTest {
  DatePickerHelper helper;

  @Before
  public void before() {
    helper = new DatePickerHelper();
  }

  @Test
  public void firstMonthAndDayCorrect() {
    // To make sure 0 indexes work.
    String target = "2016-01-01";
    DatePicker datePicker = new DatePicker(RuntimeEnvironment.application);
    // months are 0 indexed
    datePicker.updateDate(2016, 00, 01);
    String actual = helper.getFriendlyString(datePicker);
    assertThat(actual).isEqualTo(target);
  }

  @Test
  public void lastMonthAndDayCorrect() {
    // To make sure it handles two digits correct.
    String target = "2012-12-31";
    DatePicker datePicker = new DatePicker(RuntimeEnvironment.application);
    datePicker.updateDate(2012, 11, 31);
    String actual = helper.getFriendlyString(datePicker);
    assertThat(actual).isEqualTo(target);
  }
}
