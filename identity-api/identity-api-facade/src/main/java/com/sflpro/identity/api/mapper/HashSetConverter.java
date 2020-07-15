package com.sflpro.identity.api.mapper;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Company: SFL LLC
 * Created on 7/15/20
 *
 * @author Manuk Gharslyan
 */
@Component
public class HashSetConverter extends CustomConverter<HashSet<?>, Object> {

    @Override
    public Object convert(HashSet<?> hashSet, Type<?> destinationType, MappingContext mappingContext) {
        return hashSet;
    }
}
