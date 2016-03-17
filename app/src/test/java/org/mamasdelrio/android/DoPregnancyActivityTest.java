package org.mamasdelrio.android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DoPregnancyActivity}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DoPregnancyActivityTest {
  private DoPregnancyActivity activity;

  @Before
  public void before() {
    activity = Robolectric.setupActivity(DoPregnancyActivity.class);
  }

  @Test
  public void notNull() {
    assertThat(activity).isNotNull();
  }

  @Test
  public void isFalse() {
    assertThat(activity.dniOrName.isComplete()).isFalse();

  }
}
