package com.example.leo.util;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "leo.jwt")
@Slf4j
public class JwtUtils {

	private long expire;
	private String secret;
	private String header;

	// 生成jwt
	public String generateToken(String username) {

		Date nowDate = new Date();
		Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(username)
				.setIssuedAt(nowDate)
				.setExpiration(expireDate)// 7天過期
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	// 解析jwt
	public Claims getClaimByToken(String jwt) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(jwt)
					.getBody();
			return claims;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	// jwt是否过期
	public boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date());
	}

	public static void main(String[] args) {
		Claims claims = Jwts.parser()
				.setSigningKey("ji8n3439n439n43ld9ne9343fdfer49h")
				.parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsZW8iLCJpYXQiOjE2MzU4NDQ3MzEsImV4cCI6MTY0MTg5MjczMX0.wrNZRce62A3LeLkmwaHNetKS6T3c_ptHALMw2n3JLPLcGuRX4-3nTpkrwBWFHXIpatsRVuCYkZ1ryGqzGMdwzA")
				.getBody();
		System.out.println(claims);
	}

}
