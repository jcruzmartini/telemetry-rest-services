package com.techner.tau.services.filter;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.techner.tau.common.exception.AuthenticationException;
import com.techner.tau.services.config.Config;
import com.techner.tau.services.service.AuthenticationTokenService;

public class AuthenticationTokenFilter implements ContainerRequestFilter {
	/** auth service */
	private final AuthenticationTokenService service;
	/** security enable */
	private final boolean securityEnable;
	/** Token a buscar */
	private final String TOKEN_PARAM = "eToken";
	/** Accept Lang Header */
        private final String ACCEPT_LANG_HEADER = "Accept-Language";
        /** Accept Lang Header Value*/
        private final String ACCEPT_LANG_HEADER_VALUE = "en-US,en;q=0.8,es;q=0.6";
	/** DMZ paths **/
	private final Set<String> pathts;
	/** logger */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

	/**
	 * @param service
	 */
	@Inject
	AuthenticationTokenFilter(AuthenticationTokenService service, Config config) {
		this.service = service;
		this.securityEnable = config.isSecurityEnable();
		this.pathts = config.getDMZPaths();
	}

	@Override
	public ContainerRequest filter(ContainerRequest request) {
	        //HACK for issue related with Languages in Jersey
	        InBoundHeaders headers = replaceAcceptLanguageHeader(request.getRequestHeaders());
	        request.setHeaders(headers);
	        
		if (securityEnable) {
			String path = request.getPath();
			if (pathts.contains(path)) {
				return request;
			}

			String eToken = getEToken(request.getQueryParameters());

			if (StringUtils.isEmpty(eToken)) {
				throw new AuthenticationException(TOKEN_PARAM + " not present");
			}

			if (!service.isValidToken(eToken)) {
				throw new AuthenticationException(TOKEN_PARAM + " expired or not valid");
			}
		}
		return request;
	}

	private InBoundHeaders replaceAcceptLanguageHeader(MultivaluedMap<String, String> requestHeaders) {
        	InBoundHeaders headers = new InBoundHeaders();
        	
        	for (Entry<String, List<String>> header : requestHeaders.entrySet()) {
                    if (header.getKey().equals(ACCEPT_LANG_HEADER)){
                        headers.add(ACCEPT_LANG_HEADER,ACCEPT_LANG_HEADER_VALUE);
                    }else {
                        if (header.getValue() != null && header.getValue().size() > 1){
                            LOG.error("hay mas de un valor para el header " + header.getKey() );
                        }
                        headers.add(header.getKey(), header.getValue().get(0));
                    }
                }
        	return headers;
        }

    /**
	 * Obtiene el eToken desde los parametros
	 * 
	 * @param queryParameters
	 *            lista de query params
	 * @return token
	 */
	private String getEToken(MultivaluedMap<String, String> queryParameters) {
		if (!queryParameters.containsKey(TOKEN_PARAM)) {
			return null;
		}
		return queryParameters.get(TOKEN_PARAM).get(0);
	}

}
