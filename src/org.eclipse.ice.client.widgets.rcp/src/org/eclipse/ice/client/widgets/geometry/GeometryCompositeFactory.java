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
package org.eclipse.ice.client.widgets.geometry;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Frame;

import org.eclipse.ice.datastructures.form.geometry.GeometryComponent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * This is a factory class to create Composites that contain the Geometry
 * widgets. It is meant to be implemented by whichever version of the Geometry
 * widgets is loaded, whether RAP or RCP, but called only once from the
 * ICEGeometryPage.
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author Jay Jay Billings, Taylor Patterson, Jordan H. Deyton
 * @generated 
 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class GeometryCompositeFactory {

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The actual JME3 application running the geometry under the hood.
	 * </p>
	 * 
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private SimpleApplication simpleApplication;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation renders the GeometryComponent.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param parent
	 *            <p>
	 *            The parent composite to which the Geometry composite belongs.
	 *            </p>
	 * @param geometryComp
	 *            <p>
	 *            The GeometryComponent that should be rendered.
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void renderGeometryComposite(Composite parent,
			GeometryComponent geometryComp) {
		// begin-user-code

		// Set JME3 application settings
		AppSettings settings = new AppSettings(true);
		settings.setFrameRate(60);
		settings.setRenderer(AppSettings.LWJGL_OPENGL_ANY);

		simpleApplication = new GeometryApplication();

		final GeometryApplication geometryApplication = (GeometryApplication) simpleApplication;
		geometryApplication.setSettings(settings);
		geometryApplication.setPauseOnLostFocus(false);

		// Place JME3 canvas on the parent composite

		// Create the embedded frame
		Composite embeddedFrame = new Composite(parent, SWT.EMBEDDED);

		geometryApplication.createCanvas();

		JmeCanvasContext canvasContext = (JmeCanvasContext) geometryApplication
				.getContext();
		canvasContext.setSystemListener(geometryApplication);

		// Create the SWT frame
		Frame window = SWT_AWT.new_Frame(embeddedFrame);
		Canvas canvas = canvasContext.getCanvas();
		window.add(canvas);

		window.pack();
		window.setVisible(true);

		// Load the Geometry if available
		if (geometryComp != null) {
			geometryApplication.loadGeometry(geometryComp);
		}

		// Start the canvas
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				geometryApplication.startCanvas();
			}
		});

		return;
		// end-user-code
	}

	/**
	 * This operation retrieves the SimpleApplication created by this factory.
	 * 
	 * @return The SimpleApplication created by this factory
	 */
	public SimpleApplication getApplication() {
		return simpleApplication;
	}
}