package util;

import java.security.MessageDigest;

/**
 * @author test
 * @classname EncryptMd5
 * @date 2023/2/25 19:20
 */
public class EncryptMd5 {
    private static String pwd="";
    //定义一个md5，32位的加密码算法
    public static String getMd5(String str){
        try {
            //设置一个摘要
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] md5Bytes = md5.digest(str.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            pwd = hexValue.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pwd;
    }
}
