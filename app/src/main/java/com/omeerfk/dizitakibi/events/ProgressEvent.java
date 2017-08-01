package com.omeerfk.dizitakibi.events;

/**
 * Created by Faruk Karadeniz on 1.08.2017.
 */

public class ProgressEvent {
    private int progress;

    public ProgressEvent(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }
}
