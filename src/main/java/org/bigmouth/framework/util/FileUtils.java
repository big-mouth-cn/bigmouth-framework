package org.bigmouth.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class FileUtils {

    protected static final Logger logger = Logger.getLogger(FileUtils.class);

    static final int BUFFER = 2048;

    // 限制实例化
    private FileUtils() {
    }

    /**
     * 批量删除一个目录下的所有文件，包括文件夹
     * 
     * @param dir 目录
     */
    public static void batchDelete(File dir) {
        if (isDirectory(dir)) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    batchDelete(file);
                }
                file.delete();
            }
        }
    }

    /**
     * 批量重命名文件夹下的文件，包括文件夹
     * 
     * @param dir 目录
     * @param prefix 重命名的后文件的前缀
     * @param find 查找
     * @param replace 替换为
     * @param suffix 重命名后文件的后缀
     * @return 被重命名过的文件
     */
    public static File[] batchRename(File dir, String prefix, String find, String replace, String suffix) {
        List<File> ls = new ArrayList<File>();
        if (isDirectory(dir)) {
            File[] files = dir.listFiles();
            for (File file : files) {
                String oldName = file.getName();
                String newName = prefix + oldName.replaceAll(find, replace) + suffix;
                if (!newName.equals(oldName)) {
                    File dest = new File(file.getParent() + File.separator + newName);
                    if (file.renameTo(dest)) {
                        ls.add(dest);
                    }
                }
            }
        }
        return ls.toArray(new File[ls.size()]);
    }

    /**
     * 关闭输入流
     * 
     * @param inputStream 文件输入流
     */
    public static void close(FileInputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                logger.error("close FileInputStream fail", e);
            }
        }
    }

    /**
     * 关闭输出流
     * 
     * @param outputStream 文件输出流
     */
    public static void close(FileOutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            }
            catch (IOException e) {
                logger.error("close FileInputStream fail", e);
            }
        }
    }

    /**
     * 关闭BufferedReader
     * 
     * @param br BufferedReader
     */
    public static void close(BufferedReader br) {
        if (br != null) {
            try {
                br.close();
            }
            catch (IOException e) {
                logger.error("close BufferedReader fail", e);
            }
        }
    }

    /**
     * 关闭BufferedWriter
     * 
     * @param bw BufferedWriter
     */
    public static void close(BufferedWriter bw) {
        if (bw != null) {
            try {
                bw.close();
            }
            catch (IOException e) {
                logger.error("close BufferedWriter fail", e);
            }
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) {
        if (null == inputStream)
            throw new IllegalArgumentException("inputStream can't be null.");
        if (null == outputStream)
            throw new IllegalArgumentException("outputStream can't be null.");

        try {
            byte[] buffer = new byte[1024];
            int return_value = -1;

            return_value = inputStream.read(buffer);

            while (-1 != return_value) {
                outputStream.write(buffer, 0, return_value);
                return_value = inputStream.read(buffer);
            }
        }
        catch (IOException e) {
            logger.error("Error during the copying of streams.");
        }
    }

    public static void copy(InputStream inputStream, File target) {
        if (null == inputStream)
            throw new IllegalArgumentException("inputStream can't be null.");
        if (null == target)
            throw new IllegalArgumentException("target can't be null.");

        try {
            FileOutputStream file_output_stream = new FileOutputStream(target);

            copy(inputStream, file_output_stream);

            file_output_stream.close();
        }
        catch (IOException e) {
            logger.error("Error while copying an input stream to file '" + target.getAbsolutePath() + "'.");
        }
    }

    public static void copy(File source, OutputStream outputStream) {
        if (null == source)
            throw new IllegalArgumentException("source can't be null.");
        if (null == outputStream)
            throw new IllegalArgumentException("outputStream can't be null.");

        try {
            FileInputStream file_input_stream = new FileInputStream(source);

            copy(file_input_stream, outputStream);

            file_input_stream.close();
        }
        catch (IOException e) {
            logger.error("Error while copying file '" + source.getAbsolutePath() + "' to an output stream.");
        }
    }

    public static void copy(File source, File target) {
        if (null == source)
            throw new IllegalArgumentException("source can't be null.");
        if (null == target)
            throw new IllegalArgumentException("target can't be null.");

        try {
            FileInputStream file_input_stream = new FileInputStream(source);
            FileOutputStream file_output_stream = new FileOutputStream(target);

            copy(file_input_stream, file_output_stream);

            file_output_stream.close();
            file_input_stream.close();
        }
        catch (IOException e) {
            logger.error("Error while copying file '" + source.getAbsolutePath() + "' to file '"
                    + target.getAbsolutePath() + "'.");
        }
    }

    /**
     * 复制文件到某个文件夹下
     * 
     * @param file 文件
     * @param destPath 目的文件夹
     */
    public static void copy(File source, String destPath) {
        if (null == source)
            throw new IllegalArgumentException("source can't be null.");
        if (destPath != null) {
            try {
                File destDir = new File(destPath);
                if (!destDir.exists()) {
                    destDir.mkdir();
                }

                FileInputStream file_input_stream = new FileInputStream(source);
                FileOutputStream file_output_stream = new FileOutputStream(destDir.getPath() + File.separator
                        + source.getName());

                copy(file_input_stream, file_output_stream);

                file_output_stream.close();
                file_input_stream.close();
            }
            catch (IOException e) {
                logger.error("Error while copying file '" + source.getAbsolutePath() + "' to '" + destPath + "'.");
            }
        }
    }

    /**
     * 复制整个文件夹内容
     * 
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                    // temp.renameTo(arg0)
                }
                else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
    }

    /**
     * 删除文件夹里面的所有文件以及子文件夹
     * 
     * @param path String 文件夹路径 如 c:/fqf
     */
    public static void delAllFiles(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File f : dir.listFiles()) {
            delFile(f);
        }
    }

    /**
     * 删除指定文件，如果是文件则直接删除；如果是目录，递归遍历删除所有文件再删除该目录
     * 
     * @param p
     */
    public static void delFile(File p) {
        if (p.isFile()) {
            p.delete();
        }
        if (p.isDirectory()) {
            File[] files = p.listFiles();
            for (File file : files) {
                delFile(file);
            }
            p.delete();
        }
    }

    /**
     * 打开文件并得到InputStrea
     * 
     * @param file 文件
     * @return FileInputStream，如果文件不存在，返回null
     * @throws java.io.IOException 读取文件有误时抛出
     */
    public static FileInputStream getInputStream(File file) throws IOException {
        if (isFile(file)) {
            return new FileInputStream(file);
        }

        return null;
    }

    /**
     * 打开文件，如果文件不存在，自动创建，并得到FileOutputStream。
     * 
     * @param file 文件
     * @param allowOverwrite 是否运行覆盖旧文件
     * @return FileOutputStream，如果文件存在但不允许覆盖，返回null
     * @throws java.io.IOException 读取或创建新文件有误时抛出
     */
    public static FileOutputStream getOutputStream(File file, boolean allowOverwrite) throws IOException {
        if (checkFile(file, allowOverwrite)) {
            return new FileOutputStream(file);
        }

        return null;
    }

    /**
     * 打开文件并得到BufferedReader。
     * 
     * @param file 文件
     * @return BufferedReader 如果文件不存在，返回null
     * @throws java.io.IOException 读取文件有误时抛出
     */
    public static BufferedReader getReader(File file) throws IOException {
        if (isFile(file)) {
            return new BufferedReader(new FileReader(file));
        }
        return null;
    }

    /**
     * 打开文件，如果文件不存在，自动创建，并得到BufferedWriter。
     * 
     * @param file 文件
     * @param allowOverwrite 是否运行覆盖旧文件
     * @return BufferedWriter，如果文件存在但不运行覆盖，返回null
     * @throws java.io.IOException 读取或创建新文件有误时抛出
     */
    public static BufferedWriter getWriter(File file, boolean allowOverwrite) throws IOException {
        if (checkFile(file, allowOverwrite)) {
            return new BufferedWriter(new FileWriter(file));
        }

        return null;
    }

    /**
     * list folder files
     * 
     * @param targetPath
     * @return
     */
    public static List<String> listFolderFiles(String targetPath) {
        List<String> list = new ArrayList<String>();
        File folder = new File(targetPath);
        if (folder.isDirectory()) {
            String[] files = folder.list();
            for (String filename : files)
                list.add(filename);
        }
        return list;
    }

    /**
     * list folder files
     * 
     * @param targetPath
     * @return
     */
    public static File[] listFolderFileDetail(String targetPath) {
        File folder = new File(targetPath);
        if (folder.isDirectory()) {
            return folder.listFiles();
        }
        return new File[0];
    }

    /**
     * 移动文件到指定目录
     * 
     * @param oldPath String 如：c:/old/
     * @param newPath String 如：d:/new/
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delAllFiles(oldPath);
    }

    /**
     * 移动文件到指定目录
     * 
     * @param oldPath String 如：c:/old/a.txt
     * @param newPath String 如：d:/new/
     */
    public static void moveFile(File source, String destPath) {
        copy(source, destPath);
        delFile(source);
    }

    /**
     * 从文本文件中读取所有的行。
     * 
     * @param path 文件路径
     * @return 以数组形式存储的所有行
     * @throws java.io.IOException 读取文件有误时抛出
     */
    public static String[] readLines(String path) throws IOException {
        List<String> lines = new ArrayList<String>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        line = br.readLine();
        while (line != null) {
            lines.add(line);
            line = br.readLine();
        }
        br.close();

        return lines.toArray(new String[lines.size()]);
    }

    /**
     * 把基于网站的路径转换为为基于文件系统的路径 注意文件系统的分隔符 UNIX的是 '/' 而Windows的是 '\\'
     */
    public static String toLocalFilePath(String URLPath) {
        if (URLPath == null) {
            return null;
        }

        if (File.separatorChar == '\\')
            return URLPath.replace('/', File.separatorChar);
        else if (File.separatorChar == '/') {
            return URLPath.replace('\\', File.separatorChar);
        }
        else {
            logger.fatal("Not Support '" + File.separatorChar + "' to be File.separatorChar");
            return null;
        }
    }

    /**
     * 将字符串数组按行写入文件，如果文件已经存在将被覆盖。
     * 
     * @param path 文件路径
     * @param lines 需要写入的数据
     * @throws java.io.IOException 写入文件有误时抛出
     */
    public static void writeLines(String path, String[] lines) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    /**
     * 压缩
     * 
     * @param path String 如：E:/work/svn/my_work/Test-Log4j/logs/
     * @param target String 如：E:/work/svn/my_work/Test-Log4j/logs.zip
     */
    public static void zip(String path, String target) throws IOException {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(target);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte data[] = new byte[BUFFER];
            File f = new File(path);
            File files[] = f.listFiles();

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(files[i].getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        }
        catch (Exception e) {
            logger.error("Error while zip file '" + path + "' to '" + target + "'.");
            e.printStackTrace();
        }
    }

    /**
     * 解压缩
     * 
     * @param fileName String 如：E:/test/myfiles.zip
     * @param filePath String 如：E:/test/
     */
    public static void unZip(String fileName, String filePath) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(fileName);
            Enumeration<?> emu = zipFile.entries();
            while (emu.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) emu.nextElement();
                // 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory()) {
                    new File(filePath + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(filePath + entry.getName());
                // 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                // 而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if (parent != null && (!parent.exists())) {
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while unZip file '" + fileName + "' to '" + filePath + "'.");
        }

    }

    // 检查文件是否存在，如果不存在自动创建。 如果文件存在而且不允许覆盖 -> false
    private static boolean checkFile(File file, boolean allowOverwrite) throws IOException {
        if (file != null) {
            // 检查父目录，不存在时自动创建
            File parent = file.getParentFile();
            boolean parentExist = true;
            if (parent != null && !parent.exists()) {
                parentExist = parent.mkdirs();
            }
            if (parentExist && (file.createNewFile() || (file.exists() && allowOverwrite))) {
                return true;
            }
        }
        return false;
    }

    // 是否是一个存在的文件
    private static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    // 是否是一个存在的文件夹
    private static boolean isDirectory(File dir) {
        return dir != null && dir.exists() && dir.isDirectory();
    }

    /**
     * 打开文件并得到Byte[]
     * 
     * @param file 文件
     * @return FileInputStream，如果文件不存在，返回null
     * @throws java.io.IOException 读取文件有误时抛出
     */
    public static byte[] getByte(File file) throws IOException {
        if (isFile(file)) {

            InputStream is = new FileInputStream(file);

            // 获取文件大小
            long length = file.length();

            if (length > Integer.MAX_VALUE) {
                // 文件太大，无法读取
                throw new IOException("File is to large " + file.getName());
            }

            // 创建一个数据来保存文件数据
            byte[] bytes = new byte[(int) length];

            // 读取数据到byte数组中
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            // 确保所有数据均被读取
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }

            // Close the input stream and return bytes
            is.close();
            return bytes;
        }

        return null;
    }

}
