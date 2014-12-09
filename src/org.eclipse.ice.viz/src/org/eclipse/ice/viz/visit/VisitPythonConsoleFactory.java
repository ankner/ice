package org.eclipse.ice.viz.visit;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleView;

/**
 * @author Taylor Patterson
 *
 */
public class VisitPythonConsoleFactory implements IConsoleFactory {

	/**
	 * (non-Javadoc)
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
			final VisitPythonConsole console = new VisitPythonConsole(
					"VisIt Python Interface", null);

			// Create the listener for text entered into the console
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

}
