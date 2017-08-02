package com.omeerfk.dizitakibi.events;

import com.omeerfk.dizitakibi.model.Show;

import java.util.List;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

public class ListEvent {
    private List<Show> shows;
    public ListEvent(List<Show> shows){
        this.shows = shows;
    }

    public List<Show> getShows() {
        return shows;
    }
}
