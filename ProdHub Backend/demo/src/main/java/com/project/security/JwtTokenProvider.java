package com.project.security;

import java.util.Date;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.project.exceptions.MyAPIException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;



@Component
public class JwtTokenProvider {

	@Value("${app-jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		
		Date currentDate = new Date();
		
		Date expiredDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expiredDate)
				.signWith(key())
				.compact();
		
		return token;
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(
				Decoders.BASE64.decode(jwtSecret)
		);
	}
	
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();
		String username = claims.getSubject();
		return username;
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parse(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "INVALID TOKEN!!");
		} catch (ExpiredJwtException ex) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "EXPIRED TOKEN!!");
		} catch (UnsupportedJwtException ex) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "UNSUPPORTED TOKEN!!");
		} catch (IllegalArgumentException ex) {
			throw new MyAPIException(HttpStatus.BAD_REQUEST, "EMPTY TOKEN");
		}
		
	}
	
}
