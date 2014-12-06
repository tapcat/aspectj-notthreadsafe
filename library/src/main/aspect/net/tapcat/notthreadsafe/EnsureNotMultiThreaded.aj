package net.tapcat.notthreadsafe;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@Aspect
public class EnsureNotMultiThreaded {

    private static final Logger LOG = LoggerFactory.getLogger(EnsureNotMultiThreaded.class);

    private final Map<Object, Thread> instanceThreads = new ConcurrentHashMap<Object, Thread>();

    @Pointcut("within(@javax.annotation.concurrent.NotThreadSafe *)")
    void annotatedWithNotThreadSafe() {

    }

    @Pointcut("execution(public !static * *(..))")
    void anyPublicInstanceMethod() {

    }

    @Before("annotatedWithNotThreadSafe() && anyPublicInstanceMethod()")
    public void beforeAnyMethodOnNotThreadSafeClass(JoinPoint joinPoint) throws Exception {
        final Object instance = joinPoint.getThis();
        final Thread currentThread = Thread.currentThread();
        final Thread previousThread = instanceThreads.put(instance, currentThread);
        if (previousThread != null) {
            String message = String.format(
                    "Object \"%s\" accessed from thread \"%s\", while previously accessed from thread \"%s\"",
                    instance, currentThread, previousThread);

            LOG.error(message);
            throw new NotThreadSafeException(message);
        }
    }
}
