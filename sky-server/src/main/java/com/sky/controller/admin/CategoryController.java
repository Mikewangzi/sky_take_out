package com.sky.controller.admin;


import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询:{}",categoryPageQueryDTO);
        PageResult page = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(page);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("启用或禁用")
    public Result startOrStop(@PathVariable Integer status,Long id){
        categoryService.startOrStop(status,id);
        return Result.success();
    }
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result delete(Long id){
        categoryService.delete(id);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> getByType(Integer type){
        List<Category> category = categoryService.getByType(type);
        return Result.success(category);
    }
}
