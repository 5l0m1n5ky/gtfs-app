package com.gtfsapp.api.service.interfaces;

import com.google.transit.realtime.GtfsRealtime;

import java.util.List;

public interface GtfsFeedParser<T> {
    List<T> parse(GtfsRealtime.FeedMessage feed);
}
