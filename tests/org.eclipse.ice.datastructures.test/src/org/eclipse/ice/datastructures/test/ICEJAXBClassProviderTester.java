/*******************************************************************************
 * Copyright (c) 2013, 2014 UT-Battelle, LLC.
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
package org.eclipse.ice.datastructures.test;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.ice.datastructures.ICEObject.ListComponent;
import org.eclipse.ice.datastructures.form.DataComponent;
import org.eclipse.ice.datastructures.form.Material;
import org.eclipse.ice.datastructures.form.MatrixComponent;
import org.eclipse.ice.datastructures.form.ResourceComponent;
import org.eclipse.ice.datastructures.form.TableComponent;
import org.eclipse.ice.datastructures.form.TimeDataComponent;
import org.eclipse.ice.datastructures.form.TreeComposite;
import org.eclipse.ice.datastructures.form.emf.EMFComponent;
import org.eclipse.ice.datastructures.form.geometry.GeometryComponent;
import org.eclipse.ice.datastructures.form.mesh.MeshComponent;
import org.eclipse.ice.datastructures.jaxbclassprovider.ICEJAXBClassProvider;
import org.eclipse.ice.datastructures.jaxbclassprovider.IJAXBClassProvider;
import org.junit.Test;

/**
 * This class tests the ICEJAXBClassProvider. Specifically, 
 * it checks that the correct classes are created and returned by 
 * its IJAXBClassProvider.getClasses() method. 
 * 
 * @author Alex McCaskey
 *
 */
public class ICEJAXBClassProviderTester {

	/**
	 * Reference to the IJAXBClassProvider we are testing
	 */
	private IJAXBClassProvider provider;
	
	/**
	 * This method checks that we can get a valid set of 
	 * Classes from the ICEJAXBClassProvider. 
	 */
	@Test
	public void checkClassList() {
		
		// Create the class provider
		provider = new ICEJAXBClassProvider();

		// Check the name
		assertTrue("ICE JAXB Class Provider".equals(provider.getProviderName()));
		
		// Get the list of classes, make sure its 
		// not null, and make sure we have 10 of them
		List<Class> classList = provider.getClasses();
		assertNotNull(classList);
		assertEquals(11, classList.size());
		
		// Check that all the correct ones are there. 
		assertTrue(classList.contains(ResourceComponent.class));
		assertTrue(classList.contains(TableComponent.class));
		assertTrue(classList.contains(MatrixComponent.class));
		assertTrue(classList.contains(GeometryComponent.class));
		assertTrue(classList.contains(TimeDataComponent.class));
		assertTrue(classList.contains(DataComponent.class));
		assertTrue(classList.contains(TreeComposite.class));
		assertTrue(classList.contains(MeshComponent.class));
		assertTrue(classList.contains(ListComponent.class));
		assertTrue(classList.contains(EMFComponent.class));
		assertTrue(classList.contains(Material.class));
	}
}
