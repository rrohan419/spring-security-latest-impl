package com.demo.springsecurity.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.demo.springsecurity.entity.Employee;
import com.demo.springsecurity.exception.CustomException;
import com.demo.springsecurity.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JwtUtil {
	
//	@Value("${jwt.expiration.ms}")
	private static int tokenExpirationTime = 86400000;
	
//	@Value("${jwt.secret}")
	private static String jwtSecret = "abcdefghijklmnop";
	
	public Claims getJwtClaims(String jwtToken) {
		Claims claims = null;

		try {
			claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtToken).getBody();
		} catch (ExpiredJwtException e) {
			throw new CustomException("Token is Expired", e, ErrorCode.EXPIRED_TOKEN);
		} catch (SignatureException | MalformedJwtException e) {
			throw new CustomException("Invalid Token", e, ErrorCode.INVALID_TOKEN);
		} catch (Exception e) {
			throw new CustomException("Internal Error while passing the token", e, ErrorCode.INTERNAL_SERVER_ERROR);
		}

		return claims;
	}
	
	public String generateToken(Authentication authentication) {
		Employee employeePrinciple = (Employee) authentication.getPrincipal();

		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(employeePrinciple.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime()+ tokenExpirationTime))
				.claim("roles", authorities)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
//	public boolean validateToken(Claims claims) {
//		return !claims.getExpiration().before(new Date());
//	}
//	
//	
//	
//	
//	public static String generateToken(String username,Claims claims)
//	{
//		return Jwts.builder()
//				.setHeaderParam("typ", "JWT")
//				.setClaims(claims)
//				.setSubject(username)
//				.setExpiration(new Date(System.currentTimeMillis()+tokenExpirationTime))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret)
//				.compact();
//	}
//	
//	public static void verifyToken(String token)
//	{
//		Jwts.parser()
//			.setSigningKey(jwtSecret)
//			.parse(token.substring(7));
//	}
//	
//	public static Claims getClaimsFromToken(String token)
//	{
//		return Jwts.parser()
//				.setSigningKey(jwtSecret)
//				.parseClaimsJws(token.substring(7))
//				.getBody();
//	}
//	
//	public static String updateExpirationDateToken(String token)
//	{
//		Claims claims = getClaimsFromToken(token);
//		
//		return Jwts.builder()
//				.setHeaderParam("typ", "JWT")
//				.setClaims(claims)
//				.setExpiration(new Date(System.currentTimeMillis()+tokenExpirationTime))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret)
//				.compact();
//	}
}
