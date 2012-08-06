package com.insieme.android;

import java.util.concurrent.ExecutionException;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.artist.task.impl.GetArtistTask;
import com.insieme.android.constants.InsiemeAndroidConstants;
import com.insieme.common.domain.dto.ArtistEntity;
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
	
	private volatile UserEntity currentUser;
	
	@Inject
	Provider<GetArtistTask> getArtistTaskProvider;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (UserEntity) getIntent().getSerializableExtra(InsiemeAndroidConstants.USER_ID);
        
        if (!isArtist(currentUser.getUserId())) {
        	myArtistPageLayout.setVisibility(View.INVISIBLE);
        }
        
        Button newTrack;
        
        for(int i = 0; i<100; i++) {
        	newTrack = new Button(this); 
        	newTrack.setText("track" + i);
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
		 intent.putExtra(InsiemeAndroidConstants.USER_ID, currentUser.getUserId());
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
	
}
