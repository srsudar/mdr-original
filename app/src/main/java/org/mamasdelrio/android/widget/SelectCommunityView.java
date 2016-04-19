package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.mamasdelrio.android.R;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sudars on 4/18/16.
 */
public class SelectCommunityView extends LinearLayout {
  @Bind(R.id.widget_select_community) SelectOneView community;

  public SelectCommunityView(Context context) {
    super(context);
    init();
  }

  public SelectCommunityView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SelectCommunityView(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.widget_select_community,
        this, true);
    ButterKnife.bind(this);

    community.initializeView(R.array.community_labels,
        R.array.community_values);
  }

  public void addValuesToMap(Map<String, Object> map, String communityKey) {
    map.put(communityKey, community.getValueForSelected());
  }

  /**
   * Return the user-friendly name of the selected community.
   */
  public String getUserFriendlyCommunityName() {
    return community.getLabelForSelected();
  }
}
