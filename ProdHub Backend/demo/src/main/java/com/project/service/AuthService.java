package com.project.service;

import com.project.payload.Login;
import com.project.payload.RegisterReq;
import com.project.payload.RegisterRes;

public interface AuthService {

	String login(Login login);
	RegisterRes register(RegisterReq registerReq);
	
}
