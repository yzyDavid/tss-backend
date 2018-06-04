package tss.interceptors;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;

@Aspect
@Component
public class LogInterceptor {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    private ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();

    @Pointcut("execution(* tss.controllers..*.*(..))")
    private void controllerLog() {

    }

    @Before("controllerLog()")
    private void beforeLog(JoinPoint joinPoint) {
        request.setAttribute("startTime", System.currentTimeMillis());
    }

    @After("controllerLog()")
    private void afterLog(JoinPoint joinPoint) {
    }

    @AfterReturning(value = "controllerLog()", returning = "ret")
    private void afterReturningLog(JoinPoint joinPoint, Object ret) {
        Logger logger = loggerFactory.getLogger(joinPoint.getTarget().getClass().toString());
        long duration = System.currentTimeMillis() - (long) request.getAttribute("startTime");
        logger.info("\"{} {} {}\" {} {}ms", request.getMethod(), request.getRequestURI(), request.getProtocol(),
                response.getStatus(), duration);
    }

    @AfterThrowing(value = "controllerLog()", throwing = "exception")
    private void afterThrowingLog(JoinPoint joinPoint, Throwable exception) {
    }

    private String getIpfromRequest() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }

        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }


}
