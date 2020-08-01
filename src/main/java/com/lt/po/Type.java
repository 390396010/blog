package com.lt.po;


import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type")          //配置一对多的关系  mappedBy取值对方配置关系的属性名称   是被维护关系    //一个type可以包含多个blog
    private List<Blog> blogs=new ArrayList<>();

    public Type() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
