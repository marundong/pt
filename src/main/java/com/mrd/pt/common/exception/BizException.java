package com.mrd.pt.common.exception;

import com.mrd.pt.common.code.ResultCode;
import com.mrd.pt.common.code.SysResultCode;
import lombok.Getter;

/**
 * @author marundong
 */
@Getter
public class BizException extends RuntimeException{
    ResultCode resultCode;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BizException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public BizException(ResultCode resultCode,String message){
        super(message);
        this.resultCode = resultCode;
    }
    public BizException(String message){
        super(message);
        this.resultCode = SysResultCode.ERROR;
    }
}
