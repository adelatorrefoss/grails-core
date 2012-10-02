/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins;

import grails.util.PluginBuildSettings;
import org.codehaus.groovy.grails.io.support.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Combines different implementation into one.
 *
 * @author Graeme Rocher
 * @since 1.3
 */
public class CompositePluginDescriptorReader implements PluginDescriptorReader {

    private List<PluginDescriptorReader> pluginDescriptorReaders = new ArrayList<PluginDescriptorReader>();

    public CompositePluginDescriptorReader(PluginBuildSettings pluginSettings) {
        pluginDescriptorReaders.add(new XmlPluginDescriptorReader(pluginSettings));
        pluginDescriptorReaders.add(new AstPluginDescriptorReader());
    }

    public GrailsPluginInfo readPluginInfo(Resource r) {
        for (PluginDescriptorReader reader : pluginDescriptorReaders) {
            GrailsPluginInfo info = reader.readPluginInfo(r);
            if (info != null) return info;
        }
        return null;
    }
}
