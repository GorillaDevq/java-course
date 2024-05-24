package com.campus.course.lesson.controller;

import com.campus.course.course.entity.CourseEntity;
import com.campus.course.course.exception.CourseNotFoundException;
import com.campus.course.course.repository.CourseRepository;
import com.campus.course.lesson.dto.request.CreateLessonRequest;
import com.campus.course.lesson.dto.request.EditLessonRequest;
import com.campus.course.lesson.dto.response.LessonFullResponse;
import com.campus.course.lesson.dto.response.LessonResponse;
import com.campus.course.lesson.entity.LessonEntity;
import com.campus.course.lesson.exception.LessonNotFoundException;
import com.campus.course.lesson.repository.LessonRepository;
import com.campus.course.lesson.routes.LessonRoutes;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class LessonApiController {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @PostMapping(LessonRoutes.CREATE)
    public LessonFullResponse create(@RequestBody CreateLessonRequest request) throws CourseNotFoundException {
        CourseEntity courseEntity = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        LessonEntity entity = lessonRepository.save(request.entity());
        return LessonFullResponse.of(entity, courseEntity);
    }

    @GetMapping(LessonRoutes.BY_ID)
    public LessonFullResponse ById(@PathVariable Long id) throws CourseNotFoundException, LessonNotFoundException {
        LessonEntity lessonEntity = lessonRepository
                .findById(id)
                .orElseThrow(LessonNotFoundException::new);

        CourseEntity courseEntity = courseRepository
                .findById(lessonEntity.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        return LessonFullResponse.of(lessonEntity, courseEntity);
    }

    @PutMapping(LessonRoutes.EDIT)
    public LessonFullResponse edit(@PathVariable Long id, @RequestBody EditLessonRequest request) throws CourseNotFoundException, LessonNotFoundException {
        LessonEntity lessonEntity = lessonRepository
                .findById(id)
                .orElseThrow(LessonNotFoundException::new);

        CourseEntity courseEntity = courseRepository
                .findById(lessonEntity.getCourseId())
                .orElseThrow(CourseNotFoundException::new);

        lessonEntity.setTitle(request.getTitle());
        lessonEntity.setDescription(request.getDescription());

        lessonEntity = lessonRepository.save(lessonEntity);

        return LessonFullResponse.of(lessonEntity, courseEntity);
    }

    @GetMapping(LessonRoutes.SEARCH)
    public List<LessonResponse> search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageable = PageRequest.of(page, size);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<LessonEntity> example = Example.of(
                LessonEntity.builder()
                        .title(query)
                        .description(query)
                        .build(), exampleMatcher);

        return lessonRepository.findAll(example, pageable)
                .stream().map(LessonResponse::of).collect(Collectors.toList());
    }

    @DeleteMapping(LessonRoutes.DELETE)
    public String delete(@PathVariable Long id) {
        lessonRepository.deleteById(id);

        return HttpStatus.OK.name();
    }
}
