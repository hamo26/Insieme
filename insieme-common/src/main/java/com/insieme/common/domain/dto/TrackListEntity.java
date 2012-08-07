package com.insieme.common.domain.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * Simple track list POJO structured to be serialized by JOOQ and mapped to the appropriate fields.
 * I chose to override the DTO generated by JOOQ although I kept it in the core database package for reference.
 */
public class TrackListEntity extends RestEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1689478793019954652L;
	private final Collection<TrackEntity> trackList;
	
	/**
	 * Default constructor.
	 *
	 * @param trackList the track list
	 */
	public TrackListEntity(final Collection<TrackEntity> trackList) {
		this.trackList = trackList;
	}

	/**
	 * Gets the track list.
	 *
	 * @return the track list
	 */
	public Collection<TrackEntity> getTrackList() {
		return trackList;
	}
	
	

}
