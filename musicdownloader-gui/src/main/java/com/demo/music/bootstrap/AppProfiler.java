package com.demo.music.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang3.SerializationUtils;

import com.demo.music.sdo.MusicProfile;

public class AppProfiler {
	private static final String PROFILER="md.bin";

	public static String getAppProfile() {
		String home = System.getProperty("user.home") + "/.MusicDownloader";
		File f = new File(home);
		if (!f.exists()) {
			f.mkdirs();
		}
		return home;
	}
	public static void persistProfile(MusicProfile musicProfile) {
		try {
			FileOutputStream fout = new FileOutputStream(new File(getAppProfile(), PROFILER));
			SerializationUtils.serialize(musicProfile, fout);
			fout.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MusicProfile load() {
		FileInputStream fin;
		try {
			fin = new FileInputStream(new File(getAppProfile(), PROFILER));
			return  (MusicProfile)SerializationUtils.deserialize(fin);

		} catch (Exception e) {
			MusicProfile m = new MusicProfile();
			persistProfile(m);
			return m;
		}
	}
}
