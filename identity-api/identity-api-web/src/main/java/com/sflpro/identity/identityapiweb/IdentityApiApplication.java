package com.sflpro.identity.identityapiweb;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

/**
 * Company: SFL LLC
 * Created on 20/11/2017
 *
 * @author Davit Harutyunyan
 */
@SpringBootApplication
@ImportResource("classpath:/com/sflpro/identity/identity-context-api-web.xml")
public class IdentityApiApplication {

    public IdentityApiApplication() {
        super();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(IdentityApiApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
