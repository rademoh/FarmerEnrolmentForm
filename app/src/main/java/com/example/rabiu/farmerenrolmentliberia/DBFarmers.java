package com.example.rabiu.farmerenrolmentliberia;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DBFarmers extends SQLiteOpenHelper {

    private Context context;
    public static  final String DEFAULT = "NULL";

    public static final class DBFarmersContract {
        public static final String DB_NAME = "farmersrecordDB.db";
       // public static final int VERSION = 18;
       public static final int VERSION = 19;
        private byte[] img=null;

        ///table for users
        public static final String TABLE_NAME_FARMERS = "farmers";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_FARMERID = "farmerID";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_MIDDLENAME = "middlename";
        public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
        public static final String COLUMN_PHONE_NUMBER2 = "phoneNumber_2";
        public static final String COLUMN_DOB = "dob";
        public static final String COLUMN_EDUCATIONLEVEL = "educationlevel";
        public static final String COLUMN_GENDER = "gender";


        public static final String COLUMN_VILLAGE = "village";
        public static final String COLUMN_FARMERS_ADDRESS = "farmersaddress";
        public static final String COLUMN_MARITAL_STATUS = "maritalstatus";
        public static final String COLUMN_PREFERREDLANGUAGE = "preferredlanguage";
        public static final String COLUMN_COUNTY = "county";
        public static final String COLUMN_DISTRICT = "district";
        public static final String COLUMN_AREA = "area";
        public static final String COLUMN_VCR = "vcr";

        public static final String COLUMN_PRIMARY_CROP = "primary_crop";
        public static final String COLUMN_FARMERSCROP = "farmerscrop";
        public static final String COLUMN_TREE_CROPS = "tree_crops";
        public static final String COLUMN_ROOTS = "roots";
        public static final String COLUMN_FRUITS = "fruits";
        public static final String COLUMN_LEGUMES = "legumes";
        public static final String COLUMN_VEGETABLES = "vegetables";

        public static final String COLUMN_FARMSIZE = "farmsize";
        public static final String COLUMN_FARMLOCATION = "farmlocation";
        public static final String COLUMN_FARMERSLIVESTOCK = "farmerslivestock";
        public static final String COLUMN_FARMERSFISHERIES = "farmersfisheries";

        public static final String COLUMN_MEMBER_QUESTION = "member_question";
        public static final String COLUMN_GROUPNAME = "groupname";
        public static final String COLUMN_GROUPLEADERNAME = "groupleadername";
        public static final String COLUMN_GROUPLEADERPHONE = "groupleaderphone";

        public static final String COLUMN_UPDATE_STATUS = "updateStatus";
        public static final String COLUMN_DATE_CREATED = "dateCreated";
        public static final String COLUMN_ADMINFULL_NAME = "adminfullName";
        public static final String COLUMN_ADMINPHONE_NUMBER = "adminphoneNumber";

        public static final String COLUMN_ADMINCOUNTY = "admincounty";
        public static final String COLUMN_ADMINDISTRICT = "admindistrict";

        public static final String COLUMN_PICTURE = "picture";



        //// table for admin
        public static final String TABLE_NAME_ADMIN = "admin";
        public static final String COLUMN_ADMIN_ID = "_id";
        public static final String COLUMN_ADMIN_FULL_NAME = "fullName";
        public static final String COLUMN_ADMIN_PHONE_NUMBER = "phoneNumber";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_ADMINSTATE = "state";
        public static final String COLUMN_ADMINLGA = "lga";
        public static final String COLUMN_IMEI = "imei";


        //// table for county
        public static final String TABLE_NAME_COUNTY = "county";
        public static final String COLUMN_COUNTYID = "county_id";
        public static final String COLUMN_COUNTYNAME = "county_name";

        //// table for district
        public static final String TABLE_NAME_TOWN = "town";
        public static final String COLUMN_TOWNID = "town_id";
        public static final String COLUMN_TOWNNAME = "town_name";
        public static final String COLUMN_ELECTORALDISTRICT = "electroral_district";


        //// table for town
        public static final String TABLE_NAME_DISTRICT = "district";
        public static final String COLUMN_DISTRICTID = "district_id";
        public static final String COLUMN_DISTRICTNAME = "district_name";

        //// table for vcr
        public static final String TABLE_NAME_VCR = "vcr";
        public static final String COLUMN_VCR_CODE = "vcr_code";
        public static final String COLUMN_VCR_NAME = "vcr_name";




        //// table for state
        public static final String TABLE_NAME_STATE = "states";
        public static final String COLUMN_STATEID = "stateid";
        public static final String COLUMN_STATENAME = "statename";
        public static final String COLUMN_STATECODE = "statecode";
        //// table for LGA
        public static final String TABLE_NAME_LGA = "lga";
        public static final String COLUMN_LGAID = "lgaid";
        public static final String COLUMN_LGANAME = "lganame";
        public static final String COLUMN_LGACODE = "lgacode";
        public static final String COLUMN_STATEIDL = "statecode";

        //// table for ward
        public static final String TABLE_NAME_WARD = "wards";
        public static final String COLUMN_WARDID = "wardid";
        public static final String COLUMN_WARDNAME = "wardname";
        public static final String COLUMN_WARDCODE = "wardcode";
        public static final String COLUMN_LGAIDW = "lgaid";


        //// create admin table
        public static final String CREATE_TABLE_ADMIN = "CREATE TABLE " + TABLE_NAME_ADMIN + " ("
                + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY , "
                + COLUMN_ADMIN_FULL_NAME + " TEXT, "
                + COLUMN_ADMIN_PHONE_NUMBER + " VARCHAR , "
                + COLUMN_ADMINSTATE + " VARCHAR , "
                + COLUMN_ADMINLGA + " VARCHAR , "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_IMEI + " TEXT)";

        /// create users table
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_FARMERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY  , "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_FARMERID + " TEXT, "
                + COLUMN_SURNAME + " TEXT, "
                + COLUMN_FIRSTNAME + " TEXT, "
                + COLUMN_MIDDLENAME + " TEXT, "
                + COLUMN_GENDER + " TEXT, "
                + COLUMN_DOB + " TEXT, "
                + COLUMN_EDUCATIONLEVEL + " TEXT, "
                + COLUMN_PHONE_NUMBER + " VARCHAR , "
                + COLUMN_PHONE_NUMBER2 + " VARCHAR , "

                + COLUMN_VILLAGE + " TEXT, "
                + COLUMN_FARMERS_ADDRESS + " TEXT, "
                + COLUMN_MARITAL_STATUS + " TEXT, "
                + COLUMN_PREFERREDLANGUAGE + " TEXT, "
                + COLUMN_COUNTY + " TEXT, "
                + COLUMN_DISTRICT + " TEXT, "
                + COLUMN_AREA + " TEXT, "
                + COLUMN_VCR + " TEXT, "

                + COLUMN_PRIMARY_CROP + " TEXT, "
                + COLUMN_FARMERSCROP + " TEXT, "
                + COLUMN_TREE_CROPS + " TEXT, "
                + COLUMN_ROOTS + " TEXT, "
                + COLUMN_FRUITS + " TEXT, "
                + COLUMN_LEGUMES + " TEXT, "
                + COLUMN_VEGETABLES + " TEXT, "

                + COLUMN_FARMSIZE + " TEXT, "
                + COLUMN_FARMLOCATION + " TEXT, "
                + COLUMN_FARMERSLIVESTOCK + " TEXT, "
                + COLUMN_FARMERSFISHERIES + " TEXT, "

                + COLUMN_MEMBER_QUESTION + " TEXT , "
                + COLUMN_GROUPNAME + " TEXT, "
                + COLUMN_GROUPLEADERNAME + " TEXT, "
                + COLUMN_GROUPLEADERPHONE + " TEXT, "
                + COLUMN_PICTURE + " BLOB, "
                + COLUMN_ADMINFULL_NAME + " TEXT, "
                + COLUMN_ADMINPHONE_NUMBER + " TEXT, "
                + COLUMN_ADMINCOUNTY + " TEXT, "
                + COLUMN_ADMINDISTRICT + " TEXT, "
                + COLUMN_IMEI + " TEXT, "

                + COLUMN_DATE_CREATED + " TEXT, "
                + COLUMN_UPDATE_STATUS + " TEXT) ";

        //// create state county
        public static final String CREATE_TABLE_COUNTY = "CREATE TABLE " + TABLE_NAME_COUNTY + " ("
                + COLUMN_COUNTYID + " VARCHAR(9) NOT NULL PRIMARY KEY , "
                + COLUMN_COUNTYNAME  + " VARCHAR(16)) ";


        //// create state district
        public static final String CREATE_TABLE_DISTRICT = "CREATE TABLE " + TABLE_NAME_DISTRICT + " ("
                + COLUMN_DISTRICTID + " VARCHAR(11) NOT NULL PRIMARY KEY , "
                + COLUMN_DISTRICTNAME + " VARCHAR(24), "
                + COLUMN_COUNTYID + " VARCHAR(9)) ";

        //// create state town
        public static final String CREATE_TABLE_TOWN = "CREATE TABLE " + TABLE_NAME_TOWN + " ("
                + COLUMN_TOWNID + " VARCHAR(7) NOT NULL PRIMARY KEY , "
                + COLUMN_TOWNNAME + " VARCHAR(31), "
                + COLUMN_DISTRICTID + " VARCHAR(11), "
                + COLUMN_ELECTORALDISTRICT + " VARCHAR(19), "
                + COLUMN_COUNTYID + " VARCHAR(9)) ";

        //// create state VCR
        public static final String CREATE_TABLE_VCR = "CREATE TABLE " + TABLE_NAME_VCR + " ("
                + COLUMN_VCR_CODE + " INTEGER(5) , "
                + COLUMN_VCR_NAME + " VARCHAR(56), "
                + COLUMN_TOWNID + " INTEGER(3), "
                + COLUMN_DISTRICTID + " INTEGER(3), "
                + COLUMN_ELECTORALDISTRICT + " VARCHAR(4), "
                + COLUMN_COUNTYID + " INTEGER(2)) ";

        //// create state table
       public static final String CREATE_TABLE_STATE = "CREATE TABLE " + TABLE_NAME_STATE + " ("
                + COLUMN_STATEID + " VARCHAR(7) NOT NULL PRIMARY KEY , "
                + COLUMN_STATENAME + " VARCHAR(18), "
                + COLUMN_STATECODE + " VARCHAR(9)) ";

          //// create lga table
        public static final String CREATE_TABLE_LGA = "CREATE TABLE " + TABLE_NAME_LGA + " ("
                + COLUMN_LGAID + " VARCHAR(6) NOT NULL PRIMARY KEY , "
                + COLUMN_LGANAME + " VARCHAR(45), "
                + COLUMN_LGACODE + " VARCHAR(8) , "
                + COLUMN_STATEIDL + " VARCHAR(8)) ";

         //// create ward table
        public static final String CREATE_TABLE_WARD = "CREATE TABLE " + TABLE_NAME_WARD + " ("
                + COLUMN_WARDID + " VARCHAR(6) NOT NULL PRIMARY KEY , "
                + COLUMN_WARDNAME + " VARCHAR(45), "
                + COLUMN_WARDCODE + " VARCHAR(8) , "
                + COLUMN_LGAIDW + " VARCHAR(8)) ";
}

    public DBFarmers(Context applicationcontext) {
        super(applicationcontext, DBFarmersContract.DB_NAME, null, DBFarmersContract.VERSION);
        this.context=applicationcontext;
        Log.d("LOGCAT","Created");

    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String queryUser, queryState,queryCounty,queryTown,queryDistrict,queryVcr, queryLga, queryWard, queryAdmin;


        queryUser = DBFarmersContract.CREATE_TABLE;
        queryCounty = DBFarmersContract.CREATE_TABLE_COUNTY;
        queryTown = DBFarmersContract.CREATE_TABLE_TOWN;
        queryDistrict = DBFarmersContract.CREATE_TABLE_DISTRICT;
        queryVcr = DBFarmersContract.CREATE_TABLE_VCR;
        queryAdmin = DBFarmersContract.CREATE_TABLE_ADMIN;

        database.execSQL(queryUser);
        database.execSQL(queryCounty);
        database.execSQL(queryDistrict);
        database.execSQL(queryTown);
        database.execSQL(queryVcr);
        database.execSQL(queryAdmin);

           executeSQLScript(database, "county.sql");
           executeSQLScript(database, "district.sql");
           executeSQLScript(database, "town.sql");
           executeSQLScript(database, "vcr.sql");

    }

    private void executeSQLScript(SQLiteDatabase database, String dbname) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1048576];
        // ByteBuffer buf[] = new ByteBuffer [4096];
        int len;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open(dbname);
            while ((len = inputStream.read(buf)) != -1) {
                System.out.println("No of lines read " +len);
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] createScript = outputStream.toString().split(";");


            for (int i = 0; i < createScript.length; i++) {
                String sqlStatement = createScript[i].trim();
                // TODO You may want to parse out comments here
                if (sqlStatement.length() > 0) {

                    System.out.println("No of Script read " + sqlStatement );
                    database.execSQL(sqlStatement + ";");
                }
            }
        } catch (IOException e){
            // TODO Handle Script Failed to Load
        } catch (SQLiteException e) {
            // TODO Handle Script Failed to Execute
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query, queryS, queryL, queryW ,queryC, queryA, queryD, queryT,queryV;


        query = "DROP TABLE IF EXISTS farmers";
        queryC = "DROP TABLE IF EXISTS county";
        queryD = "DROP TABLE IF EXISTS district";
        queryT = "DROP TABLE IF EXISTS town";
        queryV = "DROP TABLE IF EXISTS vcr";
        queryS = "DROP TABLE IF EXISTS states";
        queryL = "DROP TABLE IF EXISTS lga";
       queryW = "DROP TABLE IF EXISTS wards";
        queryA = "DROP TABLE IF EXISTS admin";

       /* if (version_old < 24) {
          //  ExportDB edb = new ExportDB();
           // edb.exportDB();
            database.execSQL(queryV);
            database.execSQL(queryVcr);
            executeSQLScript(database, "vcr.sql");
        }*/
        database.execSQL(query);
       database.execSQL(queryS);
       database.execSQL(queryC);
       database.execSQL(queryD);
       database.execSQL(queryT);
       database.execSQL(queryV);
        database.execSQL(queryL);
       database.execSQL(queryW);
       database.execSQL(queryA);

       onCreate(database);
    }



    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    ///unique key



    public void insertUser(HashMap<String, String> queryValues, Account account,String authority, Bundle extras) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        /// Get current time and date
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);

        int id = (int)(Math.random()* 9000000)+1000000;

        values.put("_id", id);
        values.put("title", queryValues.get("title"));
        values.put("farmerID", queryValues.get("farmerID"));
         values.put("surname", queryValues.get("surname"));
       values.put("middlename", queryValues.get("middlename"));
        values.put("phoneNumber", queryValues.get("phoneNumber"));
        values.put("phoneNumber_2", queryValues.get("phoneNumber2"));
        values.put("firstname", queryValues.get("firstname"));
        values.put("gender", queryValues.get("gender"));
        values.put("dob", queryValues.get("dob"));
        values.put("educationlevel", queryValues.get("educationlevel"));

        values.put("village", queryValues.get("farmervillage"));
        values.put("farmersaddress", queryValues.get("farmeraddress"));
        values.put("maritalstatus", queryValues.get("maritalstatus"));

        values.put("preferredlanguage", queryValues.get("preferredlanguage"));
        values.put("county", queryValues.get("county"));
        values.put("district", queryValues.get("district"));
        values.put("area", queryValues.get("area"));
        values.put("vcr", queryValues.get("vcr"));

        values.put("primary_crop", queryValues.get("primary_crop"));
        values.put("farmerscrop", queryValues.get("farmerscrop"));

        values.put("tree_crops", queryValues.get("tree_crops"));
        values.put("roots", queryValues.get("roots"));
        values.put("fruits", queryValues.get("fruits"));
        values.put("legumes", queryValues.get("legumes"));
        values.put("vegetables", queryValues.get("vegetables"));

        values.put("farmsize", queryValues.get("farmsize"));
        values.put("farmlocation", queryValues.get("farmlocation"));
        values.put("farmerslivestock", queryValues.get("farmerslivestock"));
        values.put("farmersfisheries", queryValues.get("farmersfisheries"));

        values.put("member_question", queryValues.get("member_question"));
        values.put("groupname", queryValues.get("farmersgroup"));
        values.put("groupleadername", queryValues.get("groupleadername"));
        values.put("groupleaderphone", queryValues.get("groupleaderphone"));

        values.put("adminfullName", queryValues.get("adminfullname"));
        values.put("adminphoneNumber", queryValues.get("adminphone"));
        values.put("admincounty", queryValues.get("admincounty"));
        values.put("admindistrict", queryValues.get("admindistrict"));
        values.put("imei", queryValues.get("imei"));

        values.put("picture", queryValues.get("image"));

        values.put("updateStatus", "no");
        values.put("dateCreated", dateTime);

        database.insert("farmers", null, values);
        Log.d("INSERTED", "CreatedRecord");
        database.close();
    }

    /// insert into Admin Table
    public void insertEntry( String fullName, String mobileNumber , String userName,String password , String state, String lga, String imei)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("username", userName);
        newValues.put("password",password);
        newValues.put("fullName",fullName);
        newValues.put("phoneNumber",mobileNumber);
        newValues.put("state", state);
        newValues.put("lga",lga);
        newValues.put("imei", imei);

        // Insert the row into your table
        database.insert("admin", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public String[] getDetails(String nfcid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("farmers",null, "surname =?",
                new String[] {nfcid} , null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String[] arr =  new String[2];
        arr[0] = cursor.getString(1);
        arr [1] = cursor.getString(25);

        // return contact
        return arr;


    }
    // check login
    public String getSinlgeEntry(String userName){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query("admin", null, " username=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }

 ///check if username already exist in the database

public String getUser(String userName){
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor=database.query("admin", null, " username=?", new String[]{userName}, null, null, null);
    if(cursor.getCount()<1) // UserName Not Exist
    {
        cursor.close();
        return "NOT EXIST";
    }
    cursor.moveToFirst();
    String username= cursor.getString(cursor.getColumnIndex("username"));
    cursor.close();
    return username;
}
    /////
    public String checkRecord(String tagno){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query("farmers", null, " farmerID=?", new String[]{tagno}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String username= cursor.getString(cursor.getColumnIndex("farmerID"));
        cursor.close();
        return username;
    }

////

    public String getImage(String tagnumber){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query("farmers", null, " tagnumber=?",
                new String[] {tagnumber}, null, null, null);
        if(cursor.getCount()<1) // Barcode Not Exist
        {
            cursor.close();
            return "No Barcode associated with this Tag Number";
        }
        cursor.moveToFirst();
        String picture= cursor.getString(cursor.getColumnIndex("barcode"));
        cursor.close();
        return picture;
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

       // ArrayList<HashMap><String, String>
        String selectQuery = "SELECT  * FROM "+ DBFarmersContract.TABLE_NAME_FARMERS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("userId", cursor.getString(0));
                map.put("title", cursor.getString(1));
                map.put("farmerID", cursor.getString(2));
                map.put("surname", cursor.getString(3));
                map.put("firstname", cursor.getString(4));
                map.put("updateStatus", cursor.getString(41));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public synchronized String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM farmers where updateStatus = '"+"no"+"' LIMIT 10";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("title", cursor.getString(1));
                map.put("farmerID", cursor.getString(2));
                map.put("surname", cursor.getString(3));
                map.put("firstname", cursor.getString(4));
                map.put("middlename", cursor.getString(5));
                map.put("gender", cursor.getString(6));
                map.put("dob", cursor.getString(7));
                map.put("educationlevel", cursor.getString(8));
                map.put("phoneNumber", cursor.getString(9));
                map.put("phoneNumber2", cursor.getString(10));

                map.put("village", cursor.getString(11));
                map.put("farmersaddress", cursor.getString(12));
                map.put("maritalstatus", cursor.getString(13));
                map.put("preferredlanguage", cursor.getString(14));
                map.put("county", cursor.getString(15));
                map.put("district", cursor.getString(16));
                map.put("area", cursor.getString(17));
                map.put("vcr", cursor.getString(18));

                map.put("primary_crop", cursor.getString(19));
                map.put("farmerscrop", cursor.getString(20));
                map.put("tree_crops", cursor.getString(21));
                map.put("roots", cursor.getString(22));
                map.put("fruits", cursor.getString(23));
                map.put("legumes", cursor.getString(24));
                map.put("vegetables", cursor.getString(25));

                map.put("farmsize", cursor.getString(26));
                map.put("farmlocation", cursor.getString(27));
                map.put("farmerslivestock", cursor.getString(28));
                map.put("farmersfisheries", cursor.getString(29));

                map.put("member_question", cursor.getString(30));
                map.put("groupname", cursor.getString(31));
                map.put("groupleadername", cursor.getString(32));
                map.put("groupleaderphone", cursor.getString(33));
                map.put("picture", cursor.getString(34));
                map.put("adminfullName", cursor.getString(35));
                map.put("adminphoneNumber", cursor.getString(36));
                map.put("admincounty", cursor.getString(37));
                map.put("admindistrict", cursor.getString(38));
                map.put("imei", cursor.getString(39));
                map.put("dateCreated", cursor.getString(40));
                map.put("updateStatus", cursor.getString(41));

                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    public String delete()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        String msg = null;
        if(this.dbDeleteCount() == 0){

            msg = "No record to delete";

        } else {
            //  String updateQuery = "DELETE FROM farmers";
            String updateQuery = "DELETE FROM farmers where updateStatus = '"+"yes"+"' ";
            database.execSQL(updateQuery);
            msg = "Completed";
            database.close();
        }
        return msg;
    }
    /**
     * Get SQLite records that needed to be deleted
     * @return
     */
    public int dbDeleteCount(){
        int count = 0;
       // String selectQuery = "SELECT * FROM farmers  where updateStatus = "+"yes" +" AND scanedBarcode = "+"yes"+" ";
        String selectQuery = "SELECT * FROM farmers  where updateStatus = '"+"yes"+"' ";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        //database.close();
        return count;
    }
    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM farmers where updateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }


    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    public void updateSyncStatus(String id ,String status){
        SQLiteDatabase database = this.getWritableDatabase();
      String updateQuery = "Update farmers set updateStatus = '"+ status +"' where "+ DBFarmersContract.COLUMN_ID+"="+"'"+ id +"'";

        Log.d("query", updateQuery);
        database.execSQL(updateQuery);

        database.close();
    }

    public List<String> getAllCounty(){
        List<String> countyname = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM county" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                countyname.add(cursor.getString(1));
                // statename.add(cursor.getString(o1));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning names
        return countyname;
    }
    public List<String> getDistrict(String label){
        List<String> districtname = new ArrayList<String>();
        // Select All Query
        String[] columns = {"county_id"};
        SQLiteDatabase db = this.getReadableDatabase();
        /// Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query("county", columns, "county_name" + " = '" + label + "'", null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String county_id = cursor.getString(0);
                System.out.println("COUNTY_ID: " + county_id);

                String[] columnslga = {"district_name"};
                Cursor cursors = db.query("district", columnslga, "county_id" + " = '" + county_id + "'", null, null, null, null);
                // System.out.println( "LGA NAME: " + stateid);

                if(cursors.moveToFirst()){
                    do {

                        districtname.add(cursors.getString(0));
                        String lganames = cursors.getString(0);
                        System.out.println( "LGA NAME: " + lganames);
                    }while (cursors.moveToNext());

                }
            }
            while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning names
        return districtname;
    }
    public List<String> getArea(String wardlabel){
        List<String> areaname = new ArrayList<String>();
        // Select All Query
        String[] columns = {"district_id"};
        SQLiteDatabase db = this.getReadableDatabase();
        /// Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query("district", columns, "district_name" + " = '" + wardlabel + "'", null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String districtid = cursor.getString(0);
                System.out.println("District ID: " + districtid);

                String[] columnward = {"town_name"};
                Cursor cursors = db.query("town", columnward, "district_id" + " = '" + districtid + "'", null, null, null, null);
                // System.out.println( "LGA NAME: " + stateid);

                if(cursors.moveToFirst()){
                    do {

                        areaname.add(cursors.getString(0));
                        String areanames = cursors.getString(0);
                        System.out.println( "TOWN_NAME: " + areanames);
                    }while (cursors.moveToNext());

                }
            }
            while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning names
        return areaname;
    }

    public List<String> getVcr(String wardlabel){
        List<String> vcrname = new ArrayList<String>();
        // Select All Query
        String[] columns = {"town_id"};
        SQLiteDatabase db = this.getReadableDatabase();
        /// Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query("town", columns, "town_name" + " = '" + wardlabel + "'", null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String townid = cursor.getString(0);
               // System.out.println("TOWN ID: " + townid);

                String[] columnward = {"vcr_name"};
                Cursor cursors = db.query("vcr", columnward, "town_id" + " = '" + townid + "'", null, null, null, null);


                if(cursors.moveToFirst()){
                    do {

                        vcrname.add(cursors.getString(0));
                      //  String areanames = cursors.getString(0);
                      //  System.out.println( "TOWN_NAME: " + areanames);
                    }while (cursors.moveToNext());

                }
            }
            while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning names
        return vcrname;
    }

    public List<String> getWard(String wardlabel){
        List<String> wardname = new ArrayList<String>();
        // Select All Query
        String[] columns = {"lgaid"};
        SQLiteDatabase db = this.getReadableDatabase();
        /// Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query("lga", columns, "lganame" + " = '" + wardlabel + "'", null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String lgaid = cursor.getString(0);
                System.out.println("LGA ID: " + lgaid);

                String[] columnward = {"wardname"};
                Cursor cursors = db.query("wards", columnward, "lgaid" + " = '" + lgaid + "'", null, null, null, null);
                // System.out.println( "LGA NAME: " + stateid);

                if(cursors.moveToFirst()){
                    do {

                        wardname.add(cursors.getString(0));
                        String wardnames = cursors.getString(0);
                        System.out.println( "WARD NAME: " + wardnames);
                    }while (cursors.moveToNext());

                }
            }
            while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning names
        return wardname;
    }

}