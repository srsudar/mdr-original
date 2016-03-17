package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.mamasdelrio.android.widget.DniOrNameView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoPregnancyActivity extends AppCompatActivity {
  @Bind(R.id.preg_dniorname) DniOrNameView dniOrName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_pregnancy);
    ButterKnife.bind(this);
  }
}
