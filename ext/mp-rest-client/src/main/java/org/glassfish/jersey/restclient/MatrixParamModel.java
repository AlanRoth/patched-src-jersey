/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.restclient;

import java.lang.annotation.Annotation;
import java.util.Collection;

import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.WebTarget;

/**
 * Contains information to method parameter which is annotated by {@link MatrixParam}.
 *
 * @author David Kral
 */
class MatrixParamModel extends ParamModel<WebTarget> {

    private final String matrixParamName;

    /**
     * Creates new matrix model.
     *
     * @param builder
     */
    MatrixParamModel(Builder builder) {
        super(builder);
        matrixParamName = builder.matrixParamName();
    }

    @Override
    public WebTarget handleParameter(WebTarget requestPart, Class<?> annotationClass, Object instance) {
        Object resolvedValue = interfaceModel.resolveParamValue(instance,
                                                                getType(),
                                                                getAnnotatedElement().getAnnotations());
        if (resolvedValue instanceof Collection) {
            return requestPart.matrixParam(matrixParamName, ((Collection) resolvedValue).toArray());
        } else {
            return requestPart.matrixParam(matrixParamName, resolvedValue);
        }
    }

    @Override
    public boolean handles(Class<Annotation> annotation) {
        return MatrixParam.class.equals(annotation);
    }

}