/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stpaulsmarthom.com.stpaulsmarthom.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * {@link ContentProvider} for Pets app.
 */
public class DbProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = DbProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the pets table */



    private static final int ABOUT_US = 100;



    private static final int ABOUT_US_ID = 101;

    private static final int OUR_BISHOPS = 200;



    private static final int OUR_BISHOPS_ID = 201;



    private static final int OUR_VICAR = 300;



    private static final int OUR_VICAR_ID = 301;
    private static final int KAISTHANA = 400;



    private static final int KAISTHANA_ID = 401;


    private static final int ORG = 500;



    private static final int ORG_ID = 501;

    private static final int PARISH = 600;



    private static final int PARISH_ID = 601;

    private static final int PUBLICATIONS = 700;



    private static final int PUBLICATIONS_ID = 701;

    private static final int CHILD = 800;



    private static final int CHILD_ID = 801;


    /** URI matcher code for the content URI for a single pet in the pets table */


    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #USERS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_CHILD, CHILD);


        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_ABOUT_US, ABOUT_US);
        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_OUR_BISHOPS, OUR_BISHOPS);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_OUR_VICAR, OUR_VICAR);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_KAISTHANA, KAISTHANA);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_ORGANISATIONS, ORG);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_PARISH_MEMBERS, PARISH);


        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_PUBLICATIONS, PUBLICATIONS);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #USER_ID}. This URI is used to provide access to ONE single row
        // of the pets table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_CHILD + "/#", CHILD_ID);


        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_ABOUT_US + "/#", ABOUT_US_ID);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_OUR_BISHOPS + "/#", OUR_BISHOPS_ID);


        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_OUR_VICAR + "/#", OUR_VICAR_ID);


        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_KAISTHANA + "/#", KAISTHANA_ID);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_ORGANISATIONS + "/#", ORG_ID);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_PARISH_MEMBERS + "/#", PARISH_ID);

        sUriMatcher.addURI(DbContract.CONTENT_AUTHORITY, DbContract.PATH_PUBLICATIONS + "/#", PUBLICATIONS_ID);


    }

    /** Database helper object */
    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ABOUT_US:
                cursor = database.query(DbContract.DbEntry.TABLE_ABOUT_US, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case ABOUT_US_ID:
                // For the USER_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DbContract.DbEntry.TABLE_ABOUT_US, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case OUR_BISHOPS:
                cursor = database.query(DbContract.DbEntry.TABLE_OUR_BISHOPS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case OUR_BISHOPS_ID:
                // For the USER_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DbContract.DbEntry.TABLE_OUR_BISHOPS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case OUR_VICAR:
                cursor = database.query(DbContract.DbEntry.TABLE_OUR_VICAR, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case OUR_VICAR_ID:

                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DbEntry.TABLE_OUR_VICAR, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case KAISTHANA:
                cursor = database.query(DbContract.DbEntry.TABLE_KAISTHANA, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case KAISTHANA_ID:

                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DbEntry.TABLE_KAISTHANA, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ORG:
                cursor = database.query(DbContract.DbEntry.TABLE_ORGANISATIONS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case ORG_ID:

                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DbEntry.TABLE_ORGANISATIONS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PARISH:
                cursor = database.query(DbContract.DbEntry.TABLE_PARISH, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case PARISH_ID:

                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DbEntry.TABLE_PARISH, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case PUBLICATIONS:
                cursor = database.query(DbContract.DbEntry.TABLE_PUBLICATIONS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case PUBLICATIONS_ID:

                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DbEntry.TABLE_PUBLICATIONS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case CHILD:
                cursor = database.query(DbContract.DbEntry.TABLE_CHILD, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;




            case CHILD_ID:

                selection = DbContract.DbEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(DbContract.DbEntry.TABLE_CHILD, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
           // case PERSON:
         //       return updateUser(uri, contentValues, selection, selectionArgs);
         //   case PERSON_ID:
                // For the USER_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
             //   selection = DbContract.DbEntry._ID + "=?";
            //    selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
               // return updateUser(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /*
    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    /*
    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link DbEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.

            // Check that the name is not null

            if (values.containsKey(DbEntry.COLUMN_USER_NAME)) {
                String name = values.getAsString(DbEntry.COLUMN_USER_NAME);
                if (name == null) {
                    Toast.makeText(getContext(),"Please Enter a Name",Toast.LENGTH_SHORT);
                }
            }

            // If the {@link DbEntry#COLUMN_PET_GENDER} key is present,
            // check that the gender value is valid.
            if (values.containsKey(DbEntry.COLUMN_USER_EMAIL)) {
                String email = values.getAsString(DbEntry.COLUMN_USER_EMAIL);
                if(email == null)
                {
                    Toast.makeText(getContext(),"Please Enter a Email-id",Toast.LENGTH_SHORT);

                }
            }

            // If the {@link DbEntry#COLUMN_PET_WEIGHT} key is present,
            // check that the weight value is valid.
            if (values.containsKey(DbEntry.COLUMN_USER_MOBILE)) {
                // Check that the weight is greater than or equal to 0 kg
                Integer mobile = values.getAsInteger(DbEntry.COLUMN_USER_MOBILE);
                if(mobile.toString() == null)
                {
                    Toast.makeText(getContext(),"Please Enter a Mobile Number",Toast.LENGTH_SHORT);

                }
            }


            if (values.containsKey(DbEntry.COLUMN_USER_PASSWORD)) {
                String password = values.getAsString(DbEntry.COLUMN_USER_PASSWORD);
                if (password == null) {
                    Toast.makeText(getContext(),"Please enter a password",Toast.LENGTH_SHORT);
                }
            }

            if (values.containsKey(DbEntry.COLUMN_USER_REPASSWORD)) {
                String repassword = values.getAsString(DbEntry.COLUMN_USER_REPASSWORD);
                if (repassword == null) {
                    Toast.makeText(getContext(),"Please re-enter a password",Toast.LENGTH_SHORT);
                }
            }
            String password = values.getAsString(DbEntry.COLUMN_USER_PASSWORD);
            String repassword = values.getAsString(DbEntry.COLUMN_USER_REPASSWORD);
            if(!password.equals(repassword))
            {
                Toast.makeText(getContext(),"Passwords do not match",Toast.LENGTH_SHORT);
            }


        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(DbContract.DbEntry.TABLE_USERS, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }*/





    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {

            case ABOUT_US:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_ABOUT_US;
            case ABOUT_US_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_ABOUT_US;

            case OUR_BISHOPS:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_OUR_BISHOPS;
            case OUR_BISHOPS_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_OUR_BISHOPS;

            case OUR_VICAR:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_OUR_VICAR;
            case OUR_VICAR_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_OUR_VICAR;

            case KAISTHANA:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_KAISTHANA;
            case KAISTHANA_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_KAISTHANA;


            case ORG:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_ORGANISATIONS;
            case ORG_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_ORGANISATIONS;

            case PARISH:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_PARISH_MEMBER;
            case PARISH_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_PARISH_MEMBER;

            case PUBLICATIONS:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_PUBLICATIONS;
            case PUBLICATIONS_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_PUBLICATIONS;


            case CHILD:
                return DbContract.DbEntry.CONTENT_LIST_TYPE_CHILD;
            case CHILD_ID:
                return DbContract.DbEntry.CONTENT_ITEM_TYPE_CHILD;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
