package com.omeerfk.dizitakibi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.omeerfk.dizitakibi.model.Countdown;
import com.omeerfk.dizitakibi.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Faruk Karadeniz on 2.08.2017.
 */

public class Database {

    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    public Database(Context context){
        this.dbHelper = new MyDatabaseHelper(context);
    }

    public void open(){
        this.database = dbHelper.getWritableDatabase();
        Log.e(TAG, "Database is opened");
    }

    public void close(){
        dbHelper.close();
        Log.e(TAG, "Database is closed");
    }

    private void addCountdown(Countdown countdown){
        ContentValues values = new ContentValues();

        values.put(DiziFields.CountdownDB.COLUMN_EPISODE_NAME, countdown.getName());
        values.put(DiziFields.CountdownDB.COLUMN_SEASON, countdown.getSeason());
        values.put(DiziFields.CountdownDB.COLUMN_EPISODE, countdown.getEpisode());
        values.put(DiziFields.CountdownDB.COLUMN_DATE, countdown.getAirDate());

        long id = database.insert(DiziFields.CountdownDB.TABLE_NAME, null, values);
        countdown.setId((int) id);

        if (id != -1){
            Log.e(TAG, countdown.getName() + " (" + countdown.getId() +") database'e eklenmistir");
        }else{
            Log.e(TAG, countdown.getName() + " database'e eklenemedi");
        }
    }

    public void addTvShow(TvShow show){

        addCountdown(show.getCountdown());
        ContentValues values = new ContentValues();

        values.put(DiziFields.ShowDB.COLUMN_ID, show.getId());
        values.put(DiziFields.ShowDB.COLUMN_NAME, show.getName());
        values.put(DiziFields.ShowDB.COLUMN_IMAGE, show.getImageUrl());
        values.put(DiziFields.ShowDB.COLUMN_NETWORK, show.getNetwork());
        values.put(DiziFields.ShowDB.COLUMN_COUNTDOWN, show.getCountdown().getId());

        long id = database.insert(DiziFields.ShowDB.TABLE_NAME, null, values);
        if (id != -1){
            Log.e(TAG, show.getName() + " was added to the database. ");
        }else{
            Log.e(TAG, "ERROR: " + show.getName() + " not added to the data base ");
        }
    }

    private void removeCountdown(Countdown countdown){
        int i = database.delete(DiziFields.CountdownDB.TABLE_NAME,
                DiziFields.CountdownDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(countdown.getId())});

        if (i > 0){
            Log.e(TAG, countdown.getName() + " was deleted from database.");
        }else{
            Log.e(TAG, "ERROR : " + countdown.getName() + " was not deleted.");
        }

    }

    public void removeTvShow(TvShow show){
        removeCountdown(show.getCountdown());

        int i = database.delete(DiziFields.ShowDB.TABLE_NAME,
                DiziFields.ShowDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(show.getId())});

        if (i > 0){
            Log.e(TAG, show.getName() + " was deleted from database.");
        }else{
            Log.e(TAG, "ERROR : " + show.getName() + " was not deleted.");
        }
    }

    //tvshow icerisindeki countdown güncellenir
    public void updateTvShowCountdown(TvShow show){
        Countdown countdown = show.getCountdown();
        //todo db'den get countdown methodunu yaptıktan sonra show.setcountdown yapılabilir

        ContentValues values = new ContentValues();

        values.put(DiziFields.CountdownDB.COLUMN_EPISODE_NAME, countdown.getName());
        values.put(DiziFields.CountdownDB.COLUMN_SEASON, countdown.getSeason());
        values.put(DiziFields.CountdownDB.COLUMN_EPISODE, countdown.getEpisode());
        values.put(DiziFields.CountdownDB.COLUMN_DATE, countdown.getAirDate());

        int i = database.update(DiziFields.CountdownDB.TABLE_NAME, values,
                DiziFields.CountdownDB.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(show.getCountdown().getId())});

        if (i > 0){
            Log.e(TAG, show.getName() + " was updated.");
        }else{
            Log.e(TAG, "ERROR : " + show.getName() + " was not updated.");
        }
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
            tvShow.setCountdown(countDown);

            shows.add(tvShow);
        }
        cursor.close();

        return shows;
    }

    private Countdown getCountdown(int id){
        Countdown countdown = new Countdown();
        String sql = "SELECT * FROM " +
                DiziFields.CountdownDB.TABLE_NAME + " WHERE " +
                DiziFields.CountdownDB.COLUMN_ID + " = ? ";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        countdown.setId(id);
        countdown.setName(cursor.getString(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_EPISODE_NAME)));
        countdown.setEpisode(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_EPISODE)));
        countdown.setSeason(cursor.getInt(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_SEASON)));
        countdown.setAirDate(cursor.getString(cursor.getColumnIndex(DiziFields.CountdownDB.COLUMN_DATE)));
        cursor.close();

        return countdown;
    }

    public boolean isTvShowInDatabase(int id){
        String sql = "SELECT * FROM " + DiziFields.ShowDB.TABLE_NAME +
                " WHERE " + DiziFields.ShowDB.COLUMN_ID + " = ? ";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.getCount() == 1){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

}
