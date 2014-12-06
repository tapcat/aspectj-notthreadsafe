package net.tapcat.notthreadsafe;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * @author Maksim Zakharov
 * @since 1.0
 */
@NotThreadSafe
public class NotThreadSafeClass {

    public NotThreadSafeClass() {

    }

    public void doSomething() {

    }

    public static void doSomethingStatic() {

    }
}
