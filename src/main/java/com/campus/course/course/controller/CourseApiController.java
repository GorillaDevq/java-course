package com.campus.course.course.controller;

import com.campus.course.course.dto.request.CreateCourseRequest;
import com.campus.course.course.dto.request.EditCourseRequest;
import com.campus.course.course.dto.response.CourseResponse;
import com.campus.course.course.entity.CourseEntity;
import com.campus.course.course.exception.CourseNotFoundException;
import com.campus.course.course.repository.CourseRepository;
import com.campus.course.course.routes.CourseRoutes;
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

    @PostMapping(CourseRoutes.CREATE)
    public CourseResponse create(@RequestBody CreateCourseRequest request) {
        CourseEntity entity = courseRepository.save(request.entity());
        return CourseResponse.of(entity);
    }

    @GetMapping(CourseRoutes.BY_ID)
    public CourseResponse findById(@PathVariable long id) throws CourseNotFoundException {
        return courseRepository.findById(id)
                .map(CourseResponse::of)
                .orElseThrow(CourseNotFoundException::new);
    }

    @PutMapping(CourseRoutes.EDIT)
    public CourseResponse edit(@PathVariable long id, @RequestBody EditCourseRequest request) throws CourseNotFoundException {
        CourseEntity entity = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);

        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());

        entity = courseRepository.save(entity);

        return CourseResponse.of(entity);
    }

    @DeleteMapping(CourseRoutes.DELETE)
    public String delete(@PathVariable long id){
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
