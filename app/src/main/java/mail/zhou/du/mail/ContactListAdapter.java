package mail.zhou.du.mail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * Created by zhou on 16/7/7.
 */
public class ContactListAdapter extends BaseAdapter {
    private static final String TAG = "ContactListAdapter";
    List<Contact> mContactList = null;
    private LayoutInflater mInflater;

    public ContactListAdapter(Context context, List<Contact> mContactList) {
        this.mContactList = mContactList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Contact getItem(int i) {
        return mContactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        android.R.layout.simple_list_item_checked;
        Log.e(TAG,"getView:" + mContactList.get(i).getEmailAddress());
        ViewHolder viewHolder = null;
        if (view == null){
            view = mInflater.inflate(android.R.layout.simple_list_item_checked,null);
            viewHolder = new ViewHolder();
            viewHolder.text = (CheckedTextView)view.findViewById(android.R.id.text1);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Contact contact = mContactList.get(i);
        viewHolder.text.setText(contact.getEmailAddress());
        viewHolder.text.setChecked(contact.getChecked());
        return view;
    }

    public final class ViewHolder {
        public CheckedTextView text;
    }
}
