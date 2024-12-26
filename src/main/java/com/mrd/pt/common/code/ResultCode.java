package com.mrd.pt.common.code;

/**
 * @author marundong
 * 99999 未知错误，统一提示
 * 10000 为系统代码
 * 20000 业务代码
 */
public interface ResultCode {

    boolean success();

    int code();

    String msg();

}
