package it.swt.swtexample;

import it.swt.swtexample.ui.IShowPopup;
import it.swt.swtexample.utils.LockUtils;
import lombok.RequiredArgsConstructor;
import static it.swt.swtexample.utils.Constants.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j

/*
 * @Author AndreaCavallo
 *
 * Esempi pratici con SWT.
 *
 */
public class SwtSpringBootApplication {



	private final IShowPopup iShowPopup;
	private final LockUtils lockUtils;

	public static void main(String[] args) {
		SpringApplication.run(SwtSpringBootApplication.class, args);
	}

	/**
	 * Executes the code when the application starts.
	 *
	 * @return A CommandLineRunner bean that runs the specified code.
	 */
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			run1();
			run2();
			run3();
			//run4();

		};
	}


	/**
	 * Runs the code for the first run of the application.
	 *
	 * This method displays a series of log messages and calls the {@link IShowPopup#showPopupAsync(CountDownLatch, Boolean)}
	 * method to show a popup asynchronously. It uses a {@link CountDownLatch} to block the execution until the popup is
	 * shown. After the popup is shown, it calls the {@link IShowPopup#showPopupSync()} method to show the popup
	 * synchronously.
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting for the countdown latch
	 */
	private void run1() throws InterruptedException {

		log.info(DIVIDER);
		log.info(FIRST_RUN);
		log.info(LOCK_ASYNC_DISPLAY_WITH_COUNTDOWN_LATCH);
		CountDownLatch latch = new CountDownLatch(1);
		iShowPopup.showPopupAsync(latch,true);
		latch.await();
		iShowPopup.showPopupSync();

	}

	/**
	 * Runs the code for the second run of the application.
	 *
	 * This method displays a series of log messages and calls the {@link IShowPopup#showPopupWithVirtualThreads()} method
	 * to show a popup asynchronously with virtual threads. Then it uses a {@link ReentrantLock} to lock the thread until
	 * the popup is closed. It waits for the {@link LockUtils#popupClosed} condition to be signaled by another thread.
	 * After the condition is met, it unlocks the lock and calls the {@link IShowPopup#showPopupSync()} method to show
	 * the popup synchronously.
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting for the popup to be closed
	 */
	private void run2() throws InterruptedException {

		log.info(DIVIDER);
		log.info(SECOND_RUN);
		log.info(LOCK_ASYNC_DISPLAY_WITH_REETRANTLOCK);
		iShowPopup.showPopupWithVirtualThreads();
		//blocca thread finchè non viene chiuso..
		lockUtils.lock.lock();
		try {
			while (!lockUtils.isPopupClosed.get())
				{
				 lockUtils.popupClosed.await();
				}
		    }
		finally
		{
			lockUtils.lock.unlock();
		}
		iShowPopup.showPopupSync();

	}

	/**
	 * This method runs the third run without blocking the AsyncDisplay.
	 *
	 * @throws InterruptedException If the thread is interrupted while waiting.
	 */
	private void run3() throws InterruptedException {
		log.info(DIVIDER);
		log.info(THIRD_RUN);
		log.info(ASYNC_DISPLAY_NOT_LOCKED);
		iShowPopup.showPopupAsync(null,false);
		iShowPopup.showPopupSync();

	}

	/**
	 * This method runs the 4 run to launch a browser
	 *
	 * @throws InterruptedException If the thread is interrupted while waiting.
	 */
	private void run4() throws InterruptedException {
		log.info(DIVIDER);
		log.info(BROWSER_RUN);
		iShowPopup.showABrowser();

	}

}
