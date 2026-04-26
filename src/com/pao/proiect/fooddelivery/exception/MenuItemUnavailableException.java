package com.pao.proiect.fooddelivery.exception;

public class MenuItemUnavailableException extends RuntimeException{
    public MenuItemUnavailableException(String message){
        super(message);
    }
}