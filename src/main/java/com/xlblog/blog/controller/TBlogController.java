package com.xlblog.blog.controller;

import com.xlblog.blog.entity.TBlog;
import com.xlblog.blog.form.BlogForm;
import com.xlblog.blog.mapper.TBlogMapper;
import com.xlblog.blog.service.TBlogService;
import com.xlblog.blog.utils.RespBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/blog")
public class TBlogController {

    @Autowired
    TBlogService tBlogService;

    RespBean respBean = RespBean.build();

    /**
     * 分页查询
     * @param current
     * @param size
     * @param published
     * @param flag
     * @param share_statement
     * @param is_delete
     * @return
     */
    @GetMapping("/getByPage")
    @ApiOperation("博客分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "published",value = "是否公开"),
            @ApiImplicitParam(name = "flag",value = "原创或转载"),
            @ApiImplicitParam(name = "share_statement",value = "草稿"),
            @ApiImplicitParam(name = "is_delete",value = "是否已删除"),
    })
    public RespBean getByPage(Long current, Long size, Boolean published,
                              String flag, Boolean share_statement, Boolean is_delete){
        return tBlogService.pageBlogs(current, size,published,flag,share_statement,is_delete);
    }

    /**
     * 前台分页
     * @param current
     * @param size
     * @param type_id
     * @param tags_id
     * @return
     */
    @GetMapping("/vuefindByPage")
    public RespBean vuefindByPage(Long current, Long size, Long type_id, Long tags_id){
        return tBlogService.vuefindByPage(current, size,type_id,tags_id);
    }

    @GetMapping("/vuefindHotBlog")
    public RespBean vuefindHotBlog(){
        return tBlogService.vuefindHotBlog();
    }

    /**
     * 根据博客标题查询
     * @param title
     * @return
     */
    @GetMapping("/getByTitle")
    @ApiOperation("通过文章标题查询")
    @ApiImplicitParam(name = "title",value = "文章的标题")
    public RespBean findByTitle(String title){
        return tBlogService.getByTitle(title);
    }

    /**
     * 保存文章和标签
     * @param params
     * @return
     */
    @PostMapping("/savaBT")
    public RespBean saveBlogTag(@RequestBody HashMap<String,Object> params) {
        RespBean check = BlogForm.check(params);
        if (check.getStatus() == 500) {
            return check;
        }else {
            return tBlogService.saveBT(params);
        }
    }

    /**
     * 暂时保存博客
     * @param params
     * @return
     */
    @PostMapping("/temporarySave")
    public RespBean temporarySave(@RequestBody HashMap<String,Object> params){
        RespBean checkTemporaryBlog = BlogForm.checkTemporaryBlog(params);
        if (checkTemporaryBlog.getStatus() == 500) {
            return checkTemporaryBlog;
        }
        else {
            return tBlogService.temporarySave(params);
        }
    }

    /**
     * 更新博客和标签
     * @param params
     * @return
     */
    @PutMapping("/updateBlog")
    public RespBean updateBlog(@RequestBody HashMap<String,Object> params){
        RespBean checkUpdateBlog = BlogForm.check(params);
        if (checkUpdateBlog.getStatus() == 500) {
            return checkUpdateBlog;
        }
        else {
            return tBlogService.updateBlog(params);
        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @DeleteMapping("/logicDeleteBlog")
    public RespBean logicDeleteBlog(String id){
        TBlog tBlog = tBlogService.getById(id);
        if (tBlog != null){
            return tBlogService.logicDeleteBlog(id);
        }else {
            respBean.setStatus(500);
            respBean.setMsg("没有数据");
            return respBean;
        }
    }

    /**
     * 删除博客和对应标签
     * @param id
     * @return
     */
    @DeleteMapping("/deleteBlog")
    public RespBean deleteBlog(String id){
        TBlog tBlog = tBlogService.getById(id);
        if (tBlog != null){
            return tBlogService.deleteBT(id);
        }else {
            respBean.setStatus(500);
            respBean.setMsg("没有数据");
            return respBean;
        }
    }

    /**
     * 还原删除的博客
     * @param id
     * @return
     */
    @GetMapping("/recoveryBlog")
    public RespBean recoveryBlog(String id){
        return tBlogService.recoveryBlog(id);
    }

    /**
     * 根据id查找博客
     * @param id
     * @return
     */
    @GetMapping("/getByBlogId")
    public RespBean getByBlogId(String id){
        return tBlogService.getByBlogId(id);
    }

    /**
     * 统计数据
     * @return
     */
    @GetMapping("/countBlog")
    public RespBean countBlog(){
        return tBlogService.countBlog();
    }




}
