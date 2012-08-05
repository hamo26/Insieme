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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.artist.task.impl.FindArtistsTask;
import com.insieme.android.artist.task.impl.GetArtistTask;
import com.insieme.android.artist.task.impl.RegisterArtistTask;
import com.insieme.android.constants.InsiemeAndroidConstants;
import com.insieme.common.domain.dto.ArtistEntity;
import com.insieme.common.domain.dto.ArtistListEntity;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.rest.RestResult;

/**
 * Insieme artist activity. 
 * 
 * This class includes the ability to register as an artist and search for artists
 * to be able to go to their pages and see their music.
 * 
 * TODO: The code is acceptable but needs to be cleaned up and tested at some point.
 */
@ContentView(R.layout.artist_info_activity)
public class ArtistInfoActivity extends RoboActivity {
	
	private String userId;
	
	@InjectView(R.id.artistListLayoutId)
	private LinearLayout artistListLayout;
	
	@InjectView(R.id.registerArtistLayoutId)
	private RelativeLayout registerArtistLayout;
	
	@InjectView(R.id.searchArtistNameInputId)
	private EditText searchArtistNameInput;
	
	@InjectView(R.id.artistSearchButtonId)
	private Button artistSearchButton;
	
	@InjectView(R.id.artistListScrollViewId)
	private ScrollView artistListScrollView;
	
	@InjectView(R.id.artistNameValueTextId)
	private TextView artistInfoNameValue;
	
	@InjectView(R.id.artistGenreValueTextId)
	private TextView artistInfoGenreValue;
	
	@InjectView(R.id.registerArtistNameInputId)
	private EditText registerArtistNameInput;
	
	@InjectView(R.id.registerArtistGenreInputId)
	private EditText registerArtistGenreInput;
	
	@Inject
	private Provider<FindArtistsTask>findArtistsTaskProvider; 
	
	@Inject
	private Provider<GetArtistTask> getArtistTaskProvider;
	
	@Inject
	private Provider<RegisterArtistTask> registerArtistTaskProvider;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//If a user is registered we want to make the registration layout invincible.
		this. userId = (String) savedInstanceState.get(InsiemeAndroidConstants.USER_ID);
		if (savedInstanceState == null || isArtist(this.userId)) {
			registerArtistLayout.setVisibility(View.INVISIBLE);
		}
    }
	
	/**
	 * Search artist action when search button is pressed.
	 *
	 * @param v the view
	 */
	public void searchArtistAction(View v) {
		String artistName = searchArtistNameInput.getText().toString();
		
		try {
			RestResult<ArtistListEntity> result = findArtistsTaskProvider.get().execute(artistName).get();
			if (result.isFailure()) {
				InsiemeExceptionEntity insiemeExceptionEntity = result.getError();
    			Toast.makeText(this, insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
			} else {
				populateArtistList(result.getRestResult().getArtists());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Registers an artist if that artist is a user.
	 *
	 * @param v the v
	 */
	public void registerArtistAction(View v) {
		String artistName = registerArtistNameInput.getText().toString();
		String artistGenre = registerArtistGenreInput.getText().toString();
		
		try {
			RestResult<ArtistEntity> restResult = registerArtistTaskProvider.get()
					.execute(this.userId, artistName, artistGenre)
					.get();
			if (restResult.isFailure()) {
				InsiemeExceptionEntity insiemeExceptionEntity = restResult.getError();
    			Toast.makeText(this, insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
			} else {
				ArtistEntity newArtist = restResult.getRestResult();
				Toast.makeText(this, "Welcome: " + newArtist.getName(), Toast.LENGTH_LONG).show();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Populated the artists tab with the artists returned from a query.
	 *
	 * @param artists the artists
	 */
	private void populateArtistList(Collection<ArtistEntity> artists) {
		ArtistButton artistButton;
		artistListLayout.removeAllViews();
		if (artists.isEmpty()) {
			return;
		} else {
			for (ArtistEntity artist : artists) {
				artistButton = new ArtistButton(this, artist);	
				artistButton.setText(artist.getName());
				artistListLayout.addView(artistButton);
			}
		}
	}
	
	/**
	 * Set the artist values in the artist info layout when an artist is clicked in 
	 * scroll view.
	 *
	 * @param artist the new artist info
	 */
	private void setArtistInfo(ArtistEntity artist) {
		artistInfoGenreValue.setText(artist.getGenre());
		artistInfoNameValue.setText(artist.getName());
	}
	
	/**
	 * Checks to see whether a user is an artist given an artist id. 
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
	
	private class ArtistButton extends Button {
		private final ArtistEntity artist;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				setArtistInfo(artist);
			}
		};
		
		public ArtistButton(Context context, ArtistEntity artist) {
			super(context);
			this.artist = artist;
			setOnClickListener(clicker);
		}
	}
}
