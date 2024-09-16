package com.example.web2.Enums;

public enum EnumOrder {
    DANG_XU_LY("Đang xử lý"),
    DA_LAY("Đã lấy" ),
    DA_HUY("Đã hủy");
    private final String description;
    EnumOrder(String description1) {
        this.description = description1;
    }
    public String getDescription() {
        return description;
    }
}
