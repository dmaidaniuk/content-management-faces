/*
 * Copyright (c) 2011 Bill Reh.
 *
 * This file is part of Content Management Faces.
 *
 * Content Management Faces is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.tralfamadore.component.content;

import net.tralfamadore.cmf.ContentManager;
import net.tralfamadore.cmf.Namespace;
import net.tralfamadore.cmf.Script;
import net.tralfamadore.cmf.Style;
import net.tralfamadore.config.CmfContext;

import javax.faces.application.Application;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

/**
 * User: billreh
 * Date: 1/19/11
 * Time: 3:00 AM
 */
@FacesComponent(Content.COMPONENT_TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
public class Content extends HtmlOutputText {
    
    /** The standard component type. */
    public static final String COMPONENT_TYPE = "net.tralfamadore.component.content.Content";
    
    protected ContentManager contentManager;

    protected enum PropertyKeys {
        namespace,
        name
    }

    public ContentManager getContentManager() {
        if(contentManager == null) {
            try {
                contentManager = CmfContext.getInstance().getContentManager();
            } catch (Exception e) {
                // ignore
            }
        }
        return contentManager;
    }

    @Override
    public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();

        net.tralfamadore.cmf.Content content = getContentManager().loadContent(Namespace.createFromString(getNamespace()), getName());

        if(content != null) {
            for(Script resource : content.getScripts()) {
                addResource(context, resource);
            }

            for(Style resource : content.getStyles()) {
                addResource(context, resource);
            }
        }
    }

    public void addResource(FacesContext context, Style style) {
        addResource(context, style.getName(), style.getNamespace().getFullName(), style.getStyle(), "style");
    }

    public void addResource(FacesContext context, Script script) {
        addResource(context, script.getName(), script.getNamespace().getFullName(), script.getScript(), "script");
    }

    private void addResource(FacesContext context, String name, String namespace, String content, String type) {
        if(content != null && ! content.isEmpty()) {
            Application application = context.getApplication();
            ContentResource contentResource = (ContentResource)
                    application.createComponent(context, "ContentResource", "ContentResourceRenderer");
            contentResource.setContent(content);
            contentResource.setType(type);
            contentResource.setName(name);
            contentResource.setNamespace(namespace);
            context.getViewRoot().addComponentResource(context, contentResource, "head");
        }
    }

    public String getName() {
        return (String) getStateHelper().eval(PropertyKeys.name);
    }

    public void setName(String name) {
        getStateHelper().put(PropertyKeys.name, name);
    }

    public String getNamespace() {
        return (String) getStateHelper().eval(PropertyKeys.namespace);
    }

    public void setNamespace(String namespace) {
        getStateHelper().put(PropertyKeys.namespace, namespace);
    }
}
