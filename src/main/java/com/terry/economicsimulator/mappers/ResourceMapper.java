package com.terry.economicsimulator.mappers;

import com.terry.economicsimulator.db.models.IResource;
import com.terry.economicsimulator.mappers.helper.ResourceTypeMapper;
import com.terry.economicsimulator.models.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ResourceMapper implements ResourceTypeMapper {
	public static final ResourceMapper MAPPER = Mappers.getMapper(ResourceMapper.class);

	@Mapping(source = "resourceType", target = "resourceType")
	public abstract Resource resourceRecordToResource(IResource auditEventRecord);

}
