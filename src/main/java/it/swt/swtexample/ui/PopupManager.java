package it.swt.swtexample.ui;

import it.swt.swtexample.utils.LockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.swt.SWT;

import org.eclipse.swt.widgets.Display;
import org.springframework.stereotype.Component;
import static it.swt.swtexample.utils.Constants.*;

import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
@RequiredArgsConstructor
public class PopupManager extends SWTUtils implements IShowPopup {

    private final  LockUtils lockUtils;

    /**
     * Mostra un popup in modo asincrono.
     *
     * @param latch Il CountDownLatch utilizzato per sincronizzare il completamento dell'operazione asincrona.
     */
    @Override
    public void showPopupAsync(CountDownLatch latch, Boolean isBlocking) {
        new Thread(() -> {
            log.info("ASYNC POPUP - BLOCKING:  (" + isBlocking + ")");
            Display display = new Display();
            createPopup(display, ASYNC_DISPLAY, display.getSystemColor(SWT.COLOR_DARK_GREEN), display.getSystemColor(SWT.COLOR_WHITE));
            display.dispose();
            if (isBlocking) {
                latch.countDown();
            }

        }).start();
    }

    /**
     * Mostra un popup in modo sincrono.
     */
    @Override
    public void showPopupSync() {
        log.info("SYNC POPUP");
        Display display = new Display();
        createPopup(display, SYNC_DISPLAY, display.getSystemColor(SWT.COLOR_DARK_GRAY), display.getSystemColor(SWT.COLOR_WHITE));
        display.dispose();
    }

    /**
     * Mostra un popup utilizzando i virtual thread.
     */
    @Override
    public void showPopupWithVirtualThreads() {
        Thread.ofVirtual().start(() -> {
            log.info("ASYNC POPUP - VIRTUAL THREAD");
            Display display = new Display();
            createPopup(display, VIRTUAL_THREAD_DISPLAY, display.getSystemColor(SWT.COLOR_DARK_BLUE), display.getSystemColor(SWT.COLOR_WHITE));
            display.dispose();

            // Segnala che il popup è stato chiuso
            lockUtils.lock.lock();

            try {
                lockUtils.isPopupClosed.set(true);
                lockUtils.popupClosed.signal();
            } finally {
                lockUtils.lock.unlock();
            }
        });


    }

    @Override
    public void showABrowser() {
        log.info("Showing popup a Browser");
        Display display = new Display();
        createABrowser(display);
        display.dispose();
    }


}

