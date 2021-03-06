/*******************************************************************************
 * Copyright (c) 2012, 2014 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation - Jay Jay Billings,
 *   Jordan H. Deyton, Dasha Gorin, Alexander J. McCaskey, Taylor Patterson,
 *   Claire Saunders, Matthew Wang, Anna Wojtowicz
 *******************************************************************************/
package org.eclipse.ice.io.hdf;

import java.net.URI;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * An interface that provides the required operations for writing an
 * IHdfWriteable tree to an HDF5 file.
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author els
 * @generated 
 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IHdfWriter {
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation writes an IHdfWriteable to the HDF5 file at the provided
	 * URI. If any error or failure to write is encountered, then false is
	 * returned. Otherwise, true is returned.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param iHdfWriteable
	 *            <p>
	 *            The IHdfWriteable instance to write to the H5File at uri.
	 *            </p>
	 * @param uri
	 *            <p>
	 *            The URI of the H5File to write to.
	 *            </p>
	 * @return <p>
	 *         If any error or failure to write is encountered, then false is
	 *         returned. Otherwise, true is returned.
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean write(IHdfWriteable iHdfWriteable, URI uri);
}