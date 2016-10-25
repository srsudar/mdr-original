package org.mamasdelrio.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mamasdelrio.android.logic.BundleHelper;
import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.JsonHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.logic.WhatsappSender;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;
import org.mamasdelrio.android.widget.DniOrNameView;
import org.mamasdelrio.android.widget.LocationView;
import org.mamasdelrio.android.widget.SelectCommunityView;
import org.mamasdelrio.android.widget.SelectOneView;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class DoRiskActivity extends AppCompatActivity implements IFormActivity {
  @Bind(R.id.risk_dniorname) DniOrNameView dniOrName;
  @Bind(R.id.risk_community) SelectCommunityView community;
  @Bind(R.id.risk_risk) SelectOneView risk;
  @Bind(R.id.risk_send) Button send;
  @Bind(R.id.risk_location) LocationView location;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_risk);
    ButterKnife.bind(this);

    risk.initializeView(R.array.risks_labels, R.array.risks_values);

    send.setEnabled(isReadyToBeSent());
  }

  @Override
  protected void onResume() {
    super.onResume();
    location.setLaunchingActivity(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
    if (requestCode == Constants.RequestCodes.GET_LOCATION) {
      location.handleResultIntent(data.getExtras(), new BundleHelper());
    } else if (requestCode == Constants.RequestCodes.SEND_TO_WHATSAPP) {
      finish();
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public boolean isReadyToBeSent() {
    return dniOrName.isComplete();
  }

  @Override
  public String getUserFriendlyMessage() {
    return getString(R.string.msg_risk,
        risk.getLabelForSelected(), community.getUserFriendlyCommunityName());
  }

  @Override
  public void addValuesToMap(Map<String, Object> map, TimeStamper timeStamper) {
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(map, JsonValues.Forms.RISKS);
    jsonHelper.callAddValuesOnLocationView(map, location);

    dniOrName.addValuesToMap(map, JsonKeys.Risks.HAS_DNI, JsonKeys.Risks.DNI,
        JsonKeys.Risks.NAMES);
    community.addValuesToMap(map, JsonKeys.Risks.COMMUNITY);
    risk.addValuesToMap(map, JsonKeys.Risks.RISK);
  }


  @OnClick({ R.id.shared_nameordni_yesno_group, R.id.shared_nameordni_yesno_yes,
      R.id.shared_nameordni_yesno_no })
  public void onDniOrNameClicked(View view) {
    send.setEnabled(isReadyToBeSent());
  }

  @SuppressWarnings("unused")
  @OnTextChanged({ R.id.shared_nameordni_enter_dni,
      R.id.shared_nameordni_enter_names })
  public void onTextChanged(CharSequence text) {
    send.setEnabled(isReadyToBeSent());
  }


  @SuppressWarnings("unused")
  @OnClick(R.id.risk_send)
  public void onSendClick(View view) {
    WhatsappSender sender = new WhatsappSender();
    sender.sendMessage(this, this, WhatsappSender.MessageRecipient.DOCTORS);
  }
}
