package com.insieme.core.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.insieme.common.domain.dto.TrackEntity;
import com.mysql.jdbc.StringUtils;

public class TrackValidator {

	@SuppressWarnings("serial")
	public static Collection<String> getMissingTrackFields(final TrackEntity trackEntity) {
		Collection<String> missingTrackFields = new ArrayList<String>();

		Map<String, String> trackPropertiesMap = new LinkedHashMap<String, String>() {
			{
				put(TrackEntity.ARTIST_ID, trackEntity.getArtistId());
				put(TrackEntity.DESCRIPTION, trackEntity.getDescription());
				put(TrackEntity.GENRE, trackEntity.getGenre());
				put(TrackEntity.NAME, trackEntity.getName());
				put(TrackEntity.TRACK_ID, trackEntity.getTrackId());
			}
		};

		for (Entry<String, String> entry : trackPropertiesMap.entrySet()) {
			if (StringUtils.isNullOrEmpty(entry.getValue())) {
				missingTrackFields.add(entry.getKey());
			}
		}
		return missingTrackFields;
	}
}
