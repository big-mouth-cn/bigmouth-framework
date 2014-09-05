package org.bigmouth.framework.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.bigmouth.framework.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class ZipUtils {
    
    private static Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);
    private static final int BUFF = 2048;

    /**
     * 解压指定文件到目标
     * 
     * @param zip
     * @param dest
     * @throws ZipUncompressException 
     */
    public static void uncompress(File zip, String dest) throws ZipUncompressException {
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Uncompress file " + zip + " to " + dest);
        long start = System.currentTimeMillis();
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zip);
        }
        catch (IOException e) {
            throw new ZipUncompressException(e);
        }
        for (Enumeration<?> entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
            Object entry = entries.nextElement();
            if (entry instanceof ZipEntry) {
                ZipEntry zipEntry = (ZipEntry) entry;
                if (LOGGER.isDebugEnabled())
                    LOGGER.debug(" - " + zipEntry.getName());
                File file = new File(PathUtils.appendEndFileSeparator(dest) + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                }
                else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try {
                        write(zipFile, zipEntry, file);
                    }
                    catch (ZipException e) {
                        throw new ZipUncompressException(e);
                    }
                    catch (FileNotFoundException e) {
                        throw new ZipUncompressException(e);
                    }
                    catch (IOException e) {
                        throw new ZipUncompressException(e);
                    }
                }
            }
        }
        if (LOGGER.isInfoEnabled())
            LOGGER.info("Uncompress file " + zip + " to " + dest + " Successful! at " + (System.currentTimeMillis() - start) + " ms.");
    }

    private static void write(ZipFile zipFile, ZipEntry zipEntry, File file) 
            throws IOException, ZipException, FileNotFoundException {
        InputStream inStream = null;
        FileOutputStream output = null;
        try {
            inStream = zipFile.getInputStream(zipEntry);
            output = new FileOutputStream(file);
            byte[] bytes = new byte[BUFF];
            int len;
            while ((len = inStream.read(bytes)) != -1) {
                output.write(bytes, 0, len);
            }
        }
        finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(inStream);
        }
    }
    
    /**
     * 压缩指定文件或文件夹
     * 
     * @param inputFile
     * @param zipFileName
     * @throws ZipCompressException 
     */
    public static void compress(File inputFile, String zipFileName) throws ZipCompressException {
        Preconditions.checkNotNull(inputFile);
        Preconditions.checkArgument(StringUtils.isNotBlank(zipFileName), "zipFileName cannot be blank.");
        FileOutputStream fileOutput = null;
        ZipOutputStream zipOutput = null;
        try {
            fileOutput = new FileOutputStream(new String(zipFileName.getBytes("UTF-8")));
            zipOutput = new ZipOutputStream(fileOutput);
            long start = System.currentTimeMillis();
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Compress " + inputFile);
            compress(zipOutput, inputFile, "");
            
            if (LOGGER.isInfoEnabled())
                LOGGER.info("Compress Successful! at " + (System.currentTimeMillis() - start) + " ms");
        }
        catch (IOException e) {
            throw new ZipCompressException(e);
        }
        finally {
            IOUtils.closeQuietly(zipOutput);
            IOUtils.closeQuietly(fileOutput);
        }
    }

    private static void compress(ZipOutputStream zOut, File file, String base) throws IOException {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            zOut.putNextEntry(new ZipEntry(base + "/"));
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(" + " + base + "/");

            base = (StringUtils.isBlank(base) ? "" : base + "/");

            for (int i = 0; i < listFiles.length; i++) {
                compress(zOut, listFiles[i], base + listFiles[i].getName());
            }
        }
        else {
            if (StringUtils.isBlank(base)) {
                base = file.getName();
            }
            zOut.putNextEntry(new ZipEntry(base));
            if (LOGGER.isDebugEnabled())
                LOGGER.debug(" + " + base);

            writeFile(zOut, file);
        }
    }

    private static void writeFile(ZipOutputStream zOut, File file) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[BUFF];
            int len;
            while ((len = in.read(bytes)) != -1) {
                zOut.write(bytes, 0, len);
            }
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static void main(String[] args) throws IOException, ZipCompressException, ZipUncompressException {
//        compress(new File("C:\\Users\\allen.hu\\Desktop\\mpt-yun-1.0.6"), "C:\\Users\\allen.hu\\Desktop\\ziptest.zip");
        uncompress(new File("C:\\Users\\allen.hu\\Desktop\\ziptest.zip"), "C:\\Users\\allen.hu\\Desktop\\uncompress\\");
    }
}
