package com.poc.assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.poc.assignment.model.AlbumDataModel;

import java.util.ArrayList;

public class DBManager {
    private static DBManager instance;
    private static final String TAG = "DBManager";
    private static final String TABLEA_ALBUM = "DB_Album";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_ALBUM_ID = "albumId";
    private static final String KEY_ALBUM_TITLE = "title";
    private SQLiteDatabase database;
    DatabaseHelper databaseHelper;


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLEA_ALBUM + " ( "
            + KEY_USER_ID + " VARCHAR, "
            + KEY_ALBUM_ID + " VARCHAR, "
            + KEY_ALBUM_TITLE + " VARCHAR)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLEA_ALBUM;

    private final Context context;

    public static DBManager getInstance(Context context) {
        if(instance == null){
            instance = new DBManager(context);
        }
        return instance;
    }

    private DBManager(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public DBManager open() throws SQLException {
//        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }


    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public synchronized long insert(AlbumDataModel albumModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID, albumModel.getUserId());
        contentValues.put(KEY_ALBUM_ID, albumModel.getAlbumId());
        contentValues.put(KEY_ALBUM_TITLE, albumModel.getTitle());
        long insertedRowId = database.insert(TABLEA_ALBUM, null, contentValues);
        return insertedRowId;
    }




    public synchronized void insert(ArrayList<AlbumDataModel> data) {
        for (int index = 0; index < data.size(); index++) {
            long insertedRowId = insert(data.get(index));
            Log.v(TAG,insertedRowId+" row inserted");
        }
    }

    public long delete() {
        String whereClause = null;
        String[] whereArgs = null;
        long deletedRowId = database.delete(TABLEA_ALBUM, whereClause, whereArgs);
        Log.v(TAG, String.format("delete() :: No.Of Rows Deleted = %d", deletedRowId));
        return deletedRowId;
    }

    public synchronized ArrayList<AlbumDataModel> getAlbumList() {
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        ArrayList<AlbumDataModel> mediaArrayList = new ArrayList<AlbumDataModel>();
        Cursor cursor = database.query(TABLEA_ALBUM, columns, selection, selectionArgs, groupBy, having, orderBy);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                AlbumDataModel downloadFile = getAllList(cursor);
                mediaArrayList.add(downloadFile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        cursor = null;
        return mediaArrayList;
    }


    private AlbumDataModel getAllList(Cursor cursor) {
        AlbumDataModel downloadFile = new AlbumDataModel();
        int userIdColumnIndex = cursor.getColumnIndex(KEY_USER_ID);
        int albumIdColumnIndex = cursor.getColumnIndex(KEY_ALBUM_ID);
        int titleColumnIndex = cursor.getColumnIndex(KEY_ALBUM_TITLE);

        downloadFile.setUserId(cursor.getString(userIdColumnIndex));
        downloadFile.setAlbumId(cursor.getString(albumIdColumnIndex));
        downloadFile.setTitle(cursor.getString(titleColumnIndex));
        return downloadFile;
    }


}
