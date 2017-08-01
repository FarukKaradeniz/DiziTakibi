package com.omeerfk.dizitakibi.events;

import com.omeerfk.dizitakibi.model.TvShow;

import java.util.List;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

public class ListEvent {
    private List<TvShow> shows;
    public ListEvent(List<TvShow> shows){
        this.shows = shows;
    }

    public List<TvShow> getShows() {
        return shows;
    }
}
