package com.github.jsoncat.core.aop.intercept;

import com.github.jsoncat.annotation.validation.Validated;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @Description Bean验证拦截器
 * @Author Goodenough
 * @Date 2021/3/6 14:05
 */
public class BeanValidationInterceptor extends Interceptor{

    private final Validator validator;

    public BeanValidationInterceptor(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    // bean 是否支持验证
    @Override
    public boolean supports(Object bean) {
        return bean != null && bean.getClass().isAnnotationPresent(Validated.class);
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        return null;
    }
}
