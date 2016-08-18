package com.nbp.zjycontactpro.db.bean;

import android.provider.BaseColumns;

/**
 * Created by zjygzc on 16/8/13.
 */
public class ContactTableContract {

    public static abstract class ContactTableEntry implements BaseColumns{

        public static String TBNAME = "contact";
        public static String NAME_ENTRY = "name";
        public static String PHONE_NUMBER_ENTRY = "phone_number";
        public static String EMAIL_ID_ENTRY = "email_id";
        public static String QQ_NUMBER_ENTRY = "qq_number";

    }
}
