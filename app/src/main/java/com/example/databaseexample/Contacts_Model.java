package com.example.databaseexample;

import java.io.Serializable;

public class Contacts_Model implements Serializable {
    int id;
    String name;
    long phn_no;

    public Contacts_Model()
    {
//        this.id=id;
//        this.name=name;
//        this.phn_no=phn_no;
    }

    public Contacts_Model(int id,String name,long phn_no)
    {
        this.id=id;
        this.name=name;
        this.phn_no=phn_no;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public long getPhn_no()
    {
        return phn_no;
    }
    public void setPhn_no(long phn_no)
    {
       this.phn_no=phn_no;
    }
}
