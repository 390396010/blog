package com.lt.web.admin;


import com.lt.po.Type;
import com.lt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分页查询全部
     * size  每页显示10   sort  按照id排序      direction = Sort.Direction.DESC 排序方式为降序
     * @param pageable  排序封装类
     * @return
     */

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //返回一个新增的页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * 在实体类写了数据校验的注解
     * 想在前端获取到数据校验中的文字  需要加@Valid  代表要校验Type对象
     * BindingResult  接收校验之后的结果  hasErrors()有没有错误
     * @Valid Type type, BindingResult result,  这俩必须是挨着的
     */
    //新增实现
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //保证用分类名唯一的校验
        Type type1 = typeService.getTypeByNname(type.getName());
        if(type1 !=null){
            //存在此分类   加一些错误传到前端去  name是类中的属性名
            result.rejectValue("name","nameError ","不能添加重复的分类");
        }

        //将后端检验信息返回
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";

    }

    /**
     * 先查询到id展现到页面上  ，!!!!然后在进行修改
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    /**
     * !!!修改
     */
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        //保证用分类名唯一的校验
        Type type1 = typeService.getTypeByNname(type.getName());
        if (type1 != null) {
            //存在此分类   加一些错误传到前端去  name是类中的属性名
            result.rejectValue("name", "nameError ", "不能添加重复的分类");
        }
        //将后端检验信息返回
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除
     * 前段路基 @{/admin/types/{id}/delete(id=${type.id})}"
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }



}
