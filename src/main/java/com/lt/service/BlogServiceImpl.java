package com.lt.service;

import com.lt.NotFoundException;
import com.lt.dao.BlogRepository;
import com.lt.po.Blog;
import com.lt.po.Type;
import com.lt.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /**
     * 分页动态查询
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
          return   blogRepository.findAll(new Specification<Blog>() {
                /**
                 *
                 * @param root  查询对象
                 * @param cq  查询条件的容器
                 * @param cb   设置具体条件表达式
                 * @return
                 */
                @Override
                public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                    List<Predicate> predicates=new ArrayList<>();
                    //根据标题和分类  进行模糊查询
                    if (!"".equals(blog.getTitle()) && blog.getTitle() !=null){
                        predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                    }
                    if (blog.getTypeId() !=null){
                        predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                    }
                    //是否根据推查询
                    if (blog.isRecommend()){
                        predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                    }
                    //条件只支持数组 所以要集合转数组
                    cq.where(predicates.toArray(new Predicate[predicates.size()]));
                    return null;
                }
            },pageable);
    }
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);  //浏览次数
        return blogRepository.save(blog);
    }

    //更新
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b);
        return blogRepository.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
