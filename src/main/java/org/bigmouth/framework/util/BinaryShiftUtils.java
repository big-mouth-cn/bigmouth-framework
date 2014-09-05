package org.bigmouth.framework.util;

public class BinaryShiftUtils {

    /**
     * 将byte数组转成一个short表示的Date 2012 02 16 Bit分布[年|月|日7|4|5]
     */
    public static short transferBytesArrayToShortDate(byte[] byteArray, int idx) {
        return (short) (((byteArray[idx] & 0xFF) << 9) | ((byteArray[idx + 1] & 0xFF) << 5) | (byteArray[idx + 2] & 0xFF));
    }

    /**
     * 将byte数组转成一个short表示的Time 15 : 22 Bit分布[时|分8|8]
     */
    public static short transferBytesArrayToShortTime(byte[] byteArray, int idx) {
        return (short) (((byteArray[idx] & 0xFF) << 8) | (byteArray[idx + 1] & 0xFF));
    }

    /**
     * byte数组拼接成int
     */
    public static int getIntFromByteArray(byte[] byteArray, int idx) {
        return ((byteArray[idx] & 0xFF) << 24) | ((byteArray[idx + 1] & 0xFF) << 16)
                | ((byteArray[idx + 2] & 0xFF) << 8) | (byteArray[idx + 3] & 0xFF);
    }

    /**
     * byte数组拼接成short
     */
    public static short getShortFromByteArray(byte[] byteArray, int idx) {
        return (short) (((byteArray[idx + 1] & 0xFF) << 8) | (byteArray[idx] & 0xFF));
    }

    /**
     * int拆分成byte数组
     */
    public static byte[] getBytesFromInt(int i) {
        byte b[] = new byte[4];
        b[0] = (byte) (i >> 24 & 0xFF);
        b[1] = (byte) (i >> 16 & 0xFF);
        b[2] = (byte) (i >> 8 & 0xFF);
        b[3] = (byte) (i >> 0 & 0xFF);
        return b;
    }

    /**
     * short拆分成byte数组
     */
    public static byte[] getBytesFromShort(short s) {
        byte b[] = new byte[2];
        b[0] = (byte) (s >> 8 & 0xFF);
        b[1] = (byte) (s >> 0 & 0xFF);
        return b;
    }

    public static void main(String[] args) {
        byte b[] = new byte[3];
        b[0] = 12;
        b[1] = 2;
        b[1] = 16;
        System.out.println(transferBytesArrayToShortDate(b, 0));
    }

}
