package com.techner.tau.services.service;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.techner.tau.services.config.Config;

/**
 * Servicio de autenticación
 * 
 * @author jmartini
 * 
 */
public class AuthenticationTokenService {
	/** en/de cryptor */
	private final PBEStringCleanablePasswordEncryptor encryptor;
	/** token age */
	private final long TOKEN_AGE;
	/** lista de tokens */
	private final Set<String> tokens;
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenService.class);

	/**
	 * @param encryptor
	 */
	@Inject
	AuthenticationTokenService(PBEStringCleanablePasswordEncryptor encryptor, Config config) {
		this.encryptor = encryptor;
		this.TOKEN_AGE = config.getTokenAge();
		this.tokens = config.getWildcarTokens();
	}

	/**
	 * Genera token para ser entregado al usuario que se loguea
	 * 
	 * @param user
	 *            email usuario
	 * @return token
	 */
	public String generateToken(String user) {
		StringBuffer sb = new StringBuffer(UUID.randomUUID().toString().toUpperCase());
		sb.append("|").append(user).append("|").append(System.currentTimeMillis());
		return encryptor.encrypt(sb.toString());
	}

	/**
	 * Verifica si el token es válido o no
	 * 
	 * @param eToken
	 *            token codificado
	 * @return true si es válido, false en otro caso
	 */
	public boolean isValidToken(String eToken) {
		try {
			if (tokens.contains(eToken)) {
				return true;
			}
			String token = encryptor.decrypt(eToken);
			String params[] = StringUtils.split(token, "|");
			if (params.length != 3) {
				return false;
			}
			long time = System.currentTimeMillis() - Long.parseLong(params[2]);

			if (time > TOKEN_AGE) {
				return false;
			}
		} catch (Exception e) {
			logger.warn("Error validating token {}", eToken);
			return false;
		}
		return true;
	}

}
