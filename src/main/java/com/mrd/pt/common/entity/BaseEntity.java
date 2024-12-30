package com.mrd.pt.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author marundong
 */
@Data
public class BaseEntity implements Serializable {

    private String creator;

    private LocalDateTime createTime;

    private String updater;

    private LocalDateTime updateTime;

    private Instant ts;

    private int dr;
}
