package com.example.web2.Enums;

import lombok.Getter;


@Getter

public enum LibraryCardEnums {
    CON_HAN("Còn hạn"),HET_HAN("Hết hạn");
    private String message;
    LibraryCardEnums(String message) {
        this.message=message;
    }

}
