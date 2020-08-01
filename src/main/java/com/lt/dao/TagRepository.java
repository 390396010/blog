package com.lt.dao;


import com.lt.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

//标签
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
}
