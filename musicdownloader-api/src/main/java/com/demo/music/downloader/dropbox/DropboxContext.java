package com.demo.music.downloader.dropbox;

import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;

public class DropboxContext {
    private static final String APP_KEY = "bbt6947mmig5lq3";
    private static final String APP_SECRET = "w8bx6y0v5h5r5ck";
    private static final String KEY_TOKEN ="pnmneyrohjxqxmy";
    private static final String SECRET_TOKEN="9xepglc5g7xf351";
    
    private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
    private static DropboxAPI<WebAuthSession> mDBApi;
    
    public static DropboxAPI<WebAuthSession> getAPI() {
    	if(mDBApi==null) {
            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
            WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
            session.setAccessTokenPair(new AccessTokenPair(KEY_TOKEN, SECRET_TOKEN));
            
            mDBApi = new DropboxAPI<WebAuthSession>(session);
    	}

        return mDBApi;
	}
    
    public static boolean existed(String path) {
    	DropboxAPI<WebAuthSession> mDBApi = getAPI();
    	try {
			List<Entry> entries = mDBApi.search(FilenameUtils.getFullPath(path), FilenameUtils.getName(path), 10, false);
			for (Entry entry : entries) {
				System.out.println(entry.fileName());
			}
			if(entries.isEmpty()) {
				return false;
			}
			return true;
		} catch (DropboxException e) {
			throw new RuntimeException(e);
		}
    }

}
