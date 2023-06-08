package com.example.databaseexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    Button btnAdd;
    EditText etName,etPhn;
    Button bAdd;
    String name,phn;
    ListView lv_contacts;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DBHelper(this);
        btnAdd=findViewById(R.id.btnAdd);
        lv_contacts=findViewById(R.id.lv_contact);
//        ArrayList<Contacts_Model> arrayList=new ArrayList<>();
//        ListAdapter listAdapter=new SimpleAdapter((Context) MainActivity.this, ArrayList) dbHelper.displayContact(),R.layout.contacts_listitems,new String[]{"Name","PhoneNo"},new int[]{R.id.tvName,R.id.tvPhn});
//        lv_contacts.setAdapter(listAdapter);

        contactAdapter=new ContactAdapter(this,dbHelper.displayContact());
        lv_contacts.setAdapter(contactAdapter);
        //contactAdapter.notifyDataSetChanged();

        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contacts_Model contacts_model=dbHelper.displayContact().get(i);
                Intent intent=new Intent(MainActivity.this,EditContactActivity.class);
                intent.putExtra("Contacts",contacts_model);
                //startActivityForResult(intent,1);
                startActivity(intent);
            }
        });

     btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.insert_layout);
                bAdd=dialog.findViewById(R.id.bAdd);
                bAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etName=dialog.findViewById(R.id.etName);
                        etPhn=dialog.findViewById(R.id.etPhn);
                        name=etName.getText().toString();
                        phn=etPhn.getText().toString().trim();
                        if(name.isEmpty() || phn.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();
                        }
                        else if(phn.length() !=10)
                        {
                            Toast.makeText(MainActivity.this,"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            long phnNumber=Long.parseLong(phn);
                            Contacts_Model contacts_model=new Contacts_Model();
                            contacts_model.setName(name);
                            contacts_model.setPhn_no(phnNumber);
                            if(dbHelper.addContact(contacts_model))
                            {
                                Toast.makeText(MainActivity.this,"Inserted",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                contactAdapter.clear();
                                contactAdapter.addAll(dbHelper.displayContact());
                                contactAdapter.notifyDataSetChanged();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Not Inserted",Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        contactAdapter.clear();
//        contactAdapter.addAll(dbHelper.displayContact());
//        contactAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode ==1 &&resultCode == RESULT_OK)
//        {
//            Contacts_Model updatedContact=(Contacts_Model) data.getSerializableExtra("updatedContact");
//            int pos=-1;
//            for (int i = 0; i < contactAdapter.getCount(); i++) {
//                Contacts_Model contact=contactAdapter.getItem(i);
//                if(contact.getId() == updatedContact.getId())
//                {
//                    pos=i;
//                    break;
//                }
//            }
//            if(pos !=-1)
//            {
//                contactAdapter.remove(contactAdapter.getItem(pos));
//                contactAdapter.insert(updatedContact,pos);
//                contactAdapter.notifyDataSetChanged();
//            }
//        }
//    }
}