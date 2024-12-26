package com.mrd.pt.common.response;

import com.mrd.pt.common.code.ResultCode;
import com.mrd.pt.common.code.SysResultCode;
import lombok.Data;

/**
 * @author marundong
 */
@Data
public class JsonResponse<T> {
    private boolean success = true;

    private int code = 0;

    private String msg ;

    private T data;


    public JsonResponse() {
    }

    public JsonResponse(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public JsonResponse(ResultCode resultCode, T data) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
        this.data = data;
    }

    public JsonResponse(T data) {
        this.data = data;
    }

    public static JsonResponse success() {
        return new JsonResponse<>(SysResultCode.SUCCESS);
    }

    public static <T> JsonResponse<T> success(T data) {
        JsonResponse<T> jsonResponse = new JsonResponse<>(SysResultCode.SUCCESS);
        jsonResponse.setData(data);
        return jsonResponse;
    }

    public static JsonResponse error() {
        return new JsonResponse<>(SysResultCode.ERROR);
    }
}