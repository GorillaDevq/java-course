package com.campus.course.lesson.dto.response;

import com.campus.course.course.dto.response.CourseResponse;
import com.campus.course.course.entity.CourseEntity;
import com.campus.course.lesson.entity.LessonEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonFullResponse {
    protected long id;
    protected long courseId;
    protected String title;
    protected String description;
    protected CourseResponse course;

    public static LessonFullResponse of(LessonEntity entity, CourseEntity course) {
        return LessonFullResponse.builder()
                .id(entity.getId())
                .courseId(entity.getCourseId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .course(CourseResponse.of(course))
                .build();
    }
}
