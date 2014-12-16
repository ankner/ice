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

import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.console.TextConsoleViewer;

/**
 * @author Taylor Patterson
 *
 */
public class VisitPythonConsoleViewer extends TextConsoleViewer {

	/**
	 * The constructor
	 * 
	 * @param parent
	 *            The {@link Composite} containing this viewer
	 * @param console
	 *            The {@link TextConsole} contained in this viewer
	 */
	public VisitPythonConsoleViewer(Composite parent, final TextConsole console) {
		super(parent, console);

		final StyledText text = getTextWidget();

		// Reserve space for the '>>> ' at the start of input lines in the
		// console
		text.addLineStyleListener(new LineStyleListener() {

			@Override
			public void lineGetStyle(LineStyleEvent event) {
				// Get the line where '>>>' should be placed
				event.bulletIndex = text.getLineAtOffset(event.lineOffset);

				// Create the StyleRange to reserve space for the preceding
				// '>>> '
				StyleRange styleRange = new StyleRange();
				styleRange.metrics = new GlyphMetrics(0, 0,
						">>> ".length() * 12);

				// Create and set the reserved space
				event.bullet = new Bullet(ST.BULLET_CUSTOM, styleRange);
			}
		});

		// Actually generate the preceding '>>> '
		text.addPaintObjectListener(new PaintObjectListener() {

			@Override
			public void paintObject(PaintObjectEvent event) {
				// Only print if this is an input line
				if (((VisitPythonConsole) console).isInput()) {
					// Define the TextLayout parameters
					TextLayout textLayout = new TextLayout(event.display);
					textLayout.setAscent(event.ascent);
					textLayout.setDescent(event.descent);
					textLayout.setFont(event.style.font);
					// Create the String (">>> ") at the beginning of each input
					// line
					textLayout.setText(">>> ");
					// Draw the text
					textLayout.draw(event.gc, event.x, event.y);
					textLayout.dispose();
				}
			}
		});
	}
}
