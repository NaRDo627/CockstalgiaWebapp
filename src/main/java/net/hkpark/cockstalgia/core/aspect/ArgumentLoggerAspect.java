package net.hkpark.cockstalgia.core.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.core.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ArgumentLoggerAspect {
    @Pointcut("@annotation(net.hkpark.cockstalgia.core.annotation.PrintArguments)")
    public void argumentLoggerTarget() { }

    @Before("argumentLoggerTarget()")
    public void logArgument(JoinPoint jp) {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            try {
                log.debug(JsonUtil.convertJsonString(arg));
            } catch (JsonProcessingException e) {
                log.warn("JsonProcessing failed : print with toString() instead");
                log.debug(arg.toString());
            }
        }
    }
}
