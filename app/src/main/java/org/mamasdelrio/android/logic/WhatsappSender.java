package org.mamasdelrio.android.logic;

import android.content.Intent;

import org.mamasdelrio.android.util.Constants;

/**
 * This class prepares intents for starting Whatsapp.
 */
public class WhatsappSender {
  /**
   * Get the {@link Intent} to share a String message.
   * @param msg the Intent to share.
   * @return
   */
  public Intent getShareIntent(String msg) {
    // Based on these helpful posts:
    // http://stackoverflow.com/questions/8638475/intent-action-send-whatsapp
    // https://www.whatsapp.com/faq/android/28000012
    // Hmm, and can now send BOTH at the same time?
    // http://stackoverflow.com/questions/23077338/share-image-and-text-through-whatsapp-or-facebook
    final Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
    sendIntent.setPackage(Constants.WHATSAPP_PACKAGE);
    sendIntent.setType(Constants.TEXT_MESSAGE_MIME_TYPE);
    return sendIntent;
  }
}
