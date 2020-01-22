package com.example.agrimax;

public class Register {

    String name;
    String pass;

    public Register(String name, String pass){
        this.name=name;
        this.pass=pass;
    }

    public String getName(){
        return name;
    }

    public String getPass(){
        return pass;
    }
}
