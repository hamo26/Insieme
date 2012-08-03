package com.insieme.android.track.task.impl;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import android.os.AsyncTask;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.insieme.common.domain.dto.TrackEntity;
import com.insieme.common.domain.rest.RestResult;
import com.insieme.common.domain.rest.RestResultHandler;

public class FtpTrackDownloadTask extends AsyncTask<String, Integer, RestResult<TrackEntity>>{
	
	
	private final RestResultHandler restResultHandler;
	private final FTPClient ftpClient;
	
	@Inject
	public FtpTrackDownloadTask(
							@Named("ftpClient")
							final FTPClient ftpClient,
							@Named("restResultHandler")
							final RestResultHandler restResultHandler) {
		super();
		this.ftpClient = ftpClient;
		this.restResultHandler = restResultHandler;
	}

	@Override
	protected RestResult<TrackEntity> doInBackground(String... params) {
		try {
			boolean login = ftpClient.login("", "");
			return restResultHandler.createRestResult(params[0], TrackEntity.class);
		} catch (IOException e) {
			return restResultHandler.createErrorResult(e.getLocalizedMessage(), TrackEntity.class);
		}
	}
	
	
}
