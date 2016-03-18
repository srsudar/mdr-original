package org.mamasdelrio.android.widget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.DoAlarmActivity;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DeathView}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DeathViewTest {
  DeathView view;

  @Before
  public void before() {
    view = new DeathView(RuntimeEnvironment.application);
  }

  @Test
  public void uiElementsInitialized() {
    assertThat(view.deathDate)
        .isEnabled()
        .isVisible();
    assertThat(view.probableCause)
        .isEnabled()
        .isVisible();
    assertThat(view.dni)
        .isEnabled()
        .isVisible();
  }
}
