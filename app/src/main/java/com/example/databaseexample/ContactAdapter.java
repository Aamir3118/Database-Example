package com.example.databaseexample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contacts_Model> {
    private Context context;
    private List<Contacts_Model> contacts_modelList;
    public ContactAdapter(@NonNull Context context, List<Contacts_Model> contacts_modelList) {
        super(context,R.layout.contacts_listitems,contacts_modelList);
        this.contacts_modelList=contacts_modelList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.contacts_listitems,parent,false);
        TextView tvName=view.findViewById(R.id.tvName);
        TextView tvPhn=view.findViewById(R.id.tvPhn);
        ImageView imgCall=view.findViewById(R.id.imgCall);
        tvName.setText(contacts_modelList.get(position).getName());
        tvPhn.setText(String.valueOf(contacts_modelList.get(position).getPhn_no()));
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phn=String.valueOf(contacts_modelList.get(position).getPhn_no());
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phn));
                context.startActivity(intent);
            }
        });
        return view;
    }

//    public void setContactList(List<Contacts_Model> contactList){
//        contacts_modelList.clear();
//        contacts_modelList.addAll(contactList);
//        notifyDataSetChanged();
//    }
}
