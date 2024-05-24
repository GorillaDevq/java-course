package com.campus.course.student.dto.response;

import com.campus.course.student.entity.StudentEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StudentResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public static StudentResponse of(StudentEntity student) {
        return StudentResponse.builder()
                .id(student.getId())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .build();
    }
}
