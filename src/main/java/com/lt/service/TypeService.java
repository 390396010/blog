package com.lt.service;

import com.lt.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 分类
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    //Page 分页类  Pageable分页参数
    Page<Type> listType(Pageable pageable);

    Type getTypeByNname(String name);

    List<Type> listType();

    Type updateType(Long id,Type type);

    void deleteType(Long id);

}
