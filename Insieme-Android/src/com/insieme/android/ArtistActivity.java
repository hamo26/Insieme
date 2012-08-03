package com.insieme.android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.artist_activity)
public class ArtistActivity extends RoboActivity {
	
	@InjectView(R.id.trackListLayoutId)
	private LinearLayout trackListLayout;
	
	@InjectView(R.id.trackListScrollViewId)
	private ScrollView trackListScrollView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button newTrack;
        
        for(int i = 0; i<100; i++) {
        	newTrack = new Button(this); 
        	newTrack.setText("track" + i);
        	trackListLayout.addView(newTrack);
        }
    }
	
}
