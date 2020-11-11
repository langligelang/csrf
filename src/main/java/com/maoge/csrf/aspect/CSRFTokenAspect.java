package com.maoge.csrf.aspect;

import com.maoge.csrf.config.Constant;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class CSRFTokenAspect {


    @Pointcut("@annotation(com.maoge.csrf.annotation.CSRFToken)")
    public void annotationPointcut() {

    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] params = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (null == params || params.length == 0){
            String mes = "Using CSRF Token annotation, the token parameter is not passed, and the parameter is not valid.";
            throw new Exception(mes);
        }
        for(int i=0;i<params.length;i++){
            if(params[i].trim().equals("csrfToken")){
                String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
                List<String> csrfTokens = Constant.concurrentHashMap.get(sessionId);
                if(csrfTokens == null || !csrfTokens.contains(args[i])){
                    throw new RuntimeException("csrf token已失效或不正确");
                }
                csrfTokens.remove(args[i]);
                if(csrfTokens.size() ==0 ){
                    Constant.concurrentHashMap.remove(sessionId);
                }
                break;
            }
        }

        return joinPoint.proceed();
    }

    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {

    }

    private void checkToken(String token) {

    }

}
