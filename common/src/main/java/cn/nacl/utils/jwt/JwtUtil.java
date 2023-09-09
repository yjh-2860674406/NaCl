package cn.nacl.utils.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//注册组件
@Component
public class JwtUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    // 主体
    private static final String CLAIM_KEY_CREATED = "created";
    // 创建时间
    private final String SECRET = "NaClKClCaCl2CuCl2FeCl3Na2CO3CaCO3K2CO3NaClKClCaCl2CuCl2FeCl3Na2CO3CaCO3K2CO3";
    // 盐
    private final Long expiration = 86400L;
    // 存活时间

    /***
     * username -> token
     * @param username userinfo
     * @return token
     */
    public String createToken(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    /***
     * token -> username
     * @param token token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        String username = "";
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
            throw new RuntimeException("token非法");
        }
        return username;
    }

    /***
     * token -> claim
     * @param token token
     * @return claim
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /***
     * claim -> token
     * @param claims claim
     * @return token
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /***
     * calculate expired date
     * @return date
     */
    private Date expirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /***
     * token is belonged user ? and is expired ?
     * @param token token
     * @param username userinfo
     * @return belong ? expired ?
     */
    public boolean validateToken(String token, String username) {
        String username_v = getUsernameFromToken(token);
        return username_v.equals(username) && !isTokenExpired(token);
    }

    /***
     * token is expired ?
     * @param token token
     * @return expired
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFormToken(token);
        return expiredDate.before(new Date());
    }

    /***
     * token -> claim -> expiredDate
     * @param token token
     * @return expired date
     */
    private Date getExpiredDateFormToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /***
     * token can be refreshed ?
     * @param token
     * @return
     */
    public boolean canBeRefreshed(String token){
        return !isTokenExpired(token);
    }

    /***
     * refresh token
     * @param token token
     * @return new token
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        //修改为当前时间
        claims.put(CLAIM_KEY_CREATED,new Date());
        return createToken(claims);
    }

}

