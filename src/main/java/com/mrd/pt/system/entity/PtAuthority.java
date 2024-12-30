package com.mrd.pt.system.entity;

import com.mrd.pt.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author marundong
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_authority")
@Data
public class PtAuthority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    private String name;

    private String code;

    private Long parentId;

    private String idPath;

    private String codePath;
}
