package com.example.gallery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gallery.model.Memory;
import com.example.gallery.model.Photo;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "GALLERY.DB";
    public static final String PHOTO_TABLE = "PHOTO";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String PATH = "PATH";
    public static final String ALBUM = "ALBUM";
    public static final String MEMORY_ID = "MEMORY_ID";
    public static final String CREATED_DATE = "CREATED_DATE";

    public static final String MEMORY_TABLE = "MEMORY";
    public static final String MEMORY_NAME = "NAME";
    public static final String MEMORY_DATE = "DATE";
    public static final String MEMORY_PLACE = "PLACE";
    public static final String MEMORY_COVER_IMAGE= "COVER_IMAGE";

    private static DatabaseHelper Instance = null;

    public static DatabaseHelper getInstance(Context context){
        if (Instance == null){
            Instance = new DatabaseHelper(context);
        }
        return Instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s " +
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                MEMORY_TABLE, ID, MEMORY_NAME, MEMORY_PLACE, MEMORY_DATE, MEMORY_COVER_IMAGE));
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s " +
                "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER," +
                "FOREIGN KEY (%s) REFERENCES %s(%s));",
                PHOTO_TABLE, ID, TITLE, PATH, ALBUM, CREATED_DATE, MEMORY_ID, MEMORY_ID, MEMORY_TABLE, ID));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PHOTO_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MEMORY_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertPhoto(Photo photo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, photo.getTitle());
        contentValues.put(PATH, photo.getPath());
        contentValues.put(ALBUM, photo.getAlbum());
        contentValues.put(CREATED_DATE, String.valueOf(photo.getCreatedDate()));
        contentValues.put(MEMORY_ID, photo.getMemoryId());
        return db.insert(PHOTO_TABLE, null, contentValues) != -1;
    }

    public boolean insertMemory(Memory memory){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEMORY_NAME, memory.getName());
        contentValues.put(MEMORY_DATE, String.valueOf(memory.getDate()));
        contentValues.put(MEMORY_PLACE, memory.getPlace());
        contentValues.put(MEMORY_COVER_IMAGE, memory.getCover_image());
        return db.insert(MEMORY_TABLE, null, contentValues) != -1;
    }

    public Cursor getAllMemories(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + MEMORY_TABLE, null);
        return res;
    }

    public Cursor getPhotosByMemory(int memoryId){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery(String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.%s WHERE %s.%s = ?;",
                                                    PHOTO_TABLE, MEMORY_TABLE, PHOTO_TABLE, MEMORY_ID, MEMORY_TABLE, ID, MEMORY_TABLE, ID),
                                    new String[] {String.valueOf(memoryId)});
        return res;
    }
}
