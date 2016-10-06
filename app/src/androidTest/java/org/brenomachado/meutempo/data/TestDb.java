package org.brenomachado.meutempo.data;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /*
        Students: Uncomment this test once you've written the code to create the Location
        table.  Note that you will have to have chosen the same column names that I did in
        my solution for this test to compile, so if you haven't yet done that, this is
        a good time to change your column names to match mine.

        Note that this only tests that the Location table has the correct columns, since we
        give you the code for the weather table.  This test does not look at the
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(WeatherContract.LocationEntry.TABLE_NAME);
        tableNameHashSet.add(WeatherContract.WeatherEntry.TABLE_NAME);

        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + WeatherContract.LocationEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(WeatherContract.LocationEntry._ID);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_CITY_NAME);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LAT);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_COORD_LONG);
        locationColumnHashSet.add(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                locationColumnHashSet.isEmpty());
        db.close();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        location database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can uncomment out the "createNorthPoleLocationValues" function.  You can
        also make use of the ValidateCurrentRecord function from within TestUtilities.
    */
    public void testLocationTable() {
        SQLiteDatabase db = new WeatherDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        ContentValues cv = TestUtilities.createNorthPoleLocationValues();

        long id = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, cv);

        assertEquals("Não inserido", false, id == -1);

        Cursor c = db.query(WeatherContract.LocationEntry.TABLE_NAME,
                null,
                WeatherContract.LocationEntry._ID + " == ? ",
                new String[] { Long.toString(id) },
                null,
                null,
                null);

        if (c.moveToFirst()) {
            assertEquals("Campo " + WeatherContract.LocationEntry._ID + " falho", true,
                    c.getDouble(
                            c.getColumnIndex(WeatherContract.LocationEntry._ID)
                    ) == id);
            assertEquals("Campo " + WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + " falho", true,
                    c.getString(
                            c.getColumnIndex(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING)
                    ).equals(TestUtilities.TEST_LOCATION));
            assertEquals("Campo " + WeatherContract.LocationEntry.COLUMN_CITY_NAME + " falho", true,
                    c.getString(
                            c.getColumnIndex(WeatherContract.LocationEntry.COLUMN_CITY_NAME)
                    ).equals("North Pole"));
            assertEquals("Campo " + WeatherContract.LocationEntry.COLUMN_COORD_LAT + " falho", true,
                    c.getDouble(
                            c.getColumnIndex(WeatherContract.LocationEntry.COLUMN_COORD_LAT)
                    ) == 64.7488);
            assertEquals("Campo " + WeatherContract.LocationEntry.COLUMN_COORD_LONG + " falho", true,
                    c.getDouble(
                            c.getColumnIndex(WeatherContract.LocationEntry.COLUMN_COORD_LONG)
                    ) == -147.353);
        }

        c.close();
        db.close();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the validateCurrentRecord function from within TestUtilities.
     */
    public void testWeatherTable() {
        SQLiteDatabase db = new WeatherDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        ContentValues cv = TestUtilities.createNorthPoleLocationValues();

        long id = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, cv);

        assertEquals("Não inserido", false, id == -1);

        ContentValues cvw = TestUtilities.createWeatherValues(id);

        long idw = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, cvw);

        assertEquals("Não inserido", false, idw == -1);

        Cursor c = db.query(WeatherContract.WeatherEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        assertTrue(c.moveToFirst());

        TestUtilities.validateCurrentRecord("Validação do insert da weatherEntry falha",
                c, cvw);

        c.close();
        db.close();
    }


    /*
        Students: This is a helper method for the testWeatherTable quiz. You can move your
        code from testLocationTable to here so that you can call this code from both
        testWeatherTable and testLocationTable.
     */
    public long insertLocation() {
        return -1L;
    }
}
