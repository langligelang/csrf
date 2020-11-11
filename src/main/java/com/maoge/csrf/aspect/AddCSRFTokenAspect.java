package com.maoge.csrf.aspect;

import com.maoge.csrf.config.Constant;
import com.maoge.csrf.utils.CSRFToken;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
@SuppressWarnings({"unused"})
public class AddCSRFTokenAspect {




    @Pointcut("@annotation(com.maoge.csrf.annotation.AddCSRFToken)")
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
            if(params[i].trim().equals("model")) {
                String csrfToken = new CSRFToken().getToken();
                String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
                List<String> csrfTokens = Constant.concurrentHashMap.get(sessionId);
                if (null == csrfTokens) {
                    ArrayList<String> list = new ArrayList<>();
                    List<String> sycList = Collections.synchronizedList(list);
                    csrfTokens = sycList;
                }
                csrfTokens.add(csrfToken);
                Constant.concurrentHashMap.put(SecurityUtils.getSubject().getSession().getId().toString(), csrfTokens);
                Model model = (Model)args[i];
                model.addAttribute("csrfToken", csrfToken);
                break;
            }
        }

        return joinPoint.proceed();
    }

    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     * @param joinPoint
     */
    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {

    }

    private void checkToken(String token) {

    }

}
