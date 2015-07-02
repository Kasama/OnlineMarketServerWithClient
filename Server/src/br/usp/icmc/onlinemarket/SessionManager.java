package br.usp.icmc.onlinemarket;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {

	private Map<String, User> sessionTokens;

	public SessionManager(){
		sessionTokens = new HashMap<>();
	}

	public boolean logout(String token){

		return sessionTokens.remove(token) != null;

	}

	public String getSessionToken(String user, String password){

		String tomd5 = user+password;

		return getMD5Sum(tomd5);

	}

	public String getMD5Sum(String tomd5){
		MessageDigest md = null;
		BigInteger bi;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ignored){}

		assert md != null;
		bi = new BigInteger(1, md.digest(tomd5.getBytes()));

		return bi.toString(16);

	}

	public void addSession(String token, User user) {
		if (!sessionTokens.containsKey(token))
			sessionTokens.put(token, user);
	}

	public User getUserByToken(String token){
        return sessionTokens.get(token);
	}
}
