package com.insieme.android;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.constants.InsiemeAndroidConstants;
import com.insieme.android.track.task.impl.GetTracksForArtist;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.domain.dto.TrackListEntity;
import com.insieme.common.domain.rest.RestResult;

@ContentView(R.layout.artist_activity)
public class ArtistActivity extends RoboActivity {
	
	private ArtistEntity artistEntity;
	
	@InjectView(R.id.trackListLayoutId)
	private LinearLayout trackListLayout;
	
	@InjectView(R.id.trackListScrollViewId)
	private ScrollView trackListScrollView;
	
	@Inject
	private Provider<GetTracksForArtist> getTracksForArtistTaskProvider;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.artistEntity = (ArtistEntity) getIntent().getSerializableExtra(InsiemeAndroidConstants.ARTIST_ID);
        try {
			RestResult<TrackListEntity> restResult = getTracksForArtistTaskProvider.get()
					.execute(artistEntity)
					.get();
			if (restResult.isFailure()) {
				InsiemeExceptionEntity insiemeExceptionEntity = restResult.getError();
				Toast.makeText(this, insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
			} else {
				populateArtistTracks(restResult.getRestResult().getTrackList());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    }
	
	
	private void populateArtistTracks(Collection<TrackEntity> tracks) {
		TrackButton trackButton;
		trackListLayout.removeAllViews();
		if (tracks.isEmpty()) {
			return;
		} else {
			for (TrackEntity track : tracks) {
				trackButton = new TrackButton(this, track);
				trackButton.setText(track.getName());
				trackListLayout.addView(trackButton);
			}
		}
	}
	
	private void setTrackInfo(TrackEntity track) {
		
	}
	
	private class TrackButton extends Button {
		private final TrackEntity track;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				setTrackInfo(track);
			}
		};
		
		public TrackButton(Context context, TrackEntity track) {
			super(context);
			this.track = track;
			setOnClickListener(clicker);
		}
	}
	
}
