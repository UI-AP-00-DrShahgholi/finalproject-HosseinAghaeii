package com.company;

public class NotEnoughMoneyException extends  Exception{
    public NotEnoughMoneyException(){
        super("not enough money in wallet ");
    }
}
