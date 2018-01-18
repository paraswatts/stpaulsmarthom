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

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class DbContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DbContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "stpaulsmarthom.com.stpaulsmarthom";



    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_ABOUT_US = "aboutus";

    public static final String PATH_OUR_BISHOPS = "ourbishops";


    public static final String PATH_OUR_VICAR = "ourvicar";

    public static final String PATH_KAISTHANA = "kaisthana";


    public static final String PATH_ORGANISATIONS = "organisations";

    public static final String PATH_PARISH_MEMBERS = "parish_members";

    public static final String PATH_PUBLICATIONS = "parish_publications";

    public static final String PATH_CHILD = "parish_child";


    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */


    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class DbEntry implements BaseColumns {

        /** The content URI to access the pet data in the provider */






        public static final Uri CONTENT_URI_ABOUT_US = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ABOUT_US);

        public static final Uri CONTENT_URI_CHILD = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CHILD);


        public static final Uri CONTENT_URI_OUR_BISHOPS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_OUR_BISHOPS);


        public static final Uri CONTENT_URI_OUR_VICAR = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_OUR_VICAR);


        public static final Uri CONTENT_URI_KAISTHANA = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KAISTHANA);


        public static final Uri CONTENT_URI_ORGANISATIONS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ORGANISATIONS);

        public static final Uri CONTENT_URI_PARISH_MEMBER = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PARISH_MEMBERS);
        public static final Uri CONTENT_URI_PARISH_PUBLICATIONS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PUBLICATIONS);


        /**

*/

        public static final String CONTENT_LIST_TYPE_CHILD = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHILD;


        public static final String CONTENT_LIST_TYPE_ABOUT_US = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ABOUT_US;


        public static final String CONTENT_LIST_TYPE_OUR_BISHOPS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OUR_BISHOPS;



        public static final String CONTENT_LIST_TYPE_OUR_VICAR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OUR_VICAR;


        public static final String CONTENT_LIST_TYPE_KAISTHANA = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KAISTHANA;


        public static final String CONTENT_LIST_TYPE_ORGANISATIONS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORGANISATIONS;


        public static final String CONTENT_LIST_TYPE_PARISH_MEMBER = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PARISH_MEMBERS;

        public static final String CONTENT_LIST_TYPE_PUBLICATIONS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PUBLICATIONS;


        public static final String CONTENT_ITEM_TYPE_CHILD =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHILD;


        public static final String CONTENT_ITEM_TYPE_ABOUT_US =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ABOUT_US;


        public static final String CONTENT_ITEM_TYPE_OUR_BISHOPS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OUR_BISHOPS;


        public static final String CONTENT_ITEM_TYPE_OUR_VICAR =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OUR_VICAR;


        public static final String CONTENT_ITEM_TYPE_KAISTHANA =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KAISTHANA;


        public static final String CONTENT_ITEM_TYPE_ORGANISATIONS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ORGANISATIONS;


        public static final String CONTENT_ITEM_TYPE_PARISH_MEMBER =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PARISH_MEMBERS;


        public static final String CONTENT_ITEM_TYPE_PUBLICATIONS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PUBLICATIONS;



        /** Name of database tables */
        public final static String TABLE_CHILD = "child";


        public final static String TABLE_ABOUT_US = "about_us";

        public final static String TABLE_PRIVACY = "privacy_policy";

        public final static String TABLE_OUR_BISHOPS = "our_bishops";


        public final static String TABLE_OUR_VICAR = "our_vicar";


        public final static String TABLE_KAISTHANA = "kaisthana";


        public final static String TABLE_ORGANISATIONS_SECTIONS = "org_sections";
        public final static String TABLE_KAIS_SECTIONS = "kais_sections";

        public final static String TABLE_ORGANISATIONS = "organisations";

        public final static String TABLE_ORGANISATIONS_TIMING = "organisations_timing";

        public final static String TABLE_CONTACT_US = "contact_us";

        public final static String TABLE_PARISH = "parish_members";

        public final static String TABLE_PUBLICATIONS = "publications";

        public final static String COLUMN_ORG_SECTION = "org_section";

        public final static String COLUMN_ORG_SECTION_NUMBER = "org_section_number";

        public final static String COLUMN_KAIS_SECTION = "kais_section";



        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PRIVACY_POLICY = "privacy_policy";


        public final static String COLUMN_ABOUT_US_CONTENT = "about_us_content";

        public final static String COLUMN_ABOUT_US_IMAGE = "about_us_image";

        public final static String COLUMN_BISHOP_NAME = "bishop_name";

        public final static String COLUMN_BISHOP_PHONE = "bishop_phone";

        public final static String COLUMN_BISHOP_ADDRESS = "bishop_address";
        public final static String COLUMN_BISHOP_EMAIL = "bishop_email";
        public final static String COLUMN_BISHOP_ABOUT = "bishop_about";
        public final static String COLUMN_BISHOP_IMAGE = "bishop_image";
        public final static String COLUMN_BISHOP_DOB = "bishop_dob";


        public final static String COLUMN_OUR_VICAR = "our_vicar";
        public final static String COLUMN_VICAR_IMAGE = "vicar_image";
        public final static String COLUMN_VICAR_TITLE = "vicar_title";


        public final static String COLUMN_KAISTHANA_NAME = "kais_name";

        public final static String COLUMN_KAISTHANA_PHONE = "kais_phone";

        public final static String COLUMN_KAISTHANA_DESIGNATION = "kais_des";
        public final static String COLUMN_KAISTHANA_ROLLNO = "kais_rollno";

        public final static String COLUMN_KAISTHANA_MEMBER_TYPE = "kais_member_type";

        public final static String COLUMN_KAISTHANA_IMAGE = "kais_image";


        public final static String COLUMN_ORG_NAME = "org_name";

        public final static String COLUMN_ORG_PHONE = "org_phone";

        public final static String COLUMN_ORG_DESIGNATION = "org_des";

        public final static String COLUMN_ORG_MEMBER_TYPE = "org_member_type";

        public final static String COLUMN_ORG_NUMBER = "org_number";

        public final static String COLUMN_CONTACT_ADDRESS = "contact_address";

        public final static String COLUMN_CONTACT_PHONE = "contact_phone";


        public final static String COLUMN_CONTACT_EMAIL = "contact_email";

        public final static String COLUMN_CONTACT_IMAGE = "contact_image";


        public final static String COLUMN_TIMING_PRACTISE = "timing_practise";
        public final static String COLUMN_TIMING_PRACTISE_TIME = "timing_practise_time";
        public final static String COLUMN_TIMING_NUMBER = "timing_practise_number";
        public final static String COLUMN_TIMING_MEMBER_TYPE = "timing_type";
        //parish member table


        public final static String MEMBER_NAME = "membername";
        public final static String MEMBER_ROLL_NO = "membernamerollno";
        public final static String MEMBER_AREA_NAME = "membernamearea";
        public final static String MEMBER_AGE = "memberyears";
        public final static String MEMBER_BLOOD = "memberblood";
        public final static String MEMBER_DOB = "memberdate";
        public final static String MEMBER_MARITAL = "membermarital";
        public final static String MEMBER_DOM = "memberdatemarriage";
        public final static String MEMBER_CPRNO = "cprno";
        public final static String MEMBER_COMPANY = "companyname";
        public final static String MEMBER_MAILING = "mailingadress";
        public final static String MEMBER_TEL = "telephone";
        public final static String MEMBER_FAX = "fax";
        public final static String MEMBER_HOME= "homeparirsh";
        public final static String MEMBER_FLAT_NO = "flatno";
        public final static String MEMBER_BUILDING_NO = "blgdno";
        public final static String MEMBER_ROAD = "road";
        public final static String MEMBER_BLOCK = "block";
        public final static String MEMBER_AREA = "area";
        public final static String MEMBER_EMERGENCY_NAME = "emergencyname";
        public final static String MEMBER_TEL_EMER = "telephoneemergency";
        public final static String MEMBER_HOUSE_NAME_INDIA = "housenameindia";
        public final static String MEMBER_PO = "postofficeindia";
        public final static String MEMBER_DISTRICT = "districtindia";
        public final static String MEMBER_STATE = "stateindia";
        public final static String MEMBER_TEL_CODE_INDIA = "telcodeindia";
        public final static String MEMBER_TOWN_INDIA = "townindia";
        public final static String MEMBER_PIN = "pinindia";
        public final static String MEMBER_EMER_INDIA_NAME = "emergencyindianame";
        public final static String MEMBER_TEL_EMER_INDIA = "telindiaemergency";
        public final static String MEMBER_WIFENAME = "wifename";
        public final static String MEMBER_DOB_WIFE = "datebirthwife";
        public final static String MEMBER_IN_BAHRAIN_WIFE = "inbaharahianfamily";
        public final static String MEMBER_EMP_WIFE = "employefamily";
        public final static String MEMBER_EMP_NAME_WIFE = "emaploynamefamily";
        public final static String MEMBER_NATIVE_WIFE = "nativefamily";
        public final static String MEMBER_BLOOD_WIFE = "bloodfamily";
        public final static String MEMBER_ACTIVE = "active";
        public final static String MEMBER_IMAGE = "memberimagepath";
        public final static String MEMBER_WIFE_IMAGE = "wife_image";
        public final static String MEMBER_TELEPHONE_RES1 = "telephoneres1";
        public final static String MEMBER_MOB1 = "telephonemob1";
        public final static String MEMBER_TELEPHONE_RES2 = "telephoneres2";
        public final static String MEMBER_MOB2 = "telephonemob2";
        public final static String MEMBER_TELEPHONE_RES3 = "telephoneres3";
        public final static String MEMBER_MOB3 = "telephonemob3";
        public final static String MEMBER_MOB_CODE = "mobcodeindia";
        public final static String MEMBER_MAILING_ADDRESS = "mailingadresss";

        public final static String PUBLICATIONS_PDF_NAME = "pdf_name";

        public final static String PUBLICATIONS_PDF_PATH = "pdf_path";

        public final static String PUBLICATIONS_PDF_IMAGE = "pdf_image";



        public final static String PARISH_CHILD_NAME = "child_name";
        public final static String PARISH_CHILD_BAH = "child_bah";

        public final static String PARISH_CHILD_SEX = "child_sex";

        public final static String PARISH_CHILD_DOB = "child_dob";

        public final static String PARISH_CHILD_BLOOD = "child_blood";

        public final static String PARISH_CHILD_STUDENT = "student_child";

        public final static String PARISH_CHILD_EMP = "child_emp";

        public final static String PARISH_CHILD_IMAGE = "child_image";

        public final static String PARISH_CHILD_EMP_NAME = "child_emp_name";

        public final static String PARISH_CHILD_ROLLNO = "child_roll_no";


    }

}

