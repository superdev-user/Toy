package com.superdev.toy.web.domain.mapper;

import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import com.superdev.toy.web.domain.StudyRoomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Created by kimyc on 15/08/2019.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudyRoomMapper {
    StudyRoom map(StudyRoomRequest request);
}
