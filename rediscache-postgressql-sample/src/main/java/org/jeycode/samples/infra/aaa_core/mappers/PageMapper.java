package org.jeycode.samples.infra.aaa_core.mappers;

import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface PageMapper {

  <T> DataPage<T> toDataPage(Page<T> page);

}
