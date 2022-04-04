package com.scb.fimob.service.mapper;

import com.scb.fimob.domain.Ethnicity;
import com.scb.fimob.service.dto.EthnicityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ethnicity} and its DTO {@link EthnicityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EthnicityMapper extends EntityMapper<EthnicityDTO, Ethnicity> {}
