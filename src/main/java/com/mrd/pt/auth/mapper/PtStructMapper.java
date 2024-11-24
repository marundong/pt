package com.mrd.pt.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author marundong
 */
@Mapper
public interface PtStructMapper {

    PtStructMapper INSTANCE = Mappers.getMapper(PtStructMapper.class);

}
