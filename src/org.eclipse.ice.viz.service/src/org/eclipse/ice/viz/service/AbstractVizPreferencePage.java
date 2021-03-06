package org.eclipse.ice.viz.service;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.FrameworkUtil;

/**
 * This is the base class for visualization service preference pages added to
 * the Eclipse IDE's Window -> Preferences dialog.
 * <p>
 * For an example of how to create a preference page using this class, see
 * {@link VizPreferencePage} and its class documentation.
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public abstract class AbstractVizPreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * The default constructor. Other constructors are available that are passed
	 * to the super class, {@link FieldEditorPreferencePage}.
	 */
	public AbstractVizPreferencePage() {
		super();
	}

	/**
	 * Creates a new field editor preference page with the given style, an empty
	 * title, and no image.
	 *
	 * @param style
	 *            either <code>GRID</code> or <code>FLAT</code>
	 */
	public AbstractVizPreferencePage(int style) {
		super(style);
	}

	/**
	 * Creates a new field editor preference page with the given title and
	 * style, but no image.
	 *
	 * @param title
	 *            the title of this preference page
	 * @param style
	 *            either <code>GRID</code> or <code>FLAT</code>
	 */
	public AbstractVizPreferencePage(String title, int style) {
		super(title, style);
	}

	/**
	 * Creates a new field editor preference page with the given title, image,
	 * and style.
	 *
	 * @param title
	 *            the title of this preference page
	 * @param image
	 *            the image for this preference page, or <code>null</code> if
	 *            none
	 * @param style
	 *            either <code>GRID</code> or <code>FLAT</code>
	 */
	public AbstractVizPreferencePage(String title, ImageDescriptor image,
			int style) {
		super(title, image, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
	 */
	protected IPreferenceStore doGetPreferenceStore() {
		// This method is used to get the page's preference store. This method
		// will be called by the super class only when the store is unset and
		// queried.

		// If we want to maintain a separate PreferenceStore for multiple
		// PreferencePages in the same bundle, we will need to design an
		// alternative mechanism rather than using the bundle's store,
		// especially if multiple preference pages in the same bundle have
		// identical preference names/keys. NOTE: If we do design a mechanism
		// for managing multiple PreferenceStores in the same bundle,
		// AbstractVizPreferenceInitializer's getPreferenceStore() method may
		// also need to be updated.
		String id = FrameworkUtil.getBundle(getClass()).getSymbolicName();
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, id);
	}

}
