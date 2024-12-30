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

import java.io.Serializable;

/**
 * @author marundong
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
@Data
public class PtRole  extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    private String name;

    private String code;

    private Long parentId;

    private Long idPath;

    private String codePath;

}
