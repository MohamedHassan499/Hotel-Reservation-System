/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Checkin;

/**
 *
 * @author BluRay
 */
interface Person {
    String getFirstName();
    String getSurName();
    String getID();
    String getPhoneNo();
    String getAge();
    String getAddress();
    String getNationality();
    String getReserveDate();
    String getWIFI();
    String getGender();
    String getRoomNo();
    String getFeeType();
    String getCreditNo();
}
class Guest implements Person{
    String firstName;
    String surName;
    String ID;
    String phoneNo;
    String age;
    String gender;
    String wifi;
    String feeType;
    String roomNo;
    String creditNo;
    String address;
    String nation;
    String reserveDate;
    
    
    public Guest(String first, String sur, String id, String phone, String a, String g, String w, String fee, String room, String cr, 
            String add, String nation, String res){
        firstName=first;
        surName=sur;
        ID=id;
        phoneNo=phone;
        age=a;
        gender=g;
        wifi=w;
        feeType=fee;
        roomNo=room;
        creditNo=cr;
        address=add;
        this.nation=nation;
        reserveDate=res;
    }
    
    @Override
    public String getFirstName() {
       return firstName;
    }

    @Override
    public String getSurName() {
        return surName;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public String getPhoneNo() {
        return phoneNo;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getNationality() {
        return nation;
    }

    @Override
    public String getReserveDate() {
        return reserveDate;
    }

    @Override
    public String getWIFI() {
        return wifi;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getRoomNo() {
        return roomNo;
    }

    @Override
    public String getFeeType() {
        return feeType;
    }

    @Override
    public String getCreditNo() {
        return creditNo;
    }
}
