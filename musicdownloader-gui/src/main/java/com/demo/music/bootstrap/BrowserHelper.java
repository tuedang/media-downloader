package com.demo.music.bootstrap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BrowserHelper {
	  public static void initialize(final Display display, Browser browser) {
		    browser.addOpenWindowListener(new OpenWindowListener() {
		      public void open(WindowEvent event) {
		        Shell shell = new Shell(display);
		        shell.setText("New Window");
		        shell.setLayout(new FillLayout());
		        Browser browser = new Browser(shell, SWT.NONE);
		        initialize(display, browser);
		        event.browser = browser;
		      }
		    });
		    
		    browser.addVisibilityWindowListener(new VisibilityWindowListener() {
		      public void hide(WindowEvent event) {
		        Browser browser = (Browser) event.widget;
		        Shell shell = browser.getShell();
		        shell.setVisible(false);
		      }
		      public void show(WindowEvent event) {
		        Browser browser = (Browser) event.widget;
		        Shell shell = browser.getShell();
		        if (event.location != null)
		          shell.setLocation(event.location);
		        if (event.size != null) {
		          Point size = event.size;
		          shell.setSize(shell.computeSize(size.x, size.y));
		        }
		        shell.open();
		      }
		    });
		    
		    browser.addCloseWindowListener(new CloseWindowListener() {
		      public void close(WindowEvent event) {
		        Browser browser = (Browser) event.widget;
		        Shell shell = browser.getShell();
		        shell.close();

		      }
		    });
		    browser.addDisposeListener(new DisposeListener() {
				
				@Override
				public void widgetDisposed(DisposeEvent e) {
					Browser browser = (Browser) e.widget;
			        Shell shell = browser.getShell();
			        shell.dispose();
				}
			});
		  }

}
