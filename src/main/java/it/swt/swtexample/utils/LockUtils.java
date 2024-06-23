package it.swt.swtexample.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockUtils {

    public final Lock lock = new ReentrantLock();
    public final Condition popupClosed = lock.newCondition();
    public final AtomicBoolean isPopupClosed = new AtomicBoolean(false);
}
