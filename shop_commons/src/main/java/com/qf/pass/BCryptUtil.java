package com.qf.pass;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/18 16:14
 */
public class BCryptUtil {

    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String hash, String password){
        return BCrypt.checkpw(password, hash);
    }

    public static void main(String[] args) {
        String str = "123456";
        System.out.println(hashPassword(str));

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
//            boolean flag = "$2a$10$rMqSggl4T31wE/WVUZ5iWuqrqXj1yY2r48Z4XQE657SeU2utSPbpG".equals(str);
            boolean flag = checkPassword("$2a$10$rMqSggl4T31wE/WVUZ5iWuqrqXj1yY2r48Z4XQE657SeU2utSPbpG", str);
        }
        long end = System.currentTimeMillis();

        System.out.println("耗时：" + ((end - begin)/1000.0));
    }
}
