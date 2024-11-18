package com.mrd.pt.common.code;

import lombok.ToString;

/**
 * @author marundong
 */
@ToString
public enum SysResultCode implements ResultCode {
    SUCCESS(true, "0", "操作成功"),
    AUTH_FAILED(false, "10001", "授权失败"),
    ERROR(false, "99999", "系统异常");

    private final boolean success;

    private final String code;

    private final String msg;

    SysResultCode(boolean success, String code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
