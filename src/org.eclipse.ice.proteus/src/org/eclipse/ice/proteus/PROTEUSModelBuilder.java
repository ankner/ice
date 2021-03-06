/*******************************************************************************
 * Copyright (c) 2014 UT-Battelle, LLC.
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
package org.eclipse.ice.proteus;

import org.eclipse.core.resources.IProject;
import org.eclipse.ice.item.Item;
import org.eclipse.ice.item.ItemBuilder;
import org.eclipse.ice.item.ItemType;

/** 
 * <!-- begin-UML-doc -->
 * <p>An ItemBuilder for building PROTEUS models.</p>
 * <!-- end-UML-doc -->
 * @author Jay Jay Billings
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class PROTEUSModelBuilder implements ItemBuilder {
	/**
	 * The name
	 */
	public static final String name = "PROTEUS Model Builder";
	
	/**
	 * The Item type
	 */
	public static final ItemType type = ItemType.Model;
	
	/** 
	 * (non-Javadoc)
	 * @see ItemBuilder#getItemName()
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getItemName() {
		// begin-user-code
		return name;
		// end-user-code
	}

	/** 
	 * (non-Javadoc)
	 * @see ItemBuilder#getItemType()
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ItemType getItemType() {
		// begin-user-code
		return type;
		// end-user-code
	}

	/** 
	 * (non-Javadoc)
	 * @see ItemBuilder#build(IProject projectSpace)
	 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Item build(IProject projectSpace) {
		// begin-user-code
		
		PROTEUSModel model = new PROTEUSModel(projectSpace);
		model.setItemBuilderName(name);

		return model;
		// end-user-code
	}
}