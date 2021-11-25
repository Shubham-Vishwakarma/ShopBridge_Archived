package com.thinkbridge.shopbridge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(long id) {
        super("No item available with id = " + id);
    }
    public ItemNotFoundException(String name) {
        super("No item available with item name as " + name);
    }
}
