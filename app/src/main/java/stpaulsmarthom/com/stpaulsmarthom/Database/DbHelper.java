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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AboutUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ContactUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgTimingModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishBulletinModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.PrivacyModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.VicarModel;

import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.*;

/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static String SQL_CREATE_ABOUT_US_TABLE;


    private static String SQL_CREATE_OUR_BISHOP_TABLE;

    private static String SQL_CREATE_OUR_VICAR_TABLE;

    private static String SQL_CREATE_KAISTHANA_TABLE;

    private static String SQL_CREATE_ORGANISATIONS_TABLE;
    private static String SQL_CREATE_ORGANISATIONS_SECTION;

    private static String SQL_CREATE_KAIS_SECTION;

    private static String SQL_CREATE_CONTACT_US_TABLE;

    private static String SQL_CREATE_PARISH_MEMBER_TABLE;


    private static String SQL_CREATE_PUBLICATIONS_TABLE;


    private static String SQL_CREATE_PRIVACY_TABLE;

    private static String SQL_CREATE_PARISH_CHILD;

    private static String SQL_CREATE_ORG_TIMING;

    Context context;

    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "StPauls.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link DbHelper}.
     *
     * @param context of the app
     */
    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        SQL_CREATE_ABOUT_US_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_ABOUT_US + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ABOUT_US_CONTENT + " TEXT,"
                + COLUMN_ABOUT_US_IMAGE + " TEXT" +")";

        SQL_CREATE_PRIVACY_TABLE=  "CREATE TABLE " + DbContract.DbEntry.TABLE_PRIVACY + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRIVACY_POLICY + " TEXT" +")";

        SQL_CREATE_ORGANISATIONS_SECTION=  "CREATE TABLE " + DbContract.DbEntry.TABLE_ORGANISATIONS_SECTIONS + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORG_SECTION + " TEXT,"
                + COLUMN_ORG_SECTION_NUMBER + " TEXT" +")";


        SQL_CREATE_KAIS_SECTION=  "CREATE TABLE " + DbContract.DbEntry.TABLE_KAIS_SECTIONS + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_KAIS_SECTION + " TEXT" +")";


        SQL_CREATE_OUR_BISHOP_TABLE = "CREATE TABLE " + DbContract.DbEntry.TABLE_OUR_BISHOPS + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BISHOP_NAME + " INTEGER,"
                + COLUMN_BISHOP_PHONE + " TEXT,"
                + COLUMN_BISHOP_ADDRESS + " TEXT,"
                + COLUMN_BISHOP_EMAIL + " TEXT,"
                + COLUMN_BISHOP_ABOUT + " TEXT,"
                + COLUMN_BISHOP_IMAGE + " TEXT,"
                + COLUMN_BISHOP_DOB + " TEXT" +")";

        SQL_CREATE_OUR_VICAR_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_OUR_VICAR + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_VICAR_IMAGE + " TEXT,"
                + COLUMN_OUR_VICAR + " TEXT,"
                + COLUMN_VICAR_TITLE + " TEXT" +")";

        SQL_CREATE_KAISTHANA_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_KAISTHANA + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_KAISTHANA_NAME + " TEXT,"
                + COLUMN_KAISTHANA_DESIGNATION + " TEXT,"
                + COLUMN_KAISTHANA_PHONE + " TEXT,"
                + COLUMN_KAISTHANA_MEMBER_TYPE + " TEXT,"
                + COLUMN_KAISTHANA_ROLLNO + " TEXT,"
                + COLUMN_KAISTHANA_IMAGE + " TEXT" +")";





        SQL_CREATE_CONTACT_US_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_CONTACT_US + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTACT_ADDRESS + " TEXT,"
                + COLUMN_CONTACT_EMAIL + " TEXT,"
                + COLUMN_CONTACT_PHONE + " TEXT,"
                + COLUMN_CONTACT_IMAGE + " TEXT" +")";



                SQL_CREATE_ORG_TIMING =  "CREATE TABLE " + DbContract.DbEntry.TABLE_ORGANISATIONS_TIMING + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TIMING_PRACTISE + " TEXT,"
                + COLUMN_TIMING_PRACTISE_TIME + " TEXT,"
                + COLUMN_TIMING_NUMBER + " TEXT,"
                + COLUMN_TIMING_MEMBER_TYPE + " TEXT" +")";

        SQL_CREATE_ORGANISATIONS_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_ORGANISATIONS + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORG_NAME + " TEXT,"
                + COLUMN_ORG_DESIGNATION + " TEXT,"
                + COLUMN_ORG_PHONE + " TEXT,"
                + COLUMN_ORG_NUMBER + " TEXT,"
                + COLUMN_ORG_MEMBER_TYPE + " TEXT" +")";


        SQL_CREATE_PARISH_MEMBER_TABLE = "CREATE TABLE " + DbContract.DbEntry.TABLE_PARISH + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MEMBER_NAME + " INTEGER,"
                + MEMBER_ROLL_NO + " TEXT,"
                + MEMBER_AREA_NAME + " TEXT,"
                + MEMBER_AGE + " TEXT,"
                + MEMBER_BLOOD + " TEXT,"
                + MEMBER_DOB + " TEXT,"
                + MEMBER_MARITAL + " TEXT,"
                + MEMBER_DOM + " TEXT,"
                + MEMBER_CPRNO + " TEXT,"
                + MEMBER_COMPANY + " TEXT,"
                + MEMBER_MAILING + " TEXT,"
                + MEMBER_TEL + " TEXT,"
                + MEMBER_FAX + " TEXT,"
                + MEMBER_HOME + " TEXT,"
                + MEMBER_FLAT_NO + " TEXT,"
                + MEMBER_BUILDING_NO + " TEXT,"
                + MEMBER_ROAD + " TEXT,"
                + MEMBER_BLOCK + " TEXT,"
                + MEMBER_AREA + " TEXT,"
                + MEMBER_EMERGENCY_NAME + " TEXT,"
                + MEMBER_TEL_EMER + " TEXT,"
                + MEMBER_HOUSE_NAME_INDIA + " TEXT,"
                + MEMBER_PO + " TEXT,"
                + MEMBER_DISTRICT + " TEXT,"
                + MEMBER_STATE + " TEXT,"
                + MEMBER_TEL_CODE_INDIA + " TEXT,"
                + MEMBER_TOWN_INDIA + " TEXT,"
                + MEMBER_PIN + " TEXT,"
                + MEMBER_EMER_INDIA_NAME + " TEXT,"
                + MEMBER_TEL_EMER_INDIA + " TEXT,"
                + MEMBER_WIFENAME + " TEXT,"
                + MEMBER_DOB_WIFE + " TEXT,"
                + MEMBER_IN_BAHRAIN_WIFE + " TEXT,"
                + MEMBER_EMP_WIFE + " TEXT,"
                + MEMBER_EMP_NAME_WIFE + " TEXT,"
                + MEMBER_NATIVE_WIFE + " TEXT,"
                + MEMBER_BLOOD_WIFE + " TEXT,"
                + MEMBER_ACTIVE + " TEXT,"
                + MEMBER_IMAGE + " TEXT,"
                + MEMBER_WIFE_IMAGE + " TEXT,"
                + MEMBER_TELEPHONE_RES1 + " TEXT,"
                + MEMBER_MOB1 + " TEXT,"
                + MEMBER_TELEPHONE_RES2 + " TEXT,"
                + MEMBER_MOB2 + " TEXT,"
                + MEMBER_TELEPHONE_RES3 + " TEXT,"
                + MEMBER_MOB3 + " TEXT,"
                + MEMBER_MOB_CODE + " TEXT,"
                + MEMBER_MAILING_ADDRESS + " TEXT" +")";

        SQL_CREATE_PARISH_CHILD = "CREATE TABLE " + DbContract.DbEntry.TABLE_CHILD + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PARISH_CHILD_NAME + " TEXT,"
                + PARISH_CHILD_BAH + " TEXT,"
                + PARISH_CHILD_SEX + " TEXT,"
                + PARISH_CHILD_DOB + " TEXT,"
                + PARISH_CHILD_BLOOD + " TEXT,"
                + PARISH_CHILD_STUDENT + " TEXT,"
                + PARISH_CHILD_EMP + " TEXT,"
                + PARISH_CHILD_IMAGE + " TEXT,"
                + PARISH_CHILD_ROLLNO + " TEXT,"
                + PARISH_CHILD_EMP_NAME + " TEXT" +")";


        SQL_CREATE_PUBLICATIONS_TABLE =  "CREATE TABLE " + DbContract.DbEntry.TABLE_PUBLICATIONS + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PUBLICATIONS_PDF_NAME + " TEXT,"
                + PUBLICATIONS_PDF_PATH + " TEXT,"
                + PUBLICATIONS_PDF_IMAGE + " TEXT" +")";

        db.execSQL(SQL_CREATE_PRIVACY_TABLE);

        db.execSQL(SQL_CREATE_ABOUT_US_TABLE);

        db.execSQL(SQL_CREATE_OUR_BISHOP_TABLE);

        db.execSQL(SQL_CREATE_OUR_VICAR_TABLE);

        db.execSQL(SQL_CREATE_KAISTHANA_TABLE);

        db.execSQL(SQL_CREATE_ORGANISATIONS_TABLE);

        db.execSQL(SQL_CREATE_ORG_TIMING);

        db.execSQL(SQL_CREATE_CONTACT_US_TABLE);

        db.execSQL(SQL_CREATE_PARISH_MEMBER_TABLE);

        db.execSQL(SQL_CREATE_PUBLICATIONS_TABLE);

        db.execSQL(SQL_CREATE_ORGANISATIONS_SECTION);

        db.execSQL(SQL_CREATE_KAIS_SECTION);

        db.execSQL(SQL_CREATE_PARISH_CHILD);

    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }


    public void addOrgSection(OrgSectionModel orgSectionModel)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORG_SECTION, orgSectionModel.getSection());
        values.put(COLUMN_ORG_SECTION_NUMBER, orgSectionModel.getSectionNumber());
        db.insert(DbContract.DbEntry.TABLE_ORGANISATIONS_SECTIONS,null,values);
        db.close();
    }


    public void addKaisSection(KaisthanaSectionModel kaisthanaSectionModel)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_KAIS_SECTION, kaisthanaSectionModel.getSection());
        db.insert(DbContract.DbEntry.TABLE_KAIS_SECTIONS,null,values);
        db.close();
    }


    public void addAboutUsContent(AboutUsModel aboutUsModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ABOUT_US_CONTENT, aboutUsModel.getAboutUsContent());
        values.put(COLUMN_ABOUT_US_IMAGE, aboutUsModel.getAboutUsImage());
        db.insert(DbContract.DbEntry.TABLE_ABOUT_US,null,values);
        db.close();
    }

    public void addPrivacy(PrivacyModel aboutUsModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRIVACY_POLICY, aboutUsModel.getAboutUsContent());
        db.insert(DbContract.DbEntry.TABLE_PRIVACY,null,values);
        db.close();
    }

    public void addParishChild(ChildModel childModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PARISH_CHILD_NAME, childModel.getChildname());
        values.put(PARISH_CHILD_BAH, childModel.getChildinbaharian());
        values.put(PARISH_CHILD_SEX, childModel.getSexchild());
        values.put(PARISH_CHILD_DOB, childModel.getDatebrithchild());
        values.put(PARISH_CHILD_BLOOD, childModel.getBloodchild());
        values.put(PARISH_CHILD_STUDENT, childModel.getStudentchild());
        values.put(PARISH_CHILD_EMP, childModel.getEmployechild());
        values.put(PARISH_CHILD_IMAGE, childModel.getChildsimage());
        values.put(PARISH_CHILD_ROLLNO, childModel.getRollno());

        values.put(PARISH_CHILD_EMP_NAME, childModel.getEmployername());

        db.insert(DbContract.DbEntry.TABLE_CHILD,null,values);
        db.close();
    }


    public void addOurBishops(BishopModel bishopModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BISHOP_NAME, bishopModel.getName());
        values.put(COLUMN_BISHOP_PHONE, bishopModel.getPhone());
        values.put(COLUMN_BISHOP_ADDRESS, bishopModel.getAddress());
        values.put(COLUMN_BISHOP_EMAIL, bishopModel.getEmail());
        values.put(COLUMN_BISHOP_IMAGE, bishopModel.getImage_url());
        values.put(COLUMN_BISHOP_DOB, bishopModel.getDob());

        db.insert(DbContract.DbEntry.TABLE_OUR_BISHOPS,null,values);
        db.close();
    }



    public void addOurVicar(VicarModel vicarModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_OUR_VICAR, vicarModel.getOur_vicar());
        values.put(COLUMN_VICAR_IMAGE, vicarModel.getImage_url());
        values.put(COLUMN_VICAR_TITLE, vicarModel.getVicar_title());

        db.insert(DbContract.DbEntry.TABLE_OUR_VICAR,null,values);
        db.close();
    }

    public void addKaisthana(KaisthanaSamithiModel kaisthanaSamithiModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KAISTHANA_NAME, kaisthanaSamithiModel.getName());
        values.put(COLUMN_KAISTHANA_PHONE, kaisthanaSamithiModel.getPhone());
        values.put(COLUMN_KAISTHANA_DESIGNATION, kaisthanaSamithiModel.getDesignation());
        values.put(COLUMN_KAISTHANA_MEMBER_TYPE, kaisthanaSamithiModel.getMemberType());
        values.put(COLUMN_KAISTHANA_ROLLNO, kaisthanaSamithiModel.getRollno());
        values.put(COLUMN_KAISTHANA_IMAGE, kaisthanaSamithiModel.getImage());


        db.insert(DbContract.DbEntry.TABLE_KAISTHANA,null,values);
        db.close();
    }

    public void addOrganisations(OrgDetailModel orgDetailModel)
    {
        //Log.e("adding org","org add");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORG_NAME, orgDetailModel.getName());
        values.put(COLUMN_ORG_PHONE, orgDetailModel.getPhone());
        values.put(COLUMN_ORG_DESIGNATION, orgDetailModel.getDesignation());
        values.put(COLUMN_ORG_NUMBER, orgDetailModel.getOrgNumber());
        values.put(COLUMN_ORG_MEMBER_TYPE, orgDetailModel.getMemberType());


        db.insert(DbContract.DbEntry.TABLE_ORGANISATIONS,null,values);
        db.close();
    }

    public void addOrganisationsTiming(OrgTimingModel orgDetailModel)
    {
        //Log.e("adding org","org add");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMING_PRACTISE, orgDetailModel.getName());
        values.put(COLUMN_TIMING_PRACTISE_TIME, orgDetailModel.getPhone());
        values.put(COLUMN_TIMING_NUMBER, orgDetailModel.getTiming_number());
        values.put(COLUMN_TIMING_MEMBER_TYPE, orgDetailModel.getMember_type());


        db.insert(DbContract.DbEntry.TABLE_ORGANISATIONS_TIMING,null,values);
        db.close();
    }

    public void addContact(ContactUsModel contactUsModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_ADDRESS, contactUsModel.getContactAddress());
        values.put(COLUMN_CONTACT_PHONE, contactUsModel.getContactPhone());
        values.put(COLUMN_CONTACT_EMAIL, contactUsModel.getContactEmail());

        values.put(COLUMN_CONTACT_IMAGE , contactUsModel.getContactImage());

        db.insert(DbContract.DbEntry.TABLE_CONTACT_US,null,values);
        db.close();
    }

    public void addPublications(ParishBulletinModel parishBulletinModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PUBLICATIONS_PDF_NAME, parishBulletinModel.getName());
        values.put(PUBLICATIONS_PDF_IMAGE, parishBulletinModel.getImage_path());
        values.put(PUBLICATIONS_PDF_PATH, parishBulletinModel.getFile_path());


        db.insert(DbContract.DbEntry.TABLE_PUBLICATIONS,null,values);
        db.close();
    }

    public void updatePublications(String pdfPath,String fileName )
    {
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//           values.put(PUBLICATIONS_PDF_PATH, pdfPath);
//
//
//        db.update(DbContract.DbEntry.TABLE_PUBLICATIONS,values,"pdf_name = ?",new String[]{fileName});

        String strSQL = "UPDATE "+TABLE_PUBLICATIONS + " SET pdf_path = "+ "'"+pdfPath+"'"+" WHERE pdf_name = "+ "'"+fileName+"'";
        Log.e("update query",""+strSQL);
        db.execSQL(strSQL);
        }

    public void addParishMember(ParishMemberModel parishMemberModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

                 values.put(MEMBER_NAME, parishMemberModel.getMembername());
                values.put(MEMBER_ROLL_NO,parishMemberModel.getMembernamerollno());
                values.put(MEMBER_AREA_NAME, parishMemberModel.getMembernamearea());
                values.put(MEMBER_AGE, parishMemberModel.getMemberyears());
                values.put(MEMBER_BLOOD, parishMemberModel.getMemberblood());
                values.put(MEMBER_DOB, parishMemberModel.getMemberdate());
                values.put(MEMBER_MARITAL, parishMemberModel.getMembermarital());
                values.put(MEMBER_DOM, parishMemberModel.getMemberdatemarriage());
                values.put(MEMBER_CPRNO, parishMemberModel.getCprno());
                values.put(MEMBER_COMPANY, parishMemberModel.getCompanyname());
                values.put(MEMBER_MAILING, parishMemberModel.getMailingadress());
                values.put(MEMBER_TEL, parishMemberModel.getTelephone());
                values.put(MEMBER_FAX, parishMemberModel.getFax());
                values.put(MEMBER_HOME, parishMemberModel.getHomeparirsh());
                values.put(MEMBER_FLAT_NO, parishMemberModel.getFlatno());
                values.put(MEMBER_BUILDING_NO, parishMemberModel.getBlgdno());
                values.put(MEMBER_ROAD, parishMemberModel.getRoad());
                values.put(MEMBER_BLOCK, parishMemberModel.getBlock());
                values.put(MEMBER_AREA, parishMemberModel.getArea());
                values.put(MEMBER_EMERGENCY_NAME, parishMemberModel.getEmergencyname());
                values.put(MEMBER_TEL_EMER, parishMemberModel.getTelephoneemergency());
                values.put(MEMBER_HOUSE_NAME_INDIA, parishMemberModel.getHousenameindia());
                values.put(MEMBER_PO, parishMemberModel.getPostofficeindia());
                values.put(MEMBER_DISTRICT, parishMemberModel.getDistrictindia());
                values.put(MEMBER_STATE, parishMemberModel.getStateindia());
                values.put(MEMBER_TEL_CODE_INDIA, parishMemberModel.getTelcodeindia());
                values.put(MEMBER_TOWN_INDIA, parishMemberModel.getTownindia());
                values.put(MEMBER_PIN, parishMemberModel.getPinindia());
                values.put(MEMBER_EMER_INDIA_NAME, parishMemberModel.getEmergencyindianame());
                values.put(MEMBER_TEL_EMER_INDIA, parishMemberModel.getTelindiaemergency());
                values.put(MEMBER_WIFENAME, parishMemberModel.getWifename());
                values.put(MEMBER_DOB_WIFE, parishMemberModel.getDatebrithfamily());
                values.put(MEMBER_IN_BAHRAIN_WIFE, parishMemberModel.getInbaharahianfamily());
                values.put(MEMBER_EMP_WIFE, parishMemberModel.getEmployefamily());
                values.put(MEMBER_EMP_NAME_WIFE, parishMemberModel.getEmaploynamefamily());
                values.put(MEMBER_NATIVE_WIFE, parishMemberModel.getNativefamily());
                values.put(MEMBER_BLOOD_WIFE, parishMemberModel.getBloodfamily());
                values.put(MEMBER_ACTIVE, parishMemberModel.getActive());
                values.put(MEMBER_IMAGE, parishMemberModel.getMemberimagepath());
                values.put(MEMBER_WIFE_IMAGE, parishMemberModel.getWife_image());
                values.put(MEMBER_TELEPHONE_RES1, parishMemberModel.getTelephoneres1());
                values.put(MEMBER_MOB1, parishMemberModel.getTelephonemob1());
                values.put(MEMBER_TELEPHONE_RES2, parishMemberModel.getTelephoneres2());
                values.put(MEMBER_MOB2, parishMemberModel.getTelephonemob2());
                values.put(MEMBER_TELEPHONE_RES3, parishMemberModel.getTelephoneres3());
                values.put(MEMBER_MOB3, parishMemberModel.getTelephonemob3());
                values.put(MEMBER_MOB_CODE, parishMemberModel.getMobcodeindia());
                values.put(MEMBER_MAILING_ADDRESS ,parishMemberModel.getMailingadresss());
        db.insert(DbContract.DbEntry.TABLE_PARISH,null,values);
        db.close();
    }

    public void deleteTable(String tableName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ tableName);

        //Log.e("Delete from",""+tableName);
    }

    public List<OrgSectionModel> getSections(String orgNumber) {
        List<OrgSectionModel> contactList = new ArrayList<OrgSectionModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORGANISATIONS_SECTIONS +" WHERE org_section_number ="+"'"+orgNumber+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OrgSectionModel orgDetailModel =  new OrgSectionModel();
                orgDetailModel.setSection(cursor.getString(1));

                // Adding contact to list
                contactList.add(orgDetailModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<OrgTimingModel> getOrgTiming(String orgNumber) {
        List<OrgTimingModel> contactList = new ArrayList<OrgTimingModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +TABLE_ORGANISATIONS_TIMING +" WHERE timing_practise_number ="+"'"+orgNumber+"'";
        // Log.d("Reading: ", "Reading getOrgList ==   " +selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OrgTimingModel orgDetailModel =  new OrgTimingModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );

                //OrgDetailModel contact = new OrgDetailModel();
//                orgDetailModel.setName(cursor.getString(1));
//                orgDetailModel.setDesignation(cursor.getString(2));
//                orgDetailModel.setPhone(cursor.getString(3));
                // Adding contact to list
                contactList.add(orgDetailModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<OrgDetailModel> getOrgList(String memberType, String orgNumber) {
        List<OrgDetailModel> contactList = new ArrayList<OrgDetailModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +TABLE_ORGANISATIONS +" WHERE org_member_type ="+"'"+memberType+"'" + " AND  org_number=" +"'"+orgNumber+"'";
       // Log.d("Reading: ", "Reading getOrgList ==   " +selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OrgDetailModel orgDetailModel =  new OrgDetailModel(cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getString(5)

                );

                //OrgDetailModel contact = new OrgDetailModel();
//                orgDetailModel.setName(cursor.getString(1));
//                orgDetailModel.setDesignation(cursor.getString(2));
//                orgDetailModel.setPhone(cursor.getString(3));
                // Adding contact to list
                contactList.add(orgDetailModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<KaisthanaSectionModel> getKaisSections(String orgNumber) {
        List<KaisthanaSectionModel> contactList = new ArrayList<KaisthanaSectionModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_KAIS_SECTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KaisthanaSectionModel orgDetailModel =  new KaisthanaSectionModel();
                orgDetailModel.setSection(cursor.getString(1));

                // Adding contact to list
                contactList.add(orgDetailModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    public List<KaisthanaSamithiModel> getKaisList(String memberType, String orgNumber) {
        List<KaisthanaSamithiModel> contactList = new ArrayList<KaisthanaSamithiModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +TABLE_KAISTHANA +" WHERE kais_member_type ="+"'"+memberType+"'";
        // Log.d("Reading: ", "Reading getOrgList ==   " +selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                KaisthanaSamithiModel orgDetailModel =  new KaisthanaSamithiModel(cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                        );

                //OrgDetailModel contact = new OrgDetailModel();

                // Adding contact to list
                contactList.add(orgDetailModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<ChildModel> getChildList(String rollno) {
        List<ChildModel> contactList = new ArrayList<ChildModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +TABLE_CHILD +" WHERE child_roll_no ="+"'"+rollno+"'";
        // Log.d("Reading: ", "Reading getOrgList ==   " +selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChildModel orgDetailModel =  new ChildModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(10),
                        cursor.getString(9)

                );

                //OrgDetailModel contact = new OrgDetailModel();
//                orgDetailModel.setName(cursor.getString(1));
//                orgDetailModel.setDesignation(cursor.getString(2));
//                orgDetailModel.setPhone(cursor.getString(3));
                // Adding contact to list
                contactList.add(orgDetailModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public List<ParishMemberModel> getParishList() {
        List<ParishMemberModel> contactList = new ArrayList<ParishMemberModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +TABLE_PARISH;
        // Log.d("Reading: ", "Reading getOrgList ==   " +selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ParishMemberModel parishMemberModel =  new ParishMemberModel(
                       cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14),
                        cursor.getString(15),
                        cursor.getString(16),
                        cursor.getString(17),
                        cursor.getString(18),
                        cursor.getString(19),
                        cursor.getString(20),
                        cursor.getString(21),
                        cursor.getString(22),
                        cursor.getString(23),
                        cursor.getString(24),
                        cursor.getString(25),
                        cursor.getString(26),
                        cursor.getString(27),
                        cursor.getString(28),
                        cursor.getString(29),
                        cursor.getString(30),
                        cursor.getString(31),
                        cursor.getString(32),
                        cursor.getString(33),
                        cursor.getString(34),
                        cursor.getString(35),
                        cursor.getString(36),
                        cursor.getString(37),
                        cursor.getString(38),
                        cursor.getString(39),
                        cursor.getString(40),
                        cursor.getString(41),
                        cursor.getString(42),
                        cursor.getString(43),
                        cursor.getString(44),
                        cursor.getString(45),
                        cursor.getString(46),
                        cursor.getString(47),
                        cursor.getString(48)




                        );

                //OrgDetailModel contact = new OrgDetailModel();
//                orgDetailModel.setName(cursor.getString(1));
//                orgDetailModel.setDesignation(cursor.getString(2));
//                orgDetailModel.setPhone(cursor.getString(3));
                // Adding contact to list
                contactList.add(parishMemberModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


}