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
package org.eclipse.ice.item.utilities.moose;

import java.util.ArrayList;

import org.eclipse.ice.datastructures.form.AllowedValueType;
import org.eclipse.ice.datastructures.form.Entry;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * This class represents a MOOSE input Parameter. This class is parsed from and
 * written to YAML using SnakeYAML's Java Bean parser. It can also be loaded
 * from an Entry, although this should be used sparingly since the conversion
 * from an ICE Entry to a MOOSE parameter is not a 1-1 mapping. It is only used
 * in ICE for writing input files, at the moment.
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author Jay Jay Billings
 * @generated 
 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class Parameter {
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The name of the parameter
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String name = "";
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The flag that indicates whether or not this parameter is required. True
	 * if it is required, false if not.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean required;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The flag that indicates whether or not this parameter is enabled (ie. if
	 * it is commented out or not). False if the parameter is commented out,
	 * otherwise true.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean enabled;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The default value of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String _default = "";
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The C++ type of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String cpp_type = "";
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The list of options of the parameter (if any). Not all parameters have
	 * options.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private ArrayList<String> options = null;
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The name of the group to which the parameter belongs.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String group_name = "";
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * A description of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private String description = "";

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation retrieves the name of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The name
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getName() {
		// begin-user-code
		return name;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation indicates if the parameter is required. True if required,
	 * false if not.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         True if required, false if not
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean isRequired() {
		// begin-user-code
		return required;
		// end-user-code
	}

	/**
	 * Returns if the parameter is enabled or not (ie. commented out).
	 * 
	 * @return	True if the parameter is enabled, false if it is commented out.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation retrieves the default value of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The default value
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getDefault() {
		// begin-user-code
		return _default;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation retrieves the C++ type of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The type
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getCpp_type() {
		// begin-user-code
		return cpp_type;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation retrieves the list of options (if any)
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The list of options.
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ArrayList<String> getOptions() {
		// begin-user-code
		return options;
		// end-user-code
	}
	
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation retrieves the group name of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The group name
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getGroup_name() {
		// begin-user-code
		return group_name;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation retrieves the description of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The description
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String getDescription() {
		// begin-user-code
		return description;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the name of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param paramName
	 *            <p>
	 *            The name
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setName(String paramName) {
		// begin-user-code
		name = paramName;
		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets specifies whether or not the parameter is required.
	 * True for required, false if not.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param flag
	 *            <p>
	 *            True if required, false if not
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setRequired(boolean flag) {
		// begin-user-code
		required = flag;
		return;
		// end-user-code
	}
	
	/**
	 * This method sets if the parameter is currently enabled (ie. not
	 * commented out).
	 * 
	 * @param flag	True if the parameter is enabled, false if it is commented
	 * 				out.
	 */
	public void setEnabled(boolean flag) {
		enabled = flag;
		return;
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the default value of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param defaultValue
	 *            <p>
	 *            The default value
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setDefault(String defaultValue) {
		// begin-user-code
		_default = defaultValue;
		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the C++ type of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param type
	 *            <p>
	 *            The type
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setCpp_type(String type) {
		// begin-user-code
		cpp_type = type;
		return;
		// end-user-code
	}
	
	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets options of the parameter (if any).
	 * <!-- end-UML-doc -->
	 * 
	 * @param type
	 *            <p>
	 *            The type
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setOptions(String optionsStr) {
		// begin-user-code
		
		// Initiate the ArrayList if it hasn't been already
		if (options == null) {
			options = new ArrayList<String>();
		}
		
		// Break up the input string of options at each whitespace
		String[] splitOptionsStr = optionsStr.split("\\s+");
		
		// Add them to the ArrayList
		for (int i = 0; i < splitOptionsStr.length; i++) {
			options.add(splitOptionsStr[i]);
		}
		
		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the group name of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param gName
	 *            <p>
	 *            The group name
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setGroup_name(String gName) {
		// begin-user-code
		group_name = gName;
		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation sets the description of the parameter.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param desc
	 *            <p>
	 *            The description
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void setDescription(String desc) {
		// begin-user-code
		description = desc;
		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation returns this parameter as an ICE Entry with
	 * AllowedValueType.UNDEFINED.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The data components
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Entry toEntry() {
		// begin-user-code

		// Local Declarations
		Entry entry = null;

		// Setup the Entry Entry
		entry = new Entry() {
			@Override
			// Check the cpp_type and determine what to set the allowed
			// value types as
			protected void setup() {
				// If the type is discrete (MooseEnum) and the options list
				// isn't empty
				if ((("MooseEnum").equals(Parameter.this.cpp_type)
						|| ("MultiMooseEnum").equals(Parameter.this.cpp_type))
						&& options != null && !options.isEmpty()) {
					// Limit the type to discrete values
					allowedValueType = AllowedValueType.Discrete;
					// Set the allowed values
					allowedValues = options;
					// Set the default value and description
					String value = Parameter.this.getDefault();
					defaultValue = (allowedValues.contains(value) ?  
											value : allowedValues.get(0));
					description = Parameter.this.getDescription();
				}
				// If the value type is boolean
				else if (("bool").equals(Parameter.this.cpp_type)) {
					// Limit the type to discrete values
					allowedValueType = AllowedValueType.Discrete;
					// Set the allowed values
					allowedValues.add("true");
					allowedValues.add("false");
					// Set the default value and description
					defaultValue = (Parameter.this.getDefault().equals(0)) ? 
							"false" : "true";
					description = Parameter.this.getDescription();
				} 
				// Otherwise, for all other parameters
				else {
					allowedValueType = AllowedValueType.Undefined;
					defaultValue = Parameter.this.getDefault();
					description = Parameter.this.getDescription();
				}
			}
		};
		entry.setName(getName());
		entry.setDescription(getDescription());
		entry.setRequired(required);
		entry.setTag(enabled ? "true" : "false");
		
		return entry;

		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation writes the name and value of the parameter to a string as
	 * "name = value" (minus the quotations of course).
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return <p>
	 *         The parameter as a string.
	 *         </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public String toString() {
		// begin-user-code
		return name + " = " + _default;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation will load a Parameter from an Entry following the same
	 * rules as in toEntry(). This operation should ONLY be used to convert an
	 * Entry to a Parameter for writing a MOOSE input file.
	 * </p>
	 * <p>
	 * The parameter can not reconstruct the cpp_type from the Entry, so only
	 * the name, description and value (stored as default) are converted.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param entry
	 *            <p>
	 *            The Entry to load into the Parameter.
	 *            </p>
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void fromEntry(Entry entry) {
		// begin-user-code

		// Only do this with a real Entry
		if (entry != null) {
			name = entry.getName();
			description = entry.getDescription();
			_default = entry.getValue();
			required = entry.isRequired();
			enabled = !"false".equalsIgnoreCase(entry.getTag());
			options = entry.getAllowedValues();
		}

		// end-user-code
	}

	/**
	 * This operation reads the parameter from a string of the form name =
	 * defaultValue and loads the name and default value.
	 * 
	 * If the string is invalid then the parameter will remain unchanged.
	 * 
	 * @param paramString
	 *            The parameter as a string.
	 */
	public void fromString(String paramString) {

		// Get the quantities if the string is formatted correctly.
		if (paramString != null && paramString.contains("=")) {
			// Split the string
			String[] pieces = paramString.split("=");
			// Get the pieces
			name = pieces[0].trim();
			_default = pieces[1].trim();
		}

		return;
	}
}