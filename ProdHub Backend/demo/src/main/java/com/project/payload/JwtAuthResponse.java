package com.project.payload;

import java.util.List;
import java.util.Set;

import com.project.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private Set<UserRole> roles;
	public String getAccessToken() {
		return token;
	}
	public void setAccessToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<UserRole> set) {
		this.roles = set;
	}
	
	
	
}
