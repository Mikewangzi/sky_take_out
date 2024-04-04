package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);


    @Select("select count(id) from dish where category_id = #{category_id}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish selectById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    void deleteBatch(List<Long> ids);

    @Select("select a.* from dish a left join setmeal_dish s on a.id=s.dish_id" +
            " where s.setmeal_id=#{id}")
    List<Dish> selectBySetmealId(Long id);

    List<Dish> list(Dish dish);
}
