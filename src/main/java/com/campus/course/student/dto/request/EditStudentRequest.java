package com.campus.course.student.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class EditStudentRequest {
    private String lastName;
    private String firstName;
}
