package com.demo.parser.common;

import org.apache.commons.io.FilenameUtils;

import java.text.DecimalFormat;

public class FileUtils {
    /**
     * Instances should NOT be constructed in standard programming.
     */
    public FileUtils() {
        super();
    }

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;
    
    public static String byteCountToDisplaySize(long size) {
        String displaySize;
        DecimalFormat dec = new DecimalFormat("###.##");

        if (size / ONE_GB > 0) {
            displaySize = dec.format((double)size / ONE_GB) + "GB";
        } else if (size / ONE_MB > 0) {
            displaySize = dec.format((double)size / ONE_MB) + "MB";
        } else if (size / ONE_KB > 0) {
            displaySize = dec.format((double)size / ONE_KB) + "KB";
        } else {
            displaySize = String.valueOf(size) + "bytes";
        }
        return displaySize;
    }

    public static String getExt(String url, String defaultValue) {
        String ext = FilenameUtils.getExtension(url);
        if (ext == null || ext.isEmpty() || ext.length() > 4) {
            ext = defaultValue;
        }
        return "." + ext;
    }

}
