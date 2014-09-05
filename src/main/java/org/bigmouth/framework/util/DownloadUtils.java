package org.bigmouth.framework.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public final class DownloadUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    public static enum FileSuffix {

        EXCEL {

            @Override
            public String getSuffix() {
                return ".xls";
            }
        },
        TXT {

            @Override
            public String getSuffix() {
                return ".txt";
            }
        },
        GIF {

            @Override
            public String getSuffix() {
                return ".gif";
            }
        },
        JPEG {

            @Override
            public String getSuffix() {
                return ".jpeg";
            }
        };

        public abstract String getSuffix();
    }

    private DownloadUtils() {
    }

    public static String genFileName(String flag, FileSuffix suf) {
        if (StringUtils.isBlank(flag))
            throw new IllegalArgumentException("flag is blank.");
        if (null == suf)
            throw new NullPointerException("suf");
        return flag + "_" + getTimeStamp() + suf.getSuffix();
    }

    public static OutputStream getDownloadOutput(String name) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("name is blank.");
        HttpServletResponse response = ServletActionContext.getResponse();
        if (null == response)
            throw new IllegalStateException("ServletActionContext.getResponse() is null.");
        String desc = "attachment; filename=" + name;
        response.setContentType("application/x-msdownload");
        response.setHeader("content-disposition", desc);
        try {
            return response.getOutputStream();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OutputStream getDownloadOutput(String flag, FileSuffix suf) {
        String name = genFileName(flag, suf);
        return getDownloadOutput(name);
    }

    private static String getTimeStamp() {
        return sdf.format(new Date());
    }
}
