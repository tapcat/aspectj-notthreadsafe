package net.tapcat.sample;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Stub service annotated with {@link javax.annotation.concurrent.NotThreadSafe}.
 * Semantically, you must not access it's methods from different threads.
 *
 * @author Maksim Zakharov
 * @since 1.0
 */
@NotThreadSafe
public class NotThreadSafeService {

    public void doSomething() {

    }
}
