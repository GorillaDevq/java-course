package com.campus.course.course.routes;

import com.campus.course.base.routes.BaseRoutes;

public class CourseRoutes {
    private final static String ROOT = BaseRoutes.API + "/course";
    public final static String CREATE = ROOT;
    public final static String BY_ID = ROOT + "/{id}";
    public final static String EDIT = BY_ID;
    public final static String SEARCH = ROOT;
    public final static String DELETE = BY_ID;
}