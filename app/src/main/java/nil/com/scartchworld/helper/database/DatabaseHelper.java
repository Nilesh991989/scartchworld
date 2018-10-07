package nil.com.scartchworld.helper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scratchworld.db";
    private static final String TABLE_NAME = "data";
    private static final String NAME_COLUMN = "name";
    private static final String VALUE_COLUMN = "value";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_JOURNEY_TABLE = "CREATE TABLE IF NOT EXISTS " +  TABLE_NAME
                + "(" + NAME_COLUMN  + " TEXT primary key,"
                + VALUE_COLUMN + " TEXT)";
        db.execSQL(CREATE_JOURNEY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addKeyValue(String name, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN, name);
        contentValues.put(VALUE_COLUMN,value);
        long id = db.insertWithOnConflict(TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_IGNORE);
        if(id == -1){
            db.update(TABLE_NAME,contentValues,"name=?",new String[]{name});
        }
    }

    public String getValue(String keyName){
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = "SELECT value FROM data where name = '%s'";
        rawQuery = String.format(rawQuery,keyName);
        Cursor cursor =  db.rawQuery(rawQuery, null);
        String value = null;
        if (cursor.moveToFirst()) {
            do {
                value = cursor.getString(0);
                break;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return value;
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }
}
