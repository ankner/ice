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
package org.eclipse.ice.viz.visit;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.console.TextConsolePage;
import org.eclipse.ui.console.TextConsoleViewer;

/**
 * @author Taylor Patterson
 *
 */
public class VisitPythonConsolePage extends TextConsolePage {

	/**
	 * @param console
	 * @param view
	 */
	public VisitPythonConsolePage(TextConsole console, IConsoleView view) {
		super(console, view);
	}

	/**
	 * @see TextConsolePage#createViewer(Composite)
	 */
	protected TextConsoleViewer createViewer(Composite parent) {
		return new VisitPythonConsoleViewer(parent,
				(VisitPythonConsole) this.getConsole());
	}
}
