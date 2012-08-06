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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.constants.InsiemeAndroidConstants;
import com.insieme.android.track.task.impl.DeleteTrackTask;
import com.insieme.android.track.task.impl.GetTracksForArtist;
import com.insieme.android.track.task.impl.UpdateTrackTask;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.domain.dto.TrackListEntity;
import com.insieme.common.domain.rest.RestResult;

/**
 * Views and manages artist activity.
 */
@ContentView(R.layout.artist_activity)
public class ArtistActivity extends RoboActivity {
	
	private volatile ArtistEntity focusedArtistEntity;
	private volatile TrackEntity focusedTrack;
	
	@InjectView(R.id.trackListLayoutId)
	private LinearLayout trackListLayout;
	
	@InjectView(R.id.trackListScrollViewId)
	private ScrollView trackListScrollView;
	
	@InjectView(R.id.trackSettingsDescriptionInputId)
	private EditText trackSettingDescriptionInput;
	
	@InjectView(R.id.trackSettingsGenreValueInputId)
	private EditText trackSettingsGenreInput;
	
	@InjectView(R.id.trackDownloadCountValueInputId)
	private EditText trackSettingsDownloadCountInput;
	
	@InjectView(R.id.trackSettingsNameInputId)
	private EditText trackSettingsNameInput;
	
	@InjectView(R.id.trackDescriptionValueTextId)
	private TextView trackDescriptionInfoText;
	
	@InjectView(R.id.trackDownloadCountValueTextId)
	private TextView trackDownloadCountInfoText;
	
	@InjectView(R.id.trackGenreValueTextId)
	private TextView trackGenreInfoText;
	
	@InjectView(R.id.trackNameValueTextId)
	private TextView trackNameInfoText;
	
	@Inject
	private Provider<GetTracksForArtist> getTracksForArtistTaskProvider;
	
	@Inject
	private Provider<UpdateTrackTask> updateTrackTaskProvider;
	
	@Inject
	private Provider<DeleteTrackTask> deleteTrackTaskProvider;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.focusedArtistEntity = (ArtistEntity) getIntent().getSerializableExtra(InsiemeAndroidConstants.ARTIST_ID);
        refreshArtistTrackList();
    }

	
	/**
	 * Refreshes the entire artist track list. This is very inefficient and I am sure there is 
	 * a better way to do this. 
	 * 
	 * TODO: Change this method to refresh only the track that is currently in focus, i.e being updated or 
	 * in the track info pane. 
	 */
	private void refreshArtistTrackList() {
		try {
			RestResult<TrackListEntity> restResult = getTracksForArtistTaskProvider.get()
					.execute(focusedArtistEntity)
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
	
	/**
	 * TODO: the update and delete tracks are astoundingly similar. Refactor both methods.
	 *
	 * @param v the v
	 */
	public void updateTrack(View v) {
		String updateGenre = trackSettingsGenreInput.getText().toString();
		int updateDownload = 
				trackSettingsDownloadCountInput.getText().toString() instanceof String ? 
						Integer.valueOf(trackSettingsDownloadCountInput.getText().toString()) : 0;
		String updateDescription = trackSettingDescriptionInput.getText().toString();
		String updateName = trackSettingsNameInput.getText().toString();

		TrackEntity updatedTrack = new TrackEntity(focusedTrack.getTrackId(),
				focusedArtistEntity.getArtistId(), 
				updateGenre, 
				updateDownload,
				updateDescription, 
				updateName);
		try {
			RestResult<TrackEntity> restResult = updateTrackTaskProvider.get()
					.execute(updatedTrack)
					.get();
			if (restResult.isFailure()) {
				InsiemeExceptionEntity insiemeExceptionEntity = restResult.getError();
				Toast.makeText(this, insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
			} else {
				refreshArtistTrackList();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO: Look at comment above.
	 *
	 * @param v the v
	 */
	public void deleteTrack(View v) {
		try {
			RestResult<TrackEntity> restResult = deleteTrackTaskProvider.get()
					.execute(this.focusedTrack)
					.get();
			if (restResult.isFailure()) {
				InsiemeExceptionEntity insiemeExceptionEntity = restResult.getError();
				Toast.makeText(this, insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
			} else {
				refreshArtistTrackList();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	private void setTrackInfo(TrackEntity track) {
		this.focusedTrack = track;
		trackGenreInfoText.setText(track.getGenre());
		trackNameInfoText.setText(track.getName());
		trackDescriptionInfoText.setText(track.getDescription());
		trackDownloadCountInfoText.setText(String.valueOf(track.getDownloadCount()));
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
