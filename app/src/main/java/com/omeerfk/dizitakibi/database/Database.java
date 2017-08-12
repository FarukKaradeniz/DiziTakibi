package com.omeerfk.dizitakibi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.omeerfk.dizitakibi.model.Countdown;
import com.omeerfk.dizitakibi.model.Show;
import com.omeerfk.dizitakibi.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Faruk Karadeniz on 2.08.2017.
 */

public class Database {

    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    public Database(Context context){
        this.dbHelper = new MyDatabaseHelper(context);
    }

    public void open(){
        this.database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    private void addCountdown(Countdown countdown){
        ContentValues values = new ContentValues();

        values.put(DiziFields.CountdownDB.COLUMN_EPISODE_NAME, countdown.getName());
        values.put(DiziFields.CountdownDB.COLUMN_SEASON, countdown.getSeason());
        values.put(DiziFields.CountdownDB.COLUMN_EPISODE, countdown.getEpisode());
        values.put(DiziFields.CountdownDB.COLUMN_DATE, countdown.getAirDate());
        values.put(DiziFields.CountdownDB.COLUMN_SHOW_ID, countdown.getShowId());

        long id = database.insert(DiziFields.CountdownDB.TABLE_NAME, null, values);
        countdown.setId((int) id);
    }

    public void addTvShow(TvShow show){
        ContentValues values = new ContentValues();
        if (show.getCountdown() != null) {
            addCountdown(show.getCountdown());
            values.put(DiziFields.ShowDB.COLUMN_COUNTDOWN, show.getCountdown().getId());
        }
        values.put(DiziFields.ShowDB.COLUMN_ID, show.getId());
        values.put(DiziFields.ShowDB.COLUMN_NAME, show.getName());
        values.put(DiziFields.ShowDB.COLUMN_IMAGE, show.getImageUrl());
        values.put(DiziFields.ShowDB.COLUMN_NETWORK, show.getNetwork());

        database.insert(DiziFields.ShowDB.TABLE_NAME, null, values);
    }

    private void removeCountdown(Countdown countdown){
        if (countdown == null) return;
        database.delete(DiziFields.CountdownDB.TABLE_NAME,
                DiziFields.CountdownDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(countdown.getId())});


    }

    public void removeTvShow(TvShow show){
        removeCountdown(show.getCountdown());
        database.delete(DiziFields.ShowDB.TABLE_NAME,
                DiziFields.ShowDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(show.getId())});

    }

    public void removeShow(Show show){
        database.delete(DiziFields.ShowDB.TABLE_NAME,
                DiziFields.ShowDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(show.getId())});
    }

    public void removeCountdownIfShowDoesNotExist(){
        ArrayList<Countdown> countdowns = getAllCountdowns();
        for (int i=0 ; i<countdowns.size() ; i++){
            if (getTvShowById(countdowns.get(i).getShowId()) != null){
                removeCountdown(countdowns.get(i));
            }
        }
    }

    private ArrayList<Countdown> getAllCountdowns(){
        ArrayList<Countdown> countdowns = new ArrayList<>();
        String sql = "SELECT * FROM " + DiziFields.CountdownDB.TABLE_NAME;

        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            Countdown countdown = new Countdown();

            countdown.setId(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_ID)));
            countdown.setName(cursor.getString(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_EPISODE_NAME)));
            countdown.setAirDate(cursor.getString(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_DATE)));
            countdown.setEpisode(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_EPISODE)));
            countdown.setSeason(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_SEASON)));
            countdown.setShowId(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_SHOW_ID)));

            countdowns.add(countdown);
        }
        cursor.close();

        return countdowns;
    }

    //tvshow icerisindeki countdown güncellenir
    public void updateTvShowCountdown(TvShow show){
        Countdown countdown = show.getCountdown();

        ContentValues values = new ContentValues();

        values.put(DiziFields.CountdownDB.COLUMN_EPISODE_NAME, countdown.getName());
        values.put(DiziFields.CountdownDB.COLUMN_SEASON, countdown.getSeason());
        values.put(DiziFields.CountdownDB.COLUMN_EPISODE, countdown.getEpisode());
        values.put(DiziFields.CountdownDB.COLUMN_DATE, countdown.getAirDate());

        show.setCountdown(getCountdown(show.getCountdown().getId()));

        database.update(DiziFields.CountdownDB.TABLE_NAME, values,
                DiziFields.CountdownDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(show.getCountdown().getId())});
    }

    public ArrayList<TvShow> getTvShows(){
        ArrayList<TvShow> shows = new ArrayList<>();
        String sql = "SELECT * FROM " + DiziFields.ShowDB.TABLE_NAME;

        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            TvShow tvShow = new TvShow();

            tvShow.setFavorited(true);
            tvShow.setId(cursor.getInt(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_ID)));
            tvShow.setName(cursor.getString(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_NAME)));
            tvShow.setNetwork(cursor.getString(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_NETWORK)));
            tvShow.setImageUrl(cursor.getString(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_IMAGE)));

            Countdown countDown = getCountdown(cursor.getInt(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_COUNTDOWN)));
            if (countDown!=null)
                tvShow.setCountdown(countDown);


            shows.add(tvShow);
        }
        cursor.close();

        return shows;
    }

    private Countdown getCountdown(int id){
        Countdown countdown = null;
        String sql = "SELECT * FROM " +
                DiziFields.CountdownDB.TABLE_NAME + " WHERE " +
                DiziFields.CountdownDB.COLUMN_ID + " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()){
            countdown = new Countdown();

            countdown.setId(id);
            countdown.setName(cursor.getString(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_EPISODE_NAME)));
            countdown.setEpisode(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_EPISODE)));
            countdown.setSeason(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_SEASON)));
            countdown.setAirDate(cursor.getString(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_DATE)));
            cursor.close();
        }
        return countdown;
    }

    public TvShow getTvShowById(int id){

        String sql = "SELECT * FROM " + DiziFields.ShowDB.TABLE_NAME + " WHERE " + DiziFields.ShowDB.COLUMN_ID
                + " = ? ";
        TvShow tvShow = null;

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()){
            tvShow = new TvShow();

            tvShow.setFavorited(true);
            tvShow.setId(cursor.getInt(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_ID)));
            tvShow.setName(cursor.getString(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_NAME)));
            tvShow.setNetwork(cursor.getString(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_NETWORK)));
            tvShow.setImageUrl(cursor.getString(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_IMAGE)));

            Countdown countDown = getCountdown(cursor.getInt(cursor.getColumnIndex(DiziFields.ShowDB.COLUMN_COUNTDOWN)));
            if (countDown!=null)
                tvShow.setCountdown(countDown);

        }
        cursor.close();
        return tvShow;
    }

}
