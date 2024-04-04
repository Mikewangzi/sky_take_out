package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    List<Long> selectByDishIds(List<Long> ids);

    void insertBatch(List<SetmealDish> setmealDishes);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> selectBySetmealId(Long id);

    List<SetmealDish> selectBySeatmealIds(List<Long> ids);

    void deleteBatch(List<Long> ids);

    @Delete("delete from setmeal_dish where setmeal_id = #{id};")
    void deleteBySetmealId(Long id);
}
