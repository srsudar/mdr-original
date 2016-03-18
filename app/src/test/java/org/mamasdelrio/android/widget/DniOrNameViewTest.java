package org.mamasdelrio.android.widget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.robolectric.RobolectricGradleTestRunner;
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
 * Tests for {@link DniOrNameView}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DniOrNameViewTest {
  private DniOrNameView view;

  @Before
  public void before() {
    view = new DniOrNameView(RuntimeEnvironment.application);
  }

  @Test
  public void viewsStartInCorrectEnabledState() {
    assertThat(view.dniYes).isEnabled().isVisible();
    assertThat(view.dniNo).isEnabled().isVisible();
    assertThat(view.dniLabel).isDisabled().isVisible();
    assertThat(view.nameLabel).isDisabled().isVisible();
    assertThat(view.dni).isDisabled().isVisible();
    assertThat(view.name).isDisabled().isVisible();
  }

  @Test
  public void togglesUpdateEnabledState() {
    // Both should start disabled
    assertDniEnabled(false);
    assertNameEnabled(false);

    view.dniYes.toggle();
    assertDniEnabled(true);
    assertNameEnabled(false);

    view.dniNo.toggle();
    assertDniEnabled(false);
    assertNameEnabled(true);

    view.dniGroup.clearCheck();
    assertDniEnabled(false);
    assertNameEnabled(false);
  }

  @Test
  public void incompleteCorrectly() {
    // Should start incomplete.
    assertIsIncomplete();
    view.dniYes.toggle();

    // DNI is blank so incomplete.
    assertIsIncomplete();
    view.dni.setText("some DNI");
    assertIsComplete();
    // Clearing DNI makes incomplete.
    view.dni.setText("");
    assertIsIncomplete();

    view.dniNo.toggle();
    assertIsIncomplete();
    view.name.setText("my name");
    assertIsComplete();
    view.name.setText("");
    assertIsIncomplete();

    view.dniGroup.clearCheck();
    assertIsIncomplete();
  }

  @Test
  public void testIsDniCorrect() {
    // starts out nothing is selected
    assertThat(view.isDni()).isFalse();
    view.dniYes.toggle();
    assertThat(view.isDni()).isTrue();
    view.dniGroup.clearCheck();
    assertThat(view.isDni()).isFalse();
  }

  @Test
  public void testIsNameIsCorrect() {
    // starts out nothing is selected
    assertThat(view.isName()).isFalse();
    view.dniNo.toggle();
    assertThat(view.isName()).isTrue();
    view.dniGroup.clearCheck();
    assertThat(view.isDni()).isFalse();
  }

  @Test
  public void testGetDni() {
    String targetDni = "test1234";
    assertThat(view.getDni()).isEqualTo("");
    view.dniYes.toggle();
    assertThat(view.getDni()).isEqualTo("");
    view.dni.setText(targetDni);
    assertThat(view.getDni()).isEqualTo(targetDni);
    view.dni.setText("");
    assertThat(view.getDni()).isEqualTo("");
  }

  @Test
  public void testGetName() {
    String targetName = "foo bar test name";
    assertThat(view.getName()).isEqualTo("");
    view.dniNo.toggle();
    assertThat(view.getName()).isEqualTo("");
    view.name.setText(targetName);
    assertThat(view.getName()).isEqualTo(targetName);
    view.name.setText("");
    assertThat(view.getName()).isEqualTo("");
  }

  @Test
  public void addValuesToMapWorks() {
    String targetDni = "target dni";
    String targetName = "target name";
    String dniKey = "dni";
    String nameKey = "name";
    Map<String, Object> map = new HashMap<>();

    // First make sure it adds empty strings.
    view.addValuesToMap(map, dniKey, nameKey);
    assertThat(map).contains(
        entry(dniKey, ""),
        entry(nameKey, ""));

    map.clear();

    view.dni.setText(targetDni);
    view.name.setText(targetName);
    view.addValuesToMap(map, dniKey, nameKey);
    assertThat(map).contains(
        entry(dniKey, targetDni),
        entry(nameKey, targetName));
  }

  private void assertIsIncomplete() {
    assertThat(view.isComplete()).isFalse();
  }

  private void assertIsComplete() {
    assertThat(view.isComplete()).isTrue();
  }

  private void assertDniEnabled(boolean enabled) {
    assertThat(view.dniLabel).isVisible();
    assertThat(view.dni).isVisible();

    if (enabled) {
      assertThat(view.dniLabel).isEnabled();
      assertThat(view.dni).isEnabled();
    } else {
      assertThat(view.dniLabel).isDisabled();
      assertThat(view.dni).isDisabled();
    }
  }

  private void assertNameEnabled(boolean enabled) {
    assertThat(view.nameLabel).isVisible();
    assertThat(view.name).isVisible();

    if (enabled) {
      assertThat(view.nameLabel).isEnabled();
      assertThat(view.name).isEnabled();
    } else {
      assertThat(view.nameLabel).isDisabled();
      assertThat(view.name).isDisabled();
    }
  }
}
