package com.demo.music.bootstrap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FacebookGate {
	
	private static FacebookGate instance;
	private static Shell shell;
	public static FacebookGate getFacebookGate(Shell shellx) {
		if(shell==null || shell.isDisposed()) {
			shell = new Shell(shellx);
			
			shell.pack();
			shell.open();
			shell.setVisible(false);
			shell.setText("Facebook request");
			shell.setLayout(new GridLayout(1, true));
		}
		if(instance==null) {
			instance = new FacebookGate();
		}

		return instance;
	}
	
	boolean facebookLogin = false;
	boolean facebookLiked=false;
	boolean proceedComplete=false;
	
	public synchronized boolean hasLogin() {
		if(proceedComplete && facebookLogin) {
			return facebookLogin;
		}
		
		GridData dataBrowser = new GridData(GridData.FILL_BOTH);
		dataBrowser.widthHint = 100;
		dataBrowser.heightHint = 300;
		dataBrowser.horizontalSpan = 3;
		final Browser browser = new Browser(shell, SWT.NONE);
		browser.setLayoutData(dataBrowser);
		
		
		browser.setUrl(Configuration.LOGIN_URL);
		browser.addProgressListener(new ProgressListener() {
			@Override
			public void completed(ProgressEvent event) {
				if(browser.getText().contains(Configuration.LOGIN_STRING)) {
					browser.removeProgressListener(this);
					browser.dispose();
					facebookLogin=true;
				} else {
					facebookLogin=false;
				}
				shell.close();
				proceedComplete=true;
			}
			@Override
			public void changed(ProgressEvent event) {
				
			}
		});
		
		Display d = shell.getDisplay();
	    while (!shell.isDisposed()) {
	      if (!d.readAndDispatch())
	    	  d.sleep();
	    }
		return facebookLogin;
	}
	public synchronized boolean hasLiked() {
		if(proceedComplete && facebookLiked) {
			return facebookLiked;
		}
		
		GridData dataBrowser = new GridData(GridData.FILL_BOTH);
		dataBrowser.widthHint = 100;
		dataBrowser.heightHint = 300;
		dataBrowser.horizontalSpan = 3;
		final Browser browser = new Browser(shell, SWT.NONE);
		browser.setLayoutData(dataBrowser);
		
		
		browser.setUrl(Configuration.LIKE_BOX_URL);
		browser.addProgressListener(new ProgressListener() {
			@Override
			public void completed(ProgressEvent event) {
				if(browser.getText().contains(Configuration.LIKED_STRING)) {
					browser.removeProgressListener(this);
					browser.dispose();
					facebookLogin=true;
					facebookLiked=true;
					System.out.println(Configuration.LIKE_MESSAGE);
				} else {
					facebookLiked=false;
					System.out.println(Configuration.NOT_LIKE_MESSAGE);
				}
				shell.close();
				proceedComplete=true;
			}
			@Override
			public void changed(ProgressEvent event) {
				
			}
		});
		
		Display d = shell.getDisplay();
	    while (!shell.isDisposed()) {
	      if (!d.readAndDispatch())
	    	  d.sleep();
	    }
		return facebookLiked;
	}
	
}
