package org.mamasdelrio.android.widget;

/**
 * Created by sudars on 3/17/16.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
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
}
