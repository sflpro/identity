package com.sflpro.identity.api.mapper;

import com.sflpro.identity.api.common.dtos.auth.mechanism.PrincipalAuthenticationRequestDetailsDto;
import com.sflpro.identity.api.common.dtos.auth.mechanism.TokenAuthenticationRequestDetailsDto;
import com.sflpro.identity.api.common.dtos.token.AccessTokenRequestDto;
import com.sflpro.identity.core.services.auth.mechanism.principal.PrincipalAuthenticationRequestDetails;
import com.sflpro.identity.core.services.auth.mechanism.token.TokenAuthenticationRequestDetails;
import com.sflpro.identity.core.services.token.AccessTokenRequest;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * Company: SFL LLC
 * Created on 6/17/16
 *
 * @author Yervand Aghababyan
 */
@Component
public class BeanMapper extends ConfigurableMapper {

    private MapperFactory factory;

    @Autowired(required = false)
    private final List<Converter<?, ?>> converters = Collections.emptyList();

    @Autowired(required = false)
    private final List<Mapper<?, ?>> mappers = Collections.emptyList();

    public BeanMapper() {
        super(false);
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(MapperFactory factory) {
        this.factory = factory;

        factory.classMap(PrincipalAuthenticationRequestDetails.class, PrincipalAuthenticationRequestDetailsDto.class).byDefault().register();
        factory.classMap(TokenAuthenticationRequestDetails.class, TokenAuthenticationRequestDetailsDto.class).byDefault().register();
        factory.classMap(AccessTokenRequest.class, AccessTokenRequestDto.class).byDefault().register();

        addAllSpringBeans();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
        // Nothing to configure for now
    }

    /**
     * Constructs and registers a {@link ClassMapBuilder} into the {@link MapperFactory} using a {@link Mapper}.
     *
     * @param mapper
     */
    @SuppressWarnings("rawtypes")
    public void addMapper(Mapper<?, ?> mapper) {
        factory.classMap(mapper.getAType(), mapper.getBType())
                .byDefault()
                .customize((Mapper) mapper)
                .register();
    }

    /**
     * Registers a {@link Converter} into the {@link ConverterFactory}.
     *
     * @param converter
     */
    public void addConverter(Converter<?, ?> converter) {
        factory.getConverterFactory().registerConverter(converter);
    }

    private void addAllSpringBeans() {
        mappers.forEach(this::addMapper);
        converters.forEach(this::addConverter);
    }
}
