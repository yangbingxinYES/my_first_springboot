package com.springboot.demo_ybx07.util;

import com.springboot.demo_ybx07.entity.UserEntity;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码散列算法 .
 *
 * @author 杨冰鑫
 * @since 2019/8/16 9:33
 */
public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static final String ALGORITHM_NAME = "md5"; // 基础散列算法
    public static final int HASH_ITERATIONS = 2; // 自定义散列次数

    public void encryptPassword(UserEntity user) {
        // 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassWord(),
                                            ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
        user.setPassWord(newPassword);
    }



}
