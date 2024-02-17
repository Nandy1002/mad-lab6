package com.example.lab6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Contact ,Emessage;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = findViewById(R.id.Send);
        Contact=findViewById(R.id.Contact);
        Emessage=findViewById(R.id.EMessage);
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                // this is to Enable the intent to Access the Contacts present in the Android Contact
                // and send the Contact details to app
                startActivityForResult(i,1);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SmsManager smg = SmsManager.getDefault();
                    //This is used get the smsManager from the android Device

                    smg.sendTextMessage(Contact.getText().toString(), null, Emessage.getText().toString(), null, null);

//This is line will use  smsManager  to send mail from the app.

                    Toast.makeText(MainActivity.this, "Message Send", Toast.LENGTH_LONG).show();
                }catch (Exception e)
                {       Log.e("Message",e.toString());
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                try{

                    Uri contactData = data.getData();
//Accessing the data which is send by the intent Activity
                    Cursor cursor = managedQuery(contactData,null,null,null,null);
//Use to traversal the data  which is got from activity
                    cursor.moveToFirst();
//Accessing the First Data in Got data
                    String number="Contact Numbere";



                    int xz=cursor.getColumnIndex("data1");
//Getting the Index where Phone Number Exists
                    number=cursor.getString(xz);
//Getting the phone number from the  Contact  Data;


                    Contact.setText(number);

                }
                catch (Exception e )
                {
                    Contact.setText(e.toString());
                }
            }
        }
    }
}