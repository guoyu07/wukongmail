package mail.zhou.du.mail.util.db;

/**
 * Created by zhou on 16/7/7.
 */
public class ContactDB {
    public static final String TABLE_NAME = "contacts";
    public static final String ID = "_id";
    public static final String EMAIL_ADDRESS = "email";
    public static final String CHECKED = "checked";

    public static final String sql_create = "create table IF NOT EXISTS " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement," +
            EMAIL_ADDRESS + " TEXT , " +
            CHECKED + " TEXT " +
            ")";
}
