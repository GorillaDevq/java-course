package com.campus.course.lesson.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    protected long courseId;
    protected String title;
    protected String description;

}
