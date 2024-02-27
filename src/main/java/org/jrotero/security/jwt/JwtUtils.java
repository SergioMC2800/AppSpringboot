package org.jrotero.security.jwt;

import java.security.Key;
import java.sql.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value("${jwt.time.expiration}")
	private String timeExpiration;
	
	//generador de acceso por token
	public String generateAccessToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	//Validar token
	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(getSignatureKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
			return true;
		}catch (Exception e) {
			log.error("invalid token, Error: ".concat(e.getMessage()));
			return false;
		}
		
		
	}
	
	//Obtener username
	public String getUsernameToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
		Claims claims = extractAllClaims(token);
		return claimsFunction.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignatureKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignatureKey() {
		byte[] keyBy = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBy);
	}
}
