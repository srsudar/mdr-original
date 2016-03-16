package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.util.Constants;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;

/**
 * Tests for {@link DoBirthActivity}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DoBirthActivityTest {
  private DoBirthActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoBirthActivity.class);
  }

  @Test
  public void editableElementsInitialized() {
    assertThat(activity.dni)
        .isEnabled()
        .isVisible();
    assertThat(activity.year)
        .isEnabled()
        .isVisible();
    assertThat(activity.month)
        .isEnabled()
        .isVisible();
    assertThat(activity.day)
        .isEnabled()
        .isVisible();
    //The Button should start disabled.
    assertThat(activity.send)
        .hasText(R.string.send_to_whatsapp_group)
        .isDisabled();

    assertThat(activity.month.getAdapter()).hasCount(Constants.MAX_MONTH);
    assertThat(activity.day.getAdapter()).hasCount(Constants.MAX_DAY);
  }

  @Test
  public void buttonEnablesDisablesAsAppropriate() {
    // The button should be disabled when the DNI and year fields are empty.
    assertSendButtonIsDisabled();
    activity.dni.setText("182849191");
    assertSendButtonIsDisabled();
    activity.year.setText("1234");
    assertSendButtonIsEnabled();
    // Now remove some text and make sure it re-disables.
    activity.year.setText("");
    assertSendButtonIsDisabled();
    activity.year.setText("12");
    assertSendButtonIsEnabled();
    activity.dni.setText("");
    assertSendButtonIsDisabled();
    activity.dni.setText("1234");
    assertSendButtonIsEnabled();
  }

  private void assertSendButtonIsDisabled() {
    assertThat(activity.send).isDisabled();
  }

  private void assertSendButtonIsEnabled() {
    assertThat(activity.send).isEnabled();
  }
}
