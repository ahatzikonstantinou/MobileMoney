package ahat.mobilemoney.Storage;

import android.provider.BaseColumns;

/**
 * Created by antonis on 10/3/2017.
 * This is the contract that describes my sqllite database
 */

public final class StorageContract
{
    private StorageContract() {}

    /*
     * Table Bank
     */
    public static class DBBank implements BaseColumns
    {
        public final static String TABLE_NAME           = "bank";
        public final static String COLUMN_NAME_NAME     = "name";
        public final static String COLUMN_NAME_CODE     = "code";
        public final static String COLUMN_NAME_VERSION  = "version";
        public final static String COLUMN_NAME_USERNAME = "username";
        public final static String COLUMN_NAME_PASSWORD = "password";
        public final static String COLUMN_NAME_ACTIVE   = "active";
        public static final String COLUMN_NAME_TASKS    = "tasks";
    }

}
