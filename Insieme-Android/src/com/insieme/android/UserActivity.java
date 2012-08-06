package com.insieme.android;

import java.util.concurrent.ExecutionException;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.artist.task.impl.GetArtistTask;
import com.insieme.android.constants.InsiemeAndroidConstants;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.domain.rest.RestResult;

@ContentView(R.layout.user_activity)
public class UserActivity extends RoboActivity {
	
	@InjectView(R.id.trackListLayoutId)
	private LinearLayout trackListLayout;
	
	@InjectView(R.id.trackListScrollViewId)
	private ScrollView trackListScrollView;
	
	@InjectView(R.id.myArtistPageLayoutId)
	private RelativeLayout myArtistPageLayout;
	
	@InjectView(R.id.trackDescriptionValueTextId)
	private TextView trackDescriptionText;
	
	@InjectView(R.id.trackGenreValueTextId)
	private TextView trackGenreText;
	
	@InjectView(R.id.trackNameValueTextId)
	private TextView trackNameText;
	
	@InjectView(R.id.trackDownloadCountValueTextId)
	private TextView trackDownloadCountText;
	
	private volatile UserEntity currentUser;
	
	@Inject
	Provider<GetArtistTask> getArtistTaskProvider;

	private volatile TrackEntity currentTrack;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (UserEntity) getIntent().getSerializableExtra(InsiemeAndroidConstants.USER_ID);
        
        if (!isArtist(currentUser.getUserId())) {
        	myArtistPageLayout.setVisibility(View.INVISIBLE);
        }
        
        UserTrackButton newTrack;
        TrackEntity newTrackEntity;
        
        for(int i = 0; i<100; i++) {
        	newTrackEntity = new TrackEntity(String.valueOf(i), 
        			"sampleArtist", 
        			"sample Genre", 
        			0,
        			"sample track: " + i, 
        			"Track: " + i);
        	newTrack = new UserTrackButton(this, newTrackEntity); 
        	newTrack.setText(newTrackEntity.getName());
        	trackListLayout.addView(newTrack);
        }
    }
	
	/**
	 * Go to the artist info page where a user can search for artists.
	 *
	 * @param v the v
	 */
	public void goToArtistInfoAction(View v) {
		 Intent intent = new Intent(v.getContext(), ArtistInfoActivity.class);
		 intent.putExtra(InsiemeAndroidConstants.USER_ID, this.currentUser);
		 startActivityForResult(intent, 0);
	}
	
	public void goToArtistPageAction(View v) {
		Intent intent = new Intent(v.getContext(), ArtistActivity.class);
		intent.putExtra(InsiemeAndroidConstants.ARTIST_ID, getArtist(currentUser.getUserId()));
		intent.putExtra(InsiemeAndroidConstants.USER_ID, this.currentUser);
		startActivityForResult(intent, 0);
	}
	
	/**
	 * Checks to see whether a user is an artist given an artist id. 
	 * 
	 * TODO: this method is duplicated across two activities. 
	 * Find a way to refactor to a common place.
	 *
	 * @param userId the user id
	 * @return the boolean
	 */
	private Boolean isArtist(String userId) {
		Boolean isArtistResult = Boolean.FALSE;
		if (userId == null) {
			return isArtistResult;
		} else {
			try {
				RestResult<ArtistEntity> result = getArtistTaskProvider.get().execute(userId).get();
				isArtistResult = result.isSuccessfull();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return isArtistResult;
	}
	
	/**
	 * Gets the artist.
	 * 
	 * TODO: Sort of hacky code that assumes that an artist will be found for the user id.
	 * Makes sense because the user should not have the ability to go to his artistPage without being an artist.
	 * The entire layout is hidden if the user is not an artist.
	 *
	 * @param userId the user id
	 * @return the artist
	 */
	private ArtistEntity getArtist(String userId) {
		ArtistEntity artistResult = null;
		try {
			artistResult = getArtistTaskProvider.get()
					.execute(userId)
					.get()
					.getRestResult();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return artistResult;
		
	}
	
	/**
	 * Sets the track info on a focused track.
	 *
	 * @param track the new track info
	 */
	private void setTrackInfo(TrackEntity track) {
		this.currentTrack = track;
		trackDescriptionText.setText(track.getDescription());
		trackGenreText.setText(track.getGenre());
		trackNameText.setText(track.getName());
		trackDownloadCountText.setText(String.valueOf(track.getDownloadCount()));
	}
	
	/**
	 * The user track button to track buttons in the user scroll list view.
	 */
	private class UserTrackButton extends Button {
		private final TrackEntity track;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				setTrackInfo(track);
			}
		};
		
		public UserTrackButton(Context context, TrackEntity track) {
			super(context);
			this.track = track;
			setOnClickListener(clicker);
		}
	}
	
}
