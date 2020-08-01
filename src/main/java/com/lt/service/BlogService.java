package com.lt.service;

import com.lt.po.Blog;
import com.lt.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//博客管理
public interface BlogService {

    //根据id查询
    Blog getBlog(Long id);

    //分页查询  根据blog的属性(条件)进行分页查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //新增
    Blog saveBlog(Blog blog);

    //修改  根据id查出来  在进行修改
    Blog updateBlog(Long id,Blog blog);

    //删除
    void deleteBlog(Long id);

}
