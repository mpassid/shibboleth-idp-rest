/*
 * The MIT License
 * Copyright (c) 2015 CSC - IT Center for Science, http://www.csc.fi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package fi.mpass.shibboleth.profile.impl;

import java.util.Map;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.annotation.constraint.NotEmpty;
import net.shibboleth.shared.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.shared.logic.Constraint;
import net.shibboleth.shared.logic.ConstraintViolationException;
import net.shibboleth.shared.primitive.LoggerFactory;

public class FlowPropertiesConfiguration extends AbstractIdentifiableInitializableComponent {

    private final Logger log = LoggerFactory.getLogger(FlowPropertiesConfiguration.class);

    @NonnullAfterInit private Map<String, Object> flowProperties;
    
    public void setFlowProperties(@Nonnull @NotEmpty final String properties) {
        checkSetterPreconditions();
        Constraint.isNotEmpty(properties, "Flow properties cannot be empty");
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        final JavaType objectType = typeFactory.constructType(Object.class);
        final JavaType stringType = typeFactory.constructType(String.class);
        final MapType objectMapType = typeFactory.constructMapType(Map.class, stringType, objectType);
        
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            flowProperties = objectMapper.readValue(properties, objectMapType);
        } catch (final JsonProcessingException e) {
            log.error("Could not deserialize the flow properties configuration", e);
            throw new ConstraintViolationException("Could not deserialize the flow properties configuration");
        }
    }

    @Nonnull public Map<String, Object> getFlowProperties() {
        checkComponentActive();
        return flowProperties;
    }
    
}
