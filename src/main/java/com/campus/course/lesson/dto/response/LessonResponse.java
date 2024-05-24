package com.campus.course.lesson.dto.response;

import com.campus.course.lesson.entity.LessonEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonResponse {
    protected long id;
    protected long courseId;
    protected String title;
    protected String description;

    public static LessonResponse of(LessonEntity entity) {
        return LessonResponse.builder()
                .id(entity.getId())
                .courseId(entity.getCourseId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }
}
