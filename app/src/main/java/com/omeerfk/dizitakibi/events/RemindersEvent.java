package com.omeerfk.dizitakibi.events;

import com.omeerfk.dizitakibi.model.TvShow;

import java.util.List;

/**
 * Created by Faruk Karadeniz on 18.08.2017.
 */

public class RemindersEvent {
    private List<TvShow> shows;
    public RemindersEvent(List<TvShow> shows){
        this.shows = shows;
    }

    public List<TvShow> getShows() {
        return shows;
    }
}
