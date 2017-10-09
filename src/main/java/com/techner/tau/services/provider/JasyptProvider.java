package com.techner.tau.services.provider;

import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.google.inject.Provider;

public class JasyptProvider implements Provider<PBEStringCleanablePasswordEncryptor> {

	@Override
	public PBEStringCleanablePasswordEncryptor get() {
		PBEStringCleanablePasswordEncryptor enc = new StandardPBEStringEncryptor();
		enc.setPassword("tau-services");
		return enc;
	}

}
