package edu.feicui.jsondemo1;

/**
 * Created by ☆刘群☆ on 2016/5/25.
 */
public class StudentBean {
    String name;
    int age;

    public StudentBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
