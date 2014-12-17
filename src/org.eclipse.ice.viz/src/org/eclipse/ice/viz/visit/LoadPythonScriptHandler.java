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

import gov.lbnl.visit.swt.VisItSwtWidget;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Taylor Patterson
 *
 */
public class LoadPythonScriptHandler extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// Get the window and the shell.
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		Shell shell = window.getShell();

		// Check for a VisitEditor, VisItSwtWidget, and VisitPythonConsole. //
		// Get the editor part
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		// If the editor is null, there is no chance VisIt has been initialized.
		if (editorPart == null
				|| !editorPart.getSite().getId().equals(VisitEditor.ID)) {
			// Present an error dialog and return
			MessageDialog.openError(shell, "VisIt Connection Error",
					"Please connect to a running VisIt client or "
							+ "select a previously opened VisIt Editor prior "
							+ "to attempting to execute Python methods.");
			return null;
		}

		// Get the VisItSWTWidget from the editor
		VisitEditor editor = (VisitEditor) editorPart;
		VisItSwtWidget visitWidget = editor.getVizWidget();

		// Make sure the widget is initialized
		if (visitWidget == null || !visitWidget.hasInitialized()) {
			// Present an error message and return
			MessageDialog.openError(shell, "VisIt Not Initialized",
					"Please connect to a running VisIt client prior to "
							+ "attempting to execute Python methods.");
			return null;
		}

		// Get the console
		IConsoleView consoleView = (IConsoleView) window.getActivePage()
				.getActivePart();
		VisitPythonConsole console = (VisitPythonConsole) consoleView
				.getConsole();

		// Make sure the console is initialized
		if (console == null) {
			// Present an error message and return
			MessageDialog.openError(shell, "VisIt Not Initialized",
					"Please connect to a running VisIt client prior to "
							+ "attempting to execute Python methods.");
			return null;
		}
		// ---------------------------------------------------------------- //

		// Create an SWT FileDialog with the option to select multiple files.
		FileDialog dialog = new FileDialog(shell, SWT.OPEN | SWT.MULTI);

		// Create a filter to limit the displayed files to those with a .py
		// extension.
		String[] filterNames = new String[] { ".py Files" };
		String[] filterExtensions = new String[] { "*.py" };

		// Set the dialog's file filters.
		dialog.setFilterNames(filterNames);
		dialog.setFilterExtensions(filterExtensions);

		// Get the OS file separator character.
		String separator = System.getProperty("file.separator");

		// Set the default location.
		String filterPath = System.getProperty("user.home") + separator
				+ "ICEFiles" + separator + "default" + separator;
		dialog.setFilterPath(filterPath);

		// If a file was selected in the dialog, process it.
		if (dialog.open() != null) {

			// Get the selected file(s)
			String[] files = dialog.getFileNames();

			if (files != null) {
				// Loop through the selected files
				for (String file : files) {
					try {
						// Get the full file path
						String path = dialog.getFilterPath() + separator + file;
						// Initialize the readers and buffers
						FileReader fReader = new FileReader(path);
						BufferedReader bReader = new BufferedReader(fReader);
						StringBuffer strBuffer = new StringBuffer();
						// Loop through the file (via the
						// BufferedReader) to create a single String.
						String line = bReader.readLine();
						while (line != null) {
							strBuffer.append(line + "\n");
							line = bReader.readLine();
						}

						// Close the reader
						bReader.close();
						// Convert the StringBuffer to a String
						String fullFileStr = strBuffer.toString().trim() + "\n";
						// Format the String of commands
						String text = ">>> " + fullFileStr;
						text = text.replace("\n", "\n>>> ");
						text = text.replace("\n>>>  ", "\n... ");

						// Create an output stream to write to the console
						IOConsoleOutputStream outStream = console
								.newOutputStream();
						outStream.setActivateOnWrite(true);
						outStream.write(text.trim() + "\n");
						// Close the output stream we are done using
						outStream.close();

						// Call the VisIt widget method to process these
						// commands
						visitWidget.getViewerMethods().processCommands(
								fullFileStr);
					} catch (Exception e1) {
						// Complain if needed
						e1.printStackTrace();
					}
				}
			}
		}

		return null;
	}

}
