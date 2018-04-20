package com.daily.common;

/**
 * Created by xuqipei on 17-7-17.
 */
public enum MerchantTableCode {
    REVERSED(2, "reserved"),
    FREE(0, "free"),
    USING(1, "using"),;

    private final int code;
    private final String desc;

    MerchantTableCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
