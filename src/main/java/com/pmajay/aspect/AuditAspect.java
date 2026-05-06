package com.pmajay.aspect;

import com.pmajay.model.AuditLog;
import com.pmajay.repository.AuditLogRepository;
import com.pmajay.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @AfterReturning(
        pointcut = "execution(* com.pmajay.controller.*.create*(..)) || " +
                   "execution(* com.pmajay.controller.*.update*(..)) || " +
                   "execution(* com.pmajay.controller.*.delete*(..))",
        returning = "result"
    )
    public void auditAction(JoinPoint joinPoint, Object result) {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return;

            HttpServletRequest request = attrs.getRequest();
            String authHeader = request.getHeader("Authorization");

            Long userId = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    userId = jwtUtil.extractUserId(token);
                } catch (Exception e) {
                    // Token parsing failed, userId remains null
                }
            }

            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String entityType = className.replace("Controller", "");

            String action;
            if (methodName.startsWith("create")) {
                action = "CREATE";
            } else if (methodName.startsWith("update")) {
                action = "UPDATE";
            } else if (methodName.startsWith("delete")) {
                action = "DELETE";
            } else {
                action = methodName.toUpperCase();
            }

            String ipAddress = request.getRemoteAddr();

            AuditLog log = AuditLog.builder()
                    .userId(userId)
                    .action(action + " " + entityType)
                    .entityType(entityType)
                    .ipAddress(ipAddress)
                    .build();

            auditLogRepository.save(log);
        } catch (Exception e) {
            // Audit logging should not break the main flow
            System.err.println("Audit logging error: " + e.getMessage());
        }
    }
}
