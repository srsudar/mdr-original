package org.mamasdelrio.android.logic;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.util.Constants;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.assertThat;

/**
 * Tests for {@link WhatsappSender}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class WhatsappSenderTest {
  WhatsappSender sender;

  @Before
  public void before() {
    sender = new WhatsappSender();
  }

  @Test
  public void createsIntentForText() {
    String testMsg = "what a lovely message!";
    Intent actual = sender.getShareIntent(testMsg);
    assertThat(actual)
        .hasAction(Intent.ACTION_SEND)
        .hasExtra(Intent.EXTRA_TEXT, testMsg)
        .hasType(Constants.TEXT_MESSAGE_MIME_TYPE);
    org.assertj.core.api.Assertions.assertThat(actual.getPackage())
        .isEqualTo(Constants.WHATSAPP_PACKAGE);
  }
}
