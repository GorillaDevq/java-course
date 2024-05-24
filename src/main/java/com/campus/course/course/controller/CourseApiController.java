package com.campus.course.course.controller;

import com.campus.course.course.dto.request.CreateCourseRequest;
import com.campus.course.course.dto.request.EditCourseRequest;
import com.campus.course.course.dto.response.CourseFullResponse;
import com.campus.course.course.dto.response.CourseResponse;
import com.campus.course.course.entity.CourseEntity;
import com.campus.course.course.exception.CourseNotFoundException;
import com.campus.course.course.repository.CourseRepository;
import com.campus.course.course.routes.CourseRoutes;
import com.campus.course.lesson.entity.LessonEntity;
import com.campus.course.lesson.repository.LessonRepository;
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
public class CourseApiController {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @PostMapping(CourseRoutes.CREATE)
    public CourseFullResponse create(@RequestBody CreateCourseRequest request) {
        CourseEntity entity = courseRepository.save(request.entity());

        return CourseFullResponse.of(entity, lessonRepository.findByCourseId(entity.getId()));
    }

    @GetMapping(CourseRoutes.BY_ID)
    public CourseFullResponse findById(@PathVariable long id) throws CourseNotFoundException {
        CourseEntity entity = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        return CourseFullResponse.of(entity, lessonRepository.findByCourseId(entity.getId()));
    }

    @PutMapping(CourseRoutes.EDIT)
    public CourseFullResponse edit(@PathVariable long id, @RequestBody EditCourseRequest request) throws CourseNotFoundException {
        CourseEntity entity = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());

        entity = courseRepository.save(entity);

        return CourseFullResponse.of(entity, lessonRepository.findByCourseId(entity.getId()));
    }

    @DeleteMapping(CourseRoutes.DELETE)
    public String delete(@PathVariable long id){
        List<LessonEntity> lessons = lessonRepository.findByCourseId(id);
        for (LessonEntity lesson : lessons) lessonRepository.deleteById(lesson.getId());
        courseRepository.deleteById(id);
        return HttpStatus.OK.name();
    }

    @GetMapping(CourseRoutes.SEARCH)
    public List<CourseResponse> search(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageable = PageRequest.of(page, size);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("description", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<CourseEntity> example = Example.of(
                CourseEntity.builder()
                        .title(query)
                        .description(query)
                        .build(), exampleMatcher);

        return courseRepository.findAll(example, pageable)
                .stream().map(CourseResponse::of).collect(Collectors.toList());
    }
}
