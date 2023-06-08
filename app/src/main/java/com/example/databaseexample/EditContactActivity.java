package com.example.databaseexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContactActivity extends AppCompatActivity {
    EditText editName,editPhn;
    Button btnUpdate,btnDelete;
    DBHelper dbHelper;
    String name,phn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        dbHelper=new DBHelper(EditContactActivity.this);
        final Contacts_Model contacts_model=(Contacts_Model) getIntent().getSerializableExtra("Contacts");
        //Toast.makeText(this, contacts_model.getName(), Toast.LENGTH_SHORT).show();
        editName=findViewById(R.id.editName);
        editPhn=findViewById(R.id.editPhn);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        editName.setText(contacts_model.getName());
        editPhn.setText(String.valueOf(contacts_model.getPhn_no()));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=editName.getText().toString();
                phn=editPhn.getText().toString();
                long phnNew= Long.parseLong(phn);
                //Contacts_Model contacts_model1=new Contacts_Model(contacts_model.getId(),name,phnNew);
                contacts_model.setName(name);
                contacts_model.setPhn_no(phnNew);
                boolean updated=dbHelper.updateContact(contacts_model);
                if(updated)
                {
                    Toast.makeText(EditContactActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditContactActivity.this,MainActivity.class);
                    startActivity(intent);
                    //intent.putExtra("updatedContact",contacts_model1);
                    //setResult(RESULT_OK,intent);
                }
                else
                {
                    Toast.makeText(EditContactActivity.this, "Not Updated", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(EditContactActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aBuilder=new AlertDialog.Builder(EditContactActivity.this);
                aBuilder.setTitle("Delete");
                aBuilder.setMessage("Do you want to delete?");
                aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean deleted=dbHelper.deleteContact(contacts_model.getId());
                        if(deleted)
                        {
                            Toast.makeText(EditContactActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(EditContactActivity.this,MainActivity.class);
                            startActivity(intent);
                            //setResult(RESULT_OK);
                        }
                        else
                        {
                            Toast.makeText(EditContactActivity.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aBuilder.create().dismiss();
                    }
                });
                aBuilder.show();
            }
        });
    }
}