package com.omeerfk.dizitakibi.database;

/**
 * Created by Faruk Karadeniz on 2.08.2017.
 */

public final class DiziFields {
    public DiziFields(){}


    static final String CREATE_SHOW_TABLE =
            "CREATE TABLE " + ShowDB.TABLE_NAME + " ( " +
                    ShowDB.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    ShowDB.COLUMN_NAME + " TEXT, " +
                    ShowDB.COLUMN_NETWORK + " TEXT, " +
                    ShowDB.COLUMN_IMAGE + " TEXT, " +
                    ShowDB.COLUMN_COUNTDOWN + " INTEGER )";

    static final String CREATE_COUNTDOWN_TABLE =
            "CREATE TABLE " + CountdownDB.TABLE_NAME + " ( " +
                    CountdownDB.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CountdownDB.COLUMN_EPISODE_NAME + " TEXT, " +
                    CountdownDB.COLUMN_SEASON + " INTEGER, " +
                    CountdownDB.COLUMN_EPISODE + " INTEGER, " +
                    CountdownDB.COLUMN_SHOW_ID + " INTEGER, " +
                    CountdownDB.COLUMN_DATE + " TEXT )";

    static class ShowDB{
        static final String TABLE_NAME = "TVSHOW";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_NAME = "NAME";
        static final String COLUMN_NETWORK = "NETWORK";
        static final String COLUMN_IMAGE = "IMAGE";
        static final String COLUMN_COUNTDOWN = "COUNTDOWN_ID";
    }

    static class CountdownDB{
        static final String TABLE_NAME = "COUNTDOWN";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_EPISODE_NAME = "EPISODE_NAME";
        static final String COLUMN_SEASON = "SEASON";
        static final String COLUMN_EPISODE = "EPISODE";
        static final String COLUMN_SHOW_ID = "SHOW_ID";
        static final String COLUMN_DATE = "DATE";
    }

}
