package com.omeerfk.dizitakibi.events;

import com.omeerfk.dizitakibi.model.TvShow;

import java.util.List;

public class RemindersEvent {
    private List<TvShow> shows;
    public RemindersEvent(List<TvShow> shows){
        this.shows = shows;
    }

    public List<TvShow> getShows() {
        return shows;
    }
}
