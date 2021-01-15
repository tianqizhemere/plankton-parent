package top.tianqi.plankton.common.shiro.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * jwt工具类
 * @author tianQi
 * @create 2021-01-12
 */
public class JwtUtil {

    /**
     * 过期时间1(天)
     */
    private static final long EXPRIE_TIME = 1;

    /**
     * token key
     */
    public static final String TOKEN_KEY = "imel";

    /**
     * 盐
     */
    private static final String SECRET_KEY = "secret-token-key";

    /**
     * 校验
     * @param token 秘钥
     * @param ieml 移动设备码
     * @return
     */
    public static boolean verify(String token, String ieml) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(TOKEN_KEY, ieml)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取imel(设备识别码)
     * 获取token中的信息无需secret解密也能获得
     * @param token 秘钥
     * @return
     */
    public static String getImel(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(TOKEN_KEY).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成token
     * @param ieml 设备识别号
     * @return token值
     * @throws UnsupportedEncodingException
     */
    public static String sign(String ieml) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + EXPRIE_TIME * 24 * 60 * 60 * 1000);
        Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
        // 附带ieml信息
        return JWT.create()
                .withClaim(TOKEN_KEY, ieml)
                .withExpiresAt(date)
                // 创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
    }

}
