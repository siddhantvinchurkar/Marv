package com.marv;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/* © Copyright 2016 Siddhant Vinchurkar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   */

public class Message {



    final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
    @SuppressWarnings("unused")
    private ContentResolver resolver;

    public Message(ContentResolver ConResolver){
        resolver = ConResolver;
    }

    public String getMessage(int batas) {
        Cursor cur = resolver.query(SMS_INBOX, null, null, null,null);
        String sms = "Message >> \n";
        int hitung = 0;
        while (cur.moveToNext()) {
            sms += "From :" + cur.getString(2) + " : " + cur.getString(11)+"\n";
            if(hitung == batas)
                break;
            hitung++;
        }
        return sms;
    }

    public int getMessageCountUnread(){
        Cursor c = resolver.query(SMS_INBOX, null, "read = 0", null, null);
        int unreadMessagesCount = c.getCount();
        c.deactivate();
        return unreadMessagesCount;
    }

    public String getMessageAll(){
        Cursor cur = resolver.query(SMS_INBOX, null, null, null,null);
        String sms = "Message >> \n";
        while (cur.moveToNext()) {
            sms += "From :" + cur.getString(2) + " : " + cur.getString(11)+"\n";
        }
        return sms;
    }

    public String getMessageUnread() {
        Cursor cur = resolver.query(SMS_INBOX, null, null, null,null);
        String sms = "Message(s) >> \n";
        int hitung = 0;
        while (cur.moveToNext()) {
            sms += "From :" + cur.getString(cur.getColumnIndexOrThrow("address")) + " : " + cur.getString(cur.getColumnIndexOrThrow("body"))+"\n";
            if(hitung == getMessageCountUnread())
                break;
            hitung++;
        }
        return sms;
    }

    public void setMessageStatusRead() {
        ContentValues values = new ContentValues();
        values.put("read",true);
        Cursor cur = resolver.query(SMS_INBOX, null, null, null,null);
        resolver.update(SMS_INBOX,values, cur.getString(cur.getColumnIndexOrThrow("_id")), null);
    }

}
