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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.mpass.shibboleth.rest.data.AuthnSourceDTO;
import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.collection.CollectionSupport;
import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.logic.Constraint;

/**
 * This action builds a response containing {@link AuthnSourceDTO}s.
 */
public class BuildAuthnSourcePropertiesRestResponse extends AbstractAuthnFlowRestResponseAction {

    /** Class logger. */
    @Nonnull private final Logger log = LoggerFactory.getLogger(BuildAuthnSourcePropertiesRestResponse.class);

    /** Flow properties configuration. */
    @NonnullAfterInit FlowPropertiesConfiguration flowPropertiesConfiguration;

    /** The full flow information to be responded by the API. */
    private Map<String, List<AuthnSourceDTO>> flowInformation;

    public void setFlowPropertiesConfiguration(@Nonnull final FlowPropertiesConfiguration config) {
        checkSetterPreconditions();
        flowPropertiesConfiguration =
                Constraint.isNotNull(config, "Flow properties configuration cannot be null");
    }
    /** {@inheritDoc} */
    @Override
    protected void doInitialize() throws ComponentInitializationException {
        log.debug("Initializing");
        super.doInitialize();
        if (flowPropertiesConfiguration == null) {
            throw new ComponentInitializationException("Flow properties configuration cannot be null");
        }
        flowInformation = new HashMap<>();
        for (final String locale : getSupportedLocales()) {
            flowInformation.put(locale, new ArrayList<AuthnSourceDTO>());
        }
        for (final String flow : flowPropertiesConfiguration.getFlowProperties().keySet()) {
            final String id = (flow.startsWith("authn")) ? flow.substring(6) : flow;
            log.debug("{} Adding flow {}", getLogPrefix(), id);
            final String titleProperty = getAdditionalInfo().getProperty(id + ".title", id + ".title");
            final String iconUrlProperty = getAdditionalInfo().getProperty(id + ".iconUrl", id + ".iconUrl");
            final List<String> tags = CollectionSupport.emptyList();
            for (final String locale : getSupportedLocales()) {
                flowInformation.get(locale).add(new AuthnSourceDTO(id, 
                        getMessageSource().getMessage(titleProperty, null, Locale.forLanguageTag(locale)), 
                        tags,
                        getMessageSource().getMessage(iconUrlProperty, null, Locale.forLanguageTag(locale)),
                        "true".equalsIgnoreCase(getAdditionalInfo().getProperty(id + ".isRegistry")),
                        true, false));
            }
        }
    }
    
    /** {@inheritDoc} */
    @Override
    protected Object getResponse(final String lang) {
        return flowInformation.get(lang.toUpperCase());
    }
}
