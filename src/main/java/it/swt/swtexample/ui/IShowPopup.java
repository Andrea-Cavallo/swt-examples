package it.swt.swtexample.ui;

import java.util.concurrent.CountDownLatch;

public interface IShowPopup {

     void showPopupAsync(CountDownLatch latch, Boolean isBlocking) ;
     void showPopupSync() ;
     void showPopupWithVirtualThreads();
     void showABrowser();


}
