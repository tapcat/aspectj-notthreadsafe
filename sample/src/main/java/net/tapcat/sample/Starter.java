package net.tapcat.sample;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        new Starter().doSomethingDangerous();
    }

    private void doSomethingDangerous() throws ExecutionException, InterruptedException {
        final NotThreadSafeService service = new NotThreadSafeService();
        final Runnable r = new RunnableService(service);
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("Going to run @NotThreadSafe service from different threads");
        executor.submit(r).get();
        executor.shutdown();
        r.run();
        System.out.println("Successfully ran @NotThreadSafe service from different threads. Aspect not applied?");
    }

    private static class RunnableService implements Runnable {

        private final NotThreadSafeService service;

        private RunnableService(NotThreadSafeService service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.doSomething();
        }
    }
}
