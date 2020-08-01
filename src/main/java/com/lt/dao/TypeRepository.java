package com.lt.dao;

import com.lt.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分类
 */
public interface TypeRepository extends JpaRepository<Type,Long>{

   Type findByName(String name);


}
