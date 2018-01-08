package com.sflpro.identity.core.db;

import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

/**
 * Company: SFL LLC
 * Created on 9/2/16
 *
 * @author Yervand Aghababyan
 */
@Component("hibernateEhCacheConfigLocator")
public class HibernateEhCacheConfigLocator {

    private static final String CONFIG_LOCATION = "/com/sflpro/identity/ehcache.xml";

    public String getconfigUri() throws URISyntaxException {
        return getClass().getResource(CONFIG_LOCATION).toURI().toString();
    }
}
