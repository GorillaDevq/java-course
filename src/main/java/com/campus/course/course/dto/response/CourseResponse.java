package com.campus.course.course.dto.response;

import com.campus.course.course.entity.CourseEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CourseResponse extends CourseEntity {

    public static CourseResponse of(CourseEntity courseEntity) {
        return CourseResponse.builder()
                .id(courseEntity.getId())
                .title(courseEntity.getTitle())
                .description(courseEntity.getDescription())
                .build();
    }
}
