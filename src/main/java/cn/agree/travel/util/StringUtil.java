package cn.agree.travel.util;

/**
 * 包名:com.itheima.travel.utils
 * 作者:Leevi
 * 日期2018-09-28  09:03
 */
public class StringUtil {
    /**
     * 判断一个字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if (str != null && !"".equals(str) && !"null".equals(str)){
            return false;
        }else {
            return true;
        }
    }
}
