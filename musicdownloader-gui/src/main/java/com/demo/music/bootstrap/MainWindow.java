package com.demo.music.bootstrap;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.demo.music.sdo.MusicProfile;

public class MainWindow {
	/**
	 * Runs the application
	 */
	public void run() {
		final Display display = Display.getDefault();
		
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
	    		Shell shell;
				try {
					shell = (Shell)XWT.load(Resource.class.getResource("/XWT/MainWindow.xml"));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				shell.setImage(new Image(display, Resource.class.getResourceAsStream("/images/music_home.jpg")));
				handlingTextConsole((Text) XWT.findElementByName(shell, "consoleId"));
	    		
	    		BrowserHelper.initialize(display, (Browser)XWT.findElementByName(shell, "browserId"));
//	    		FacebookGate.getFacebookGate(shell);
	    		shell.pack();
	    		shell.open();
	    		
	    		MusicProfile profile = AppProfiler.load();
	    		Text webLink = (Text)XWT.findElementByName(shell, "webLinkId");
	    		Text destDir = (Text)XWT.findElementByName(shell, "destDirectoryId");
	    		Button discographyCheckBox =(Button)XWT.findElementByName(shell, "discographyId");
	    		webLink.setText(profile.getUrl());
	    		destDir.setText(profile.getDestFolder());
	    		discographyCheckBox.setSelection(profile.isDiscography());
	    		
	    		while (!shell.isDisposed()) {
	    			if (!display.readAndDispatch()) {
	    				display.sleep();
	    			}
	    		}
	    		if (!shell.isDisposed()) {
	    			shell.dispose();
	    		}
		    }
		});

		display.dispose();
		System.exit(0);
	}
	
	private void handlingTextConsole(final Text consoleId) {
		final class ConsoleStream extends FilterOutputStream {
			ConsoleStream() {
				super(new ByteArrayOutputStream());
			}

			@Override
			public void write(final byte[] b, final int off, final int len) throws IOException {
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
				    	try {
				    		consoleId.append(new String(b, off, len, "UTF-8") );
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
				    }
				});
				
			}
		}
		
		final PrintStream oldOut = System.out;
		try {
			PrintStream p = new PrintStream(new ConsoleStream(), true, "UTF-8");
			System.setOut(p);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		consoleId.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				System.setOut(oldOut);
			}
		});
	}
	
	public void widgetSelected(Event event) {
		Shell shell = XWT.findShell(event.widget);
		DirectoryDialog dlg = new DirectoryDialog(shell);
		Text text = (Text)XWT.findElementByName(shell, "destDirectoryId");
		dlg.setFilterPath(text.getText());
		dlg.setText("Choose target directory...");
		dlg.setMessage("Select a directory");
		String dir = dlg.open();
		if (dir != null) {
			text.setText(dir);
		}
	}
	
	public void createAlbumEvent(Event event) {
		try {
			MainWindowController.get(XWT.findShell(event.widget)).createAlbum();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static synchronized void main(String[] args) {
		new MainWindow().run();
	}
}

