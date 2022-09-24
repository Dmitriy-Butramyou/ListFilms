package com.butramyou.listoffilms.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.butramyou.listoffilms.model.Film;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "filmsManager";
    private static final String TABLE_CONTACTS = "films";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IS_VIEWED = "is_viewed";
    private static final String KEY_IS_DOWNLOADED = "is_downloaded";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FILMS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IS_VIEWED + " TEXT," + KEY_IS_DOWNLOADED + " TEXT" + ")";
//                + KEY_IS_VIEWED + " TEXT" + ")";
        db.execSQL(CREATE_FILMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (newVersion == 2 && newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE films ADD COLUMN is_downloaded TEXT DEFAULT 'false'");
//        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IS_VIEWED, film.isViewed().toString());
        values.put(KEY_IS_DOWNLOADED, film.isDownloaded().toString());
        values.put(KEY_NAME, film.getName());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public Film getFilm(int id) {
        Film film = new Film();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME, KEY_IS_VIEWED, KEY_IS_DOWNLOADED}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            film = new Film(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Boolean.parseBoolean(cursor.getString(2)),
                    Boolean.parseBoolean(cursor.getString(3)));
        }

        return film;
    }

    public List<Film> getFilms(boolean isViewed) {
        List<Film> films = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME, KEY_IS_VIEWED, KEY_IS_DOWNLOADED}, KEY_IS_VIEWED + "=?",
                new String[]{String.valueOf(isViewed)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(0)));
                film.setName(cursor.getString(1));
                film.setViewed(Boolean.parseBoolean(cursor.getString(2)));
                film.setDownloaded(Boolean.parseBoolean(cursor.getString(3)));

                films.add(film);
            } while (cursor.moveToNext());
        }

        return films;
    }

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(Integer.parseInt(cursor.getString(0)));
                film.setName(cursor.getString(1));
                film.setViewed(Boolean.parseBoolean(cursor.getString(2)));
                film.setDownloaded(Boolean.parseBoolean(cursor.getString(3)));

                films.add(film);
            } while (cursor.moveToNext());
        }

        return films;
    }

    public int updateFilm(Film film) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IS_VIEWED, film.isViewed().toString());
        values.put(KEY_IS_DOWNLOADED, film.isDownloaded().toString());
        values.put(KEY_NAME, film.getName());

        return db.update(TABLE_CONTACTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(film.getId())});
    }

    public void deleteFilm(int filmId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + "=?",
                new String[]{String.valueOf(filmId)});
        db.close();
    }

    public int getFilmsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public Film updateViewedStatus(Film film) {
        film.setViewed(!film.isViewed());
        return film;
    }

    public Film updatingDownloadStatus(Film film) {
        film.setDownloaded(!film.isDownloaded());
        return film;
    }

}
