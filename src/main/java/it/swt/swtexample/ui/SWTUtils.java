package it.swt.swtexample.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


public class SWTUtils {

    /**
     * Crea e visualizza un popup.
     *
     * @param display Il display SWT.
     * @param title Il titolo del popup.
     * @param backgroundColor Il colore di sfondo del popup.
     * @param buttonColor Il colore del pulsante nel popup.
     */
     void createPopup(Display display, String title, Color backgroundColor, Color buttonColor) {
        Shell shell = createShell(display, title, backgroundColor);
        shell.open();
        runEventLoop(display, shell);
    }

    /**
     * Crea un pulsante nel popup.
     *
     * @param shell La shell che contiene il pulsante.
     * @param text Il testo del pulsante.
     * @param backgroundColor Il colore di sfondo del pulsante.
     * @return Il pulsante creato.
     */
     Button createButton(Shell shell, String text, Color backgroundColor) {
        Button button = new Button(shell, SWT.PUSH);
        button.setText(text);
        button.setBackground(backgroundColor);
        button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
        return button;
    }

    /**
     * Esegue il ciclo degli eventi per la shell.
     *
     * @param display Il display SWT.
     * @param shell La shell per cui eseguire il ciclo degli eventi.
     */
     void runEventLoop(Display display, Shell shell) {
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Crea una shell per il popup.
     *
     * @param display Il display SWT.
     * @param title Il titolo della shell.
     * @param backgroundColor Il colore di sfondo della shell.
     * @return La shell creata.
     */
     Shell createShell(Display display, String title, Color backgroundColor) {
        Shell shell = new Shell(display);
        shell.setText(title);
        shell.setSize(1000, 800);
        shell.setBackground(backgroundColor);
        shell.setLayout(new GridLayout(1, false));
        return shell;
    }



    /**
     * Creates and displays a browser.
     *
     * @param display The SWT display.
     */
    void createABrowser(Display display){

        final Shell shell = new Shell(display);
        shell.setText("GOOOOOOOOOOOOOOOOOOOOOOOOOOGLE");
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 3;
        shell.setLayout(gridLayout);
        ToolBar toolbar = new ToolBar(shell, SWT.NONE);
        ToolItem itemBack = new ToolItem(toolbar, SWT.PUSH);
        itemBack.setText("Back");
        ToolItem itemForward = new ToolItem(toolbar, SWT.PUSH);
        itemForward.setText("Forward");
        ToolItem itemStop = new ToolItem(toolbar, SWT.PUSH);
        itemStop.setText("Stop");
        ToolItem itemRefresh = new ToolItem(toolbar, SWT.PUSH);
        itemRefresh.setText("Refresh");
        ToolItem itemGo = new ToolItem(toolbar, SWT.PUSH);
        itemGo.setText("Go");

        GridData data = new GridData();
        data.horizontalSpan = 3;
        toolbar.setLayoutData(data);

        Label labelAddress = new Label(shell, SWT.NONE);
        labelAddress.setText("Address");

        final Text location = new Text(shell, SWT.BORDER);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        data.grabExcessHorizontalSpace = true;
        location.setLayoutData(data);

        final Browser browser;
        try {
            browser = new Browser(shell, SWT.NONE);
        } catch (SWTError e) {
            System.out.println("Could not instantiate Browser: " + e.getMessage());
            display.dispose();
            return;
        }
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.horizontalSpan = 3;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        browser.setLayoutData(data);

        final Label status = new Label(shell, SWT.NONE);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        status.setLayoutData(data);

        final ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
        data = new GridData();
        data.horizontalAlignment = GridData.END;
        progressBar.setLayoutData(data);

        /* event handling */
        Listener listener = event -> {
            ToolItem item = (ToolItem) event.widget;
            String string = item.getText();
            switch (string) {
                case "Back" -> browser.back();
                case "Forward" -> browser.forward();
                case "Stop" -> browser.stop();
                case "Refresh" -> browser.refresh();
                case "Go" -> browser.setUrl(location.getText());
            }
        };
        browser.addProgressListener(new ProgressListener() {
            @Override
            public void changed(ProgressEvent event) {
                if (event.total == 0) return;
                int ratio = event.current * 100 / event.total;
                progressBar.setSelection(ratio);
            }
            @Override
            public void completed(ProgressEvent event) {
                progressBar.setSelection(0);
            }
        });
        browser.addStatusTextListener(event -> status.setText(event.text));
        browser.addLocationListener(LocationListener.changedAdapter(event -> {
                    if (event.top) location.setText(event.location);
                }
        ));
        itemBack.addListener(SWT.Selection, listener);
        itemForward.addListener(SWT.Selection, listener);
        itemStop.addListener(SWT.Selection, listener);
        itemRefresh.addListener(SWT.Selection, listener);
        itemGo.addListener(SWT.Selection, listener);
        location.addListener(SWT.DefaultSelection, e -> browser.setUrl(location.getText()));

        shell.open();
        browser.setUrl("https://google.com");
        runEventLoop(display, shell);
   }








}
