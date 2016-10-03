package org.mamasdelrio.android.logic;

import android.app.Activity;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mamasdelrio.android.BuildConfig;
import org.mamasdelrio.android.util.Constants;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link WhatsappSender}.
 */
@RunWith(RobolectricTestRunner.class)
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

  @Test
  public void sendMessageTest() {
    IFormActivity formActivityMock = mock(IFormActivity.class);
    Activity activityMock = mock(Activity.class);
    WhatsappSender.MessageRecipient recipient =
        WhatsappSender.MessageRecipient.GROUP;
    Map<String, Object> emptyMap = new HashMap<>();

    sender.sendMessage(formActivityMock, activityMock, recipient);

    verify(formActivityMock, times(1)).addValuesToMap(eq(emptyMap),
        eq(new TimeStamper()));
    verify(formActivityMock, times(1)).getUserFriendlyMessage();
    verify(activityMock, times(1)).startActivityForResult(any(Intent.class),
        eq(Constants.RequestCodes.SEND_TO_WHATSAPP));
  }
}
