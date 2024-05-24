package com.campus.course.student.dto.request;

import com.campus.course.base.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistrationStudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public void validate() throws BadRequestException {
        if (email == null || email.isEmpty()) throw new BadRequestException();
        if (password == null || password.isEmpty()) throw new BadRequestException();
    }
}
