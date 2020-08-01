package com.lt.web.admin;

import com.lt.po.Blog;
import com.lt.po.User;
import com.lt.service.BlogService;
import com.lt.service.TagService;
import com.lt.service.TypeService;
import com.lt.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                        BlogQuery blog, Model model){
        //查询到分类的全部
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC)Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    //跳转到发布博客的页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        //初始化分类  ajax查询的时候显示在页面
        model.addAttribute("types",typeService.listType());
        //初始化标签  ajax查询的时候显示在页面
        model.addAttribute("tags",tagService.listTag());
        //初始化一个Blog是为了在和修改公用一个页面的时候，返回的时候不报错
        model.addAttribute("blog",new Blog());

        //这样初始化可以在前端拿到数据 在进页面前就查询出来全部  显示在页面 供选择
        return INPUT;
    }
    //新增   简单处理 没有实现后端校验 只实现的前端校验
    //attributes重定向数据转发的类
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        //获取到user
        blog.setUser((User) session.getAttribute("user"));
        //获取到type
        blog.setType(typeService.getType(blog.getType().getId()));
        //获取到多个tags
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if (b == null){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

}
