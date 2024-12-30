package com.mrd.pt.common.context;

import com.mrd.pt.system.entity.PtUser;

/**
 * @author marundong
 */
public class PtUserContext {
    private static final ThreadLocal<PtUser> PT_USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void putPtUser(PtUser ptUser){
        PT_USER_THREAD_LOCAL.set(ptUser);
    }

    public static void getPtUser(){
        PT_USER_THREAD_LOCAL.get();
    }

    public void remove(){
        PT_USER_THREAD_LOCAL.remove();
    }
}
