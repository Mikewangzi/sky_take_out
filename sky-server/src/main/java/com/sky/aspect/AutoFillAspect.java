package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..))" +
            "&& @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");
        //获取方法上的注解类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);

        OperationType operationType = autoFill.value();
        //获取到当前被拦截到的方法参数-实体对象
        Object[] args = joinPoint.getArgs();

        if (args == null && args.length == 0) {
            return;
        }

        Object entity = args[0];
        //准备赋值的参数
        LocalDateTime localDateTime = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        //进行相应参数的赋值
        if (entity == OperationType.UPDATE) {
            try {
                Method setUpdateTime= entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                setUpdateTime.invoke(entity,localDateTime);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (entity == OperationType.INSERT) {

            try {
                Method setUpdateTime= entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setCreateTime= entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);

                setUpdateTime.invoke(entity,localDateTime);
                setCreateTime.invoke(entity,localDateTime);
                setCreateUser.invoke(entity,id);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
