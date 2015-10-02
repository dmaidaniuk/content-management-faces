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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.tralfamadore.admin.util;

import net.tralfamadore.cmf.ContentManager;
import net.tralfamadore.config.CmfContext;

import javax.enterprise.inject.Produces;

/**
 * User: billreh
 * Date: 10/15/11
 * Time: 4:18 AM
 */
public class ContentManagerProvider {
    @Produces
    public ContentManager getContentManager() {
        return CmfContext.getInstance().getContentManager();
    }
}
