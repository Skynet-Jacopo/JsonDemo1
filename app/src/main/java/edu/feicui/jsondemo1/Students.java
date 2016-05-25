package edu.feicui.jsondemo1;

import java.util.List;

/**
 * Created by ☆刘群☆ on 2016/5/25.
 */
public class Students {
    List<StudentBean> students;
    String classname;


    public List<StudentBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentBean> students) {
        this.students = students;
    }

    public String getClassName() {
        return classname;
    }

    public void setClassName(String className) {
        this.classname = className;
    }
}
