package org.eclipse.ice.viz.service;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.FrameworkUtil;

/**
 * This is the base class for setting default preferences for visualization
 * service preference pages added to the Eclipse IDE's Window -> Preferences
 * dialog.
 * <p>
 * For an example of how to intitialize defaults for a preference page using
 * this class, see {@link VizPreferenceInitializer} and its class documentation.
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public abstract class AbstractVizPreferenceInitializer extends
		AbstractPreferenceInitializer {

	/**
	 * A reference to the associated preference page's {@link IPreferenceStore}.
	 * If this has been determined previously, then it should be returned in
	 * {@link #getPreferenceStore()}.
	 */
	private IPreferenceStore preferenceStore = null;

	/**
	 * Gets the {@link IPreferenceStore} for the associated preference page.
	 * 
	 * @return The {@code IPreferenceStore} whose defaults should be set.
	 */
	protected IPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
			// Get the PreferenceStore for the bundle.
			String id = FrameworkUtil.getBundle(getClass()).getSymbolicName();
			preferenceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE,
					id);
		}
		return preferenceStore;
	}

}
