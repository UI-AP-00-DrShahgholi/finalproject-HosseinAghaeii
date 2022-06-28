package com.company;

public class User {
    private String name;
    private String NationalCode;
    private int age;
    private gender gender;
    private int wallet=0;

    enum gender{
        MALE,
        FEMALE
    }

    public User(String name, String nationalCode, int age, int wallet) {
        this.name = name;
        NationalCode = nationalCode;
        this.age = age;
        this.wallet = wallet;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNationalCode(String nationalCode) {
        NationalCode = nationalCode;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(User.gender gender) {
        this.gender = gender;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public int getAge() {
        return age;
    }

    public User.gender getGender() {
        return gender;
    }

    public int getWallet() {
        return wallet;
    }
}
