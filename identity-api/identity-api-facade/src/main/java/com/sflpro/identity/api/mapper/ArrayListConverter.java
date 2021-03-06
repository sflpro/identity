package com.sflpro.identity.api.mapper;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Company: SFL LLC
 * Created on 7/15/20
 *
 * @author Manuk Gharslyan
 */
@Component
public class ArrayListConverter extends CustomConverter<ArrayList<?>, Object> {

    @Override
    public Object convert(ArrayList<?> hashSet, Type<?> destinationType, MappingContext mappingContext) {
        return hashSet;
    }
}