package com.insieme.core.service.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.insieme.common.domain.dto.ArtistEntity;
import com.mysql.jdbc.StringUtils;

/**
 * Convenience utility to validate artist fields. 
 * For now, only checks for empty fields.
 */
public class ArtistValidator {

	@SuppressWarnings("serial")
	public static Collection<String> getMissingArtistFields(final ArtistEntity artistEntity) {
		Collection<String> missingArtistFields = new ArrayList<String>();

		Map<String, String> artistPropertiesMap = new LinkedHashMap<String, String>() {
			{
				put(ArtistEntity.ARTIST_ID, artistEntity.getArtistId());
				put(ArtistEntity.GENRE, artistEntity.getGenre());
				put(ArtistEntity.ARTIST_NAME, artistEntity.getName());
			}
		};

		for (Entry<String, String> entry : artistPropertiesMap.entrySet()) {
			if (StringUtils.isNullOrEmpty(entry.getValue())) {
				missingArtistFields.add(entry.getKey());
			}
		}
		
		return missingArtistFields;
	}
}
