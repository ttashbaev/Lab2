package com.example.timur.labvacancies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.timur.labvacancies.data.dto.VacancyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "VACANCIES";
    private final static int DB_VERSION = 2;

    private final static String TABLE_VAC = "VAC_TABLE";
    private final static String TABLE_FAV = "FAVOURITES_TABLE";
    private final static String TABLE_VIEWED = "VIEWED_TABLE";
    private final static String ID = "_id";
    private final static String PID = "PID";
    private final static String HEADER = "HEADER";
    private final static String PROFILE = "PROFILE";
    private final static String SALARY = "SALARY";
    private final static String TELEPHONE = "TELEPHONE";
    private final static String DATA = "DATA";
    private final static String PROFESSION = "PROFESSION";
    private final static String SITE_ADDRESS = "SITE_ADDRESS";
    private final static String BODY = "BODY";
    private final static String VIEWED_VAC = "VIEWED_VAC";

    private final static String CREATE_VIEWED_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_VIEWED + "(" +
            ID + " INTEGER_PRIMARY_KEY, " +
            PID + " TEXT, " +
            VIEWED_VAC + " TEXT " +
            ");";



    private final static String CREATE_TABLE_VAC = "CREATE TABLE IF NOT EXISTS " +
            TABLE_VAC + " (" +
            ID + " INTEGER_PRIMARY_KEY, " +
            PID + " TEXT, " +
            HEADER + " TEXT, " +
            PROFILE + " TEXT, " +
            SALARY + " TEXT, " +
            TELEPHONE + " TEXT, " +
            DATA + " TEXT, " +
            PROFESSION + " TEXT, " +
            SITE_ADDRESS + " TEXT, " +
            BODY + " TEXT" +
            ");";

    private final static String CREATE_TABLE_FAV = "CREATE TABLE IF NOT EXISTS " +
            TABLE_FAV + " (" +
            ID + " INTEGER_PRIMARY_KEY, " +
            PID + " TEXT, " +
            HEADER + " TEXT, " +
            PROFILE + " TEXT, " +
            SALARY + " TEXT, " +
            TELEPHONE + " TEXT, " +
            DATA + " TEXT, " +
            PROFESSION + " TEXT, " +
            SITE_ADDRESS + " TEXT, " +
            BODY + " TEXT" +
            ");";


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_VAC);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAV);
        sqLiteDatabase.execSQL(CREATE_VIEWED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VAC);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIEWED);
        }

        onCreate(sqLiteDatabase);
    }

    public void saveViewedVacancy(VacancyModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VIEWED_VAC, model.isViewed());
        cv.put(PID, model.getPid());
        /*int update = db.update(TABLE_VIEWED, cv, VIEWED_VAC + " = ?", new String[] {vac});
        if (update <= 0) db.insert(TABLE_VIEWED, null, cv);*/
        long rowID = db.insert(TABLE_VIEWED, null, cv);
        Log.d("TABLE_VIEWED SAVED ", "row ID: " + rowID);
        db.close();

    }

    public void saveVacancies(List<VacancyModel> arrayList) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < arrayList.size(); i++) {
            VacancyModel model = arrayList.get(i);
            cv.put(ID, (i + 1));
            cv.put(PID, model.getPid());
            cv.put(HEADER, model.getHeader());
            cv.put(PROFILE, model.getProfile());
            cv.put(SALARY, model.getSalary());
            cv.put(TELEPHONE, model.getTelephone());
            cv.put(DATA, model.getData());
            cv.put(PROFESSION, model.getProfession());
            cv.put(SITE_ADDRESS, model.getSiteAddress());
            cv.put(BODY, model.getBody());

            long rowID = db.insert(TABLE_VAC, null, cv);
            Log.d("DATA SAVED ", "row ID: " + rowID);
        }
        db.close();
    }


    public void saveFavouriteVacancy(List<VacancyModel> arrayList, int position) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        VacancyModel model = arrayList.get(position);

        //cv.put(ID, (position + 1));
        cv.put(PID, model.getPid());
        cv.put(HEADER, model.getHeader());
        cv.put(PROFILE, model.getProfile());
        cv.put(SALARY, model.getSalary());
        cv.put(TELEPHONE, model.getTelephone());
        cv.put(DATA, model.getData());
        cv.put(PROFESSION, model.getProfession());
        cv.put(SITE_ADDRESS, model.getSiteAddress());
        cv.put(BODY, model.getBody());

        long rowID = db.insert(TABLE_FAV, null, cv);
        Log.d("DATA SAVED ", "row ID: " + rowID);

        db.close();
    }

    public ArrayList<VacancyModel> getAllVacancies() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<VacancyModel> modelArrayList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_VAC,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int pidIndex = cursor.getColumnIndex(PID);
            int headerIndex = cursor.getColumnIndex(HEADER);
            int profileIndex = cursor.getColumnIndex(PROFILE);
            int salaryIndex = cursor.getColumnIndex(SALARY);
            int telephoneIndex = cursor.getColumnIndex(TELEPHONE);
            int dataIndex = cursor.getColumnIndex(DATA);
            int professionIndex = cursor.getColumnIndex(PROFESSION);
            int siteAddressIndex = cursor.getColumnIndex(SITE_ADDRESS);
            int bodyIndex = cursor.getColumnIndex(BODY);
            do {
                VacancyModel model = new VacancyModel();
                model.setPid(cursor.getString(pidIndex));
                model.setHeader(cursor.getString(headerIndex));
                model.setProfile(cursor.getString(profileIndex));
                model.setSalary(cursor.getString(salaryIndex));
                model.setTelephone(cursor.getString(telephoneIndex));
                model.setData(cursor.getString(dataIndex));
                model.setProfession(cursor.getString(professionIndex));
                model.setSiteAddress(cursor.getString(siteAddressIndex));
                model.setBody(cursor.getString(bodyIndex));
                modelArrayList.add(model);
            } while (cursor.moveToNext());
            Log.d("Data received ", "amount:" + cursor.getCount());
        } else {
            Log.d("Data is empty ", "amount:" + cursor.getCount());
        }
        cursor.close();
        db.close();
        return modelArrayList;
    }

    public List<VacancyModel> getFavouriteVacancies() {
        SQLiteDatabase db = getWritableDatabase();
        List<VacancyModel> modelArrayList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_FAV,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            int pidIndex = cursor.getColumnIndex(PID);
            int headerIndex = cursor.getColumnIndex(HEADER);
            int profileIndex = cursor.getColumnIndex(PROFILE);
            int salaryIndex = cursor.getColumnIndex(SALARY);
            int telephoneIndex = cursor.getColumnIndex(TELEPHONE);
            int dataIndex = cursor.getColumnIndex(DATA);
            int professionIndex = cursor.getColumnIndex(PROFESSION);
            int siteAddressIndex = cursor.getColumnIndex(SITE_ADDRESS);
            int bodyIndex = cursor.getColumnIndex(BODY);
            do {
                VacancyModel model = new VacancyModel();
                model.setPid(cursor.getString(pidIndex));
                model.setHeader(cursor.getString(headerIndex));
                model.setProfile(cursor.getString(profileIndex));
                model.setSalary(cursor.getString(salaryIndex));
                model.setTelephone(cursor.getString(telephoneIndex));
                model.setData(cursor.getString(dataIndex));
                model.setProfession(cursor.getString(professionIndex));
                model.setSiteAddress(cursor.getString(siteAddressIndex));
                model.setBody(cursor.getString(bodyIndex));
                modelArrayList.add(model);
            } while (cursor.moveToNext());
            Log.d("Data received ", "amount:" + cursor.getCount());
        } else {
            Log.d("Data is empty ", "amount:" + cursor.getCount());
        }
        cursor.close();
        db.close();
        return modelArrayList;
    }

    public String getFavItem(String name) {

        SQLiteDatabase db = getWritableDatabase();
        //String id = name;

        //List<VacancyModel> modelArrayList = new ArrayList<>();
        Cursor cursor = null;
        String empName = "";

        cursor = db.rawQuery("SELECT * FROM " + TABLE_FAV + " WHERE " + PID +  " =?", new String[]{name});

        if (cursor.moveToFirst()) {
            int pidIndex = cursor.getColumnIndex(PID);
            do {
                VacancyModel model = new VacancyModel();
                model.setPid(cursor.getString(pidIndex));
                empName = cursor.getString(pidIndex);
                String list = Arrays.toString(new String[]{name});
                Log.d("ITEM get " + empName  , "Row: " + list);

            } while (cursor.moveToNext());

        }
        cursor.close();
        //Log.d("ITEM get " + name, "Row: " + name);
        db.close();
        return empName;
    }

    public String getViewedItem(String name) {

        SQLiteDatabase db = getWritableDatabase();
        //String id = name;

        //List<VacancyModel> modelArrayList = new ArrayList<>();
        Cursor cursor = null;
        String empName = "";

        cursor = db.rawQuery("SELECT * FROM " + TABLE_VIEWED + " WHERE " + PID +  " =?", new String[]{name});

        if (cursor.moveToFirst()) {
            int pidIndex = cursor.getColumnIndex(PID);
            do {
                VacancyModel model = new VacancyModel();
                model.setPid(cursor.getString(pidIndex));
                empName = cursor.getString(pidIndex);
                String list = Arrays.toString(new String[]{name});
                Log.d("ITEM get " + empName  , "VIEWED: " + list);

            } while (cursor.moveToNext());

        }
        cursor.close();
        //Log.d("ITEM get " + name, "Row: " + name);
        db.close();
        return empName;
    }



    public void clearFavouriteVacancies() {
        SQLiteDatabase db = getWritableDatabase();
        long clear = db.delete(TABLE_VAC, null, null);
        Log.d("DATA CLEARED ", "amount: " + clear);
        db.close();
    }

    public void clearVacancies() {
        SQLiteDatabase db = getWritableDatabase();
        long clear = db.delete(TABLE_VAC, null, null);
        Log.d("DATA CLEARED ", "amount: " + clear);
        db.close();
    }

    public void deleteItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FAV + " WHERE " + PID + "= '" + name + "'");
        Log.d("ITEM DELETED ", "Row: " + name);
        db.close();
    }

    /*public boolean tableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        ("SELECT * FROM " + TABLE_VIEWED + " WHERE " + PID +  " =?", new String[]{name});
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_NAME + "WHERE"  type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }*/

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        boolean isExist = false;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_NAME + " WHERE " + TABLE_VIEWED + "= '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            }
            cursor.close();
        }
        return isExist;
    }
}
