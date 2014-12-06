package net.tapcat.notthreadsafe;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class EnsureNotMultiThreadedTest {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Test(expected = NotThreadSafeException.class)
    public void testAspectInvokedOnNotThreadSafeClass() throws Exception {
        final NotThreadSafeClass instance = new NotThreadSafeClass();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                instance.doSomething();
            }
        };

        // Execute from different thread
        executor.submit(r).get();

        // Execute from our thread
        r.run();
    }

    @Test
    public void testAspectNotInvokedOnStaticMethod() throws Exception {
        final NotThreadSafeClass instance = new NotThreadSafeClass();
        final Runnable r = new Runnable() {
            @Override
            @SuppressWarnings("static-access")
            public void run() {
                instance.doSomethingStatic();
            }
        };

        // Execute from different thread
        executor.submit(r).get();

        // Execute from our thread
        r.run();
    }

    @Test
    public void testAspectNotInvokedOnSafeClasses() throws Exception {
        final ThreadSafeClass instance = new ThreadSafeClass();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                instance.doSomething();
            }
        };

        // Execute from different thread
        executor.submit(r).get();

        // Execute from our thread
        r.run();
    }
}
