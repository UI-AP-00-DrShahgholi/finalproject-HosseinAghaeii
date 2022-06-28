package com.company;

public class Estate {
    private String SACode; // sabt asnad code
    private String ownerNCode; // owner national code
    private String address;
    private String buyDate;
    private int  Value;

    public Estate(String SACode,String ownerNCode, String buyDate, int value) {
        this.SACode = SACode;
        this.ownerNCode=ownerNCode;
        this.buyDate = buyDate;
        Value = value;
    }

    public String getSACode() {
        return SACode;
    }

    public String getOwnerNCode() {
        return ownerNCode;
    }

    public String getAddress() {
        return address;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public int getValue() {
        return Value;
    }

    public void setSACode(String SACode) {
        this.SACode = SACode;
    }

    public void setOwnerNCode(String ownerNCode) {
        this.ownerNCode = ownerNCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public void setValue(int value) {
        Value = value;
    }
}
