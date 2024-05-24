package com.campus.course.course.dto.response;

import com.campus.course.course.entity.CourseEntity;
import com.campus.course.lesson.dto.response.LessonResponse;
import com.campus.course.lesson.entity.LessonEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
public class CourseFullResponse extends CourseEntity {
    private List<LessonResponse> lessons = new ArrayList<>();

    public static CourseFullResponse of(CourseEntity courseEntity, List<LessonEntity> lessonsEntityList) {
        return CourseFullResponse.builder()
                .id(courseEntity.getId())
                .title(courseEntity.getTitle())
                .description(courseEntity.getDescription())
                .lessons(lessonsEntityList.stream().map(LessonResponse::of).collect(Collectors.toList()))
                .build();
    }
}
