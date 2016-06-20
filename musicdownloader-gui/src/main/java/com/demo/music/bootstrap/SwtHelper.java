package com.demo.music.bootstrap;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class SwtHelper {
	
	@SuppressWarnings("unchecked")
	public static <T extends Control> T getControlById(Shell shell, String id) {
		//consoleId linkWebId destDirectoryId
		Control[] controls = shell.getChildren();
		for (Control control : controls) {
			String eleId = (String)control.getData("ID");
			if(id.equalsIgnoreCase(eleId)) {
				return (T) control;
			}
		}
		return null;
		
	}
	
	public static MessageBox createMessageBox(Shell shell, String title, String message) {
		MessageBox mb = new MessageBox(shell);
		mb.setText(title);
		mb.setMessage(message);
		mb.open();
		return mb;
	}
	
	public static Executor SwtDisplayExecutor() {
		boolean isAppleOSX = System.getProperty("os.name").contains("Mac");
		if (isAppleOSX) {
			try{
				//com.apple.concurrent.Dispatch.getInstance().getNonBlockingMainQueueExecutor().execute(threadRunner);
				Class<?> comAppleConcurrentDispatch = Class.forName("com.apple.concurrent.Dispatch");
				Method getInstance = comAppleConcurrentDispatch.getMethod("getInstance", (Class<?>[])null);
				Object dispatchInstance = getInstance.invoke(null,(Object[])null);
				Method getNonBlockingMainQueueExecutor = dispatchInstance.getClass().getMethod("getNonBlockingMainQueueExecutor", (Class<?>[])null);
				Executor executor = (Executor) getNonBlockingMainQueueExecutor.invoke(dispatchInstance, (Object[]) null);
				return executor;
			}catch(Exception ex) {
				ex.printStackTrace();
				return Executors.newSingleThreadExecutor();
			}

		} else {
			return Executors.newSingleThreadExecutor();
		}
	}
	
}
