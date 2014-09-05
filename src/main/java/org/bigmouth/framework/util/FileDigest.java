package org.bigmouth.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.io.IOUtils;

public final class FileDigest {

    private FileDigest() {
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        if (!file.exists())
            return null;
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
        }
        catch (Exception e) {
            return null;
        }
        finally {
            IOUtils.closeQuietly(in);
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
