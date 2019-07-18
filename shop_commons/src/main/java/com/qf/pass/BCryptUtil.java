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
}
