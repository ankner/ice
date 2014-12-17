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

import java.io.IOException;
import java.util.List;

import gov.lbnl.visit.swt.VisItSwtWidget;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsoleOutputStream;

import visit.java.client.AttributeSubject;
import visit.java.client.AttributeSubject.AttributeSubjectCallback;

/**
 * This {@link IConsoleFactory} subclass creates {@link VisitPythonConsole}
 * instances. This class manages the capture of user input and outputs text
 * returned by VisIt.
 * 
 * @author Taylor Patterson
 *
 */
public class VisitPythonConsoleFactory implements IConsoleFactory {

	/**
	 * The {@link VisItSwtWidget} for which a {@link VisitPythonConsole} will be
	 * created.
	 */
	private VisItSwtWidget visitWidget;

	/**
	 * The {@link VisitPythonConsole} instance created by this factory.
	 */
	private VisitPythonConsole console;

	/**
	 * The {@link AttributeSubjectCallback} used by the VisIt widget.
	 */
	private AttributeSubjectCallback attSubCallback;

	/**
	 * The constructor
	 * 
	 * @param vizWidget
	 *            The {@link VisItSwtWidget} for which a
	 *            {@link VisitPythonConsole} will be created.
	 */
	public VisitPythonConsoleFactory(VisItSwtWidget vizWidget) {
		visitWidget = vizWidget;
	}

	/**
	 * Creates a new {@link VisitPythonConsole} in the console view.
	 * 
	 * @see org.eclipse.ui.console.IConsoleFactory#openConsole()
	 */
	@Override
	public void openConsole() {

		// Get the currently active page
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			// Load the console view
			IConsoleView consoleView = (IConsoleView) page
					.showView(IConsoleConstants.ID_CONSOLE_VIEW);
			// Create the console instance that will provide the Python
			// interface for VisIt
			console = new VisitPythonConsole("VisIt Python Interface", null,
					visitWidget);
			console.createPage(consoleView);

			// Create the listener for text entered into the console
			createInputListener();

			// Create the output stream for information returned by VisIt
			createOutputStream();

			// Add the console to the console manager
			ConsolePlugin.getDefault().getConsoleManager()
					.addConsoles(new IConsole[] { console });
			// Show the console in the view
			consoleView.display(console);
		} catch (PartInitException e) {
			// Complain
			System.out.println("VisitPythonConsole Message: Unable to "
					+ "initialize the Python interface");
			e.printStackTrace();
		}

	}

	/**
	 * Create the listener to process the commands entered by the user when the
	 * 'Enter' key is pressed in the console.
	 */
	private void createInputListener() {

		IDocument document = console.getDocument();
		document.addDocumentListener(new IDocumentListener() {

			@Override
			public void documentChanged(DocumentEvent event) {
				// Detect the enter key
				// TODO Check cross-platform compatibility
				if ("\n".equals(event.getText())) {
					// Process the user's entry
					console.executeCommand();
				}
			}

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
				// Do nothing for now.
			}
		});

	}

	/**
	 * Create the output stream to print information returned by VisIt to the
	 * console.
	 */
	private void createOutputStream() {

		// Create the AttributeSubjectCallback
		attSubCallback = new AttributeSubjectCallback() {

			@Override
			public boolean update(AttributeSubject subject) {
				// Get the callback contents
				String name = subject.getAsString("methodName");

				// Make sure it is something we want
				if (!"AcceptRecordedMacro".equals(name)) {
					return false;
				}

				// Get the String from VisIt
				final List<String> vec = subject
						.getAsStringVector("stringArgs");

				// Sync with the display and print the String to the console
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						try {
							// Initiate an output stream to write callback
							// contents to the console
							IOConsoleOutputStream outStream = console
									.newOutputStream();
							outStream.setActivateOnWrite(true);
							// Write the Strings from VisIt to the console
							for (int i = 0; i < vec.size(); ++i) {
								outStream.write(vec.get(i) + "\n");
							}
							// Close the output stream we are done using
							outStream.close();
						} catch (IOException e) {
							System.out.println("VisitPythonConsoleFactory "
									+ "Message: IOConsoleOutputStream error.");
							e.printStackTrace();
						}
					}
				});

				return true;
			}
		};

		// Register the AttributeSubjectCallback with the VisIt widget
		visitWidget.getViewerState().registerCallback("ClientMethod",
				attSubCallback);

	}
}
