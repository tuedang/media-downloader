package com.demo.music.bootstrap;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.e4.xwt.XWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.demo.music.downloader.DownloadCallback;
import com.demo.music.downloader.MusicDownloadBrokerHandler;
import com.demo.music.downloader.Status;
import com.demo.music.downloader.StatusType;
import com.demo.music.sdo.MusicProfile;

public class MainWindowController {
	enum MusicSource {NCT, ZING}
	
	private static MainWindowController mainWindowController;
	private Shell shell;

	//control
	private ProgressBar progressBar;
	private Text webLink;
	private Text destDir;
	private Button discographyCheckBox;
	
	private MainWindowController(Shell shell) {
		this.shell =shell;
		this.progressBar=(ProgressBar)XWT.findElementByName(shell, "progressBarId");
		this.webLink = (Text)XWT.findElementByName(shell, "webLinkId");
		this.destDir = (Text)XWT.findElementByName(shell, "destDirectoryId");
		this.discographyCheckBox =(Button)XWT.findElementByName(shell, "discographyId");
	}
	public static MainWindowController get(Shell shell) {
		if(mainWindowController==null) {
			mainWindowController = new MainWindowController(shell);
		}
		return mainWindowController;
	}
	
	private MusicSource getMusicSource(String url) {
		if(url.indexOf("nhaccuatui") !=-1) {
			return MusicSource.NCT;
		} else if(url.indexOf("mp3.zing.vn")!=-1) {
			return MusicSource.ZING;
		}
		return null;
	}
	
	private boolean validate() {
		String url = webLink.getText();
		String dest = destDir.getText();
		
		//validation
		if(url.isEmpty() || dest.isEmpty()) {
			SwtHelper.createMessageBox(shell, Configuration.URL_DEST_REQUIRED_TITLE, Configuration.URL_DEST_REQUIRED_CONTENT);
			return false;
		}
		if(getMusicSource(url)==null) {
			SwtHelper.createMessageBox(shell, Configuration.WRONG_SITE_TITLE, Configuration.WRONG_SITE_CONTENT);
			return false;
		}
		progressBar.setState(SWT.NORMAL);
		return true;
	}
	

	public void createAlbum() throws IllegalStateException, IOException, InterruptedException, ExecutionException {
		if(!validate()) {
			return;
		}

		
		String url = webLink.getText();
		String dest = destDir.getText();
		boolean discography = discographyCheckBox.getSelection();
		AppProfiler.persistProfile(new MusicProfile(url, dest, discography));
		
//		FacebookGate fg = FacebookGate.getFacebookGate(shell);
//		if(!fg.hasLiked()) {
//			if(getMusicSource(url)==MusicSource.ZING) {
//				SwtHelper.createMessageBox(shell, "Like on Facebook", "Please like this page to download from Zing");
//			}
//			if(discography) {
//				SwtHelper.createMessageBox(shell, "Like on Facebook", "Please like this page to use discography feature");
//				return;
//			}
//			
//		}
		
		MusicDownloadBrokerHandler musicDownloadBrokerHandler = new MusicDownloadBrokerHandler(url, dest, discography, new DownloadCallback() {
			@Override
			public void updateStatus(final Status status) {
				
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
				    	StatusType st = status.getStatusType();
						switch (st) {
						case START: progressBar.setSelection(2); break;
						case PARSING: progressBar.setSelection(5);break;
						case DOWNLOAD_IMAGE: progressBar.setSelection(10);break;
						case DOWNLOAD_SOUNDTRACK: {
							int amount = (status.getCurrentTrack() * 80)/status.getTotalTrack();
							progressBar.setSelection(20+amount);break;
						}
						case FINISH: progressBar.setSelection(100);break;
						default:
							break;
						}
				    }
				});
				
			}
		});
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(musicDownloadBrokerHandler);

	}

}
