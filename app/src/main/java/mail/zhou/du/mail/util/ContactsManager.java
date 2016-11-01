package mail.zhou.du.mail.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mail.zhou.du.mail.App;
import mail.zhou.du.mail.util.db.Contact;
import mail.zhou.du.mail.util.db.ContactDB;
import mail.zhou.du.mail.util.db.DBHelper;

/**
 * Created by zhou on 16/7/7.
 */
public class ContactsManager {
    private static String TAG = "ContactsManager";
    private Context mContext;
    private DBHelper mDBHelper;

    private ContactsManager() {
        mContext = App.getAppContext();
        mDBHelper = new DBHelper(mContext);
    }

    private static ContactsManager instance;

    public static ContactsManager get() {
        if (instance == null)
            instance = new ContactsManager();
        return instance;
    }

    /**
     * 获取所有的联系人
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        Cursor cursor = database.query(ContactDB.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = loadCursorToContact(cursor);
            contacts.add(contact);
        }
        cursor.close();
        database.close();
        Log.e(TAG, "getAllContacts:" + contacts.size());
        return contacts;
    }

    /**
     * 获取所有的联系人
     */
    public List<Contact> getCheckedContacts() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        Cursor cursor = database.query(ContactDB.TABLE_NAME, null, ContactDB.CHECKED + " = 1 ", null, null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = loadCursorToContact(cursor);
            contacts.add(contact);
        }
        cursor.close();
        database.close();
        return contacts;
    }

    public void updateContacts(List<Contact> contacts) {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Contact contact : contacts) {
            values.put(ContactDB.EMAIL_ADDRESS, contact.getEmailAddress());
            values.put(ContactDB.CHECKED, contact.getChecked() ? 1 : 0);
            String selection = ContactDB.ID + " = " + contact.getId();
            database.update(ContactDB.TABLE_NAME, values, selection, null);
            values.clear();
        }
        database.close();
    }

    public void updateContact(int id, boolean checked) {
        Log.e(TAG,"checked:"+checked);
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactDB.CHECKED, checked ? 1 : 0);
        String selection = ContactDB.ID + " = " + id;
        database.update(ContactDB.TABLE_NAME, values, selection, null);
        database.close();
    }

    public void insertContact(String emailAddress) {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactDB.CHECKED, 0);
        values.put(ContactDB.EMAIL_ADDRESS, emailAddress);
        database.insert(ContactDB.TABLE_NAME, null, values);
        database.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        database.delete(ContactDB.TABLE_NAME, ContactDB.ID + " = " + id, null);
        database.close();
    }

    private Contact loadCursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getInt(cursor.getColumnIndex(ContactDB.ID)));
        contact.setEmailAddress(cursor.getString(cursor.getColumnIndex(ContactDB.EMAIL_ADDRESS)));
        contact.setChecked(cursor.getInt(cursor.getColumnIndex(ContactDB.CHECKED)) == 1 ? true : false);
        Log.e(TAG, "loadCursorToContact: "+contact.getChecked());
        return contact;
    }

    public String[] getSendMailAddress(){
        List<Contact> contacts = getCheckedContacts();
        String[] emails = new String[contacts.size()];
        for(int i = 0 ; i < contacts.size() ; i ++ ){
            emails[i] = contacts.get(i).getEmailAddress();
        }
        Log.e(TAG, Arrays.toString(emails));
        return emails;
    }


}
