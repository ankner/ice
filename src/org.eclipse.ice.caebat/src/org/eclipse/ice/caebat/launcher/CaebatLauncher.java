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
package org.eclipse.ice.caebat.launcher;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.ice.datastructures.ICEObject.IUpdateable;
import org.eclipse.ice.datastructures.ICEObject.IUpdateableListener;
import org.eclipse.ice.datastructures.form.AllowedValueType;
import org.eclipse.ice.datastructures.form.DataComponent;
import org.eclipse.ice.datastructures.form.Entry;
import org.eclipse.ice.datastructures.form.FormStatus;
import org.eclipse.ice.datastructures.form.TableComponent;
import org.eclipse.ice.io.ips.IPSReader;
import org.eclipse.ice.io.ips.IPSWriter;
import org.eclipse.ice.item.jobLauncher.JobLauncher;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <!-- begin-UML-doc -->
 * <p>
 * This class inherits from JobLauncher form. It will create the Caebat launcher
 * so that it can remote execute the code.
 * </p>
 * <!-- end-UML-doc -->
 * 
 * @author s4h
 */
@XmlRootElement(name = "CaebatLauncher")
public class CaebatLauncher extends JobLauncher {

	/**
	 * The execution command
	 */
	private String fullExecCMD;

	/**
	 * The default CAEBAT home directory.
	 */
	private String CAEBAT_ROOT;

	/**
	 * The default IPS home directory.
	 */
	private String IPS_ROOT;

	/**
	 * A nullary constructor that delegates to the project constructor.
	 */
	public CaebatLauncher() {
		this(null);
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * The constructor. Takes an IProject argument. Calls the super constructor
	 * on JobLauncher.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param project
	 *            <p>
	 *            The project space.
	 *            </p>
	 */
	public CaebatLauncher(IProject project) {

		// begin-user-code

		// Call the JobLauncher constructor
		super(project);

		return;
		// end-user-code
	}

	/**
	 * This operations sets up some CAEBAT-specific information for the
	 * launcher, including the default project installation directory.
	 */
	protected void setupItemInfo() {
		// begin-user-code

		// Set the name and description of the Item
		setName("Caebat Launcher");
		setDescription("Caebat is a coupled battery and "
				+ "physics simulation from ORNL.");

		// Set the name of the home directory
		CAEBAT_ROOT = "/home/batsim/caebat";
		IPS_ROOT = "$IPS_ROOT";

		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This operation overrides setupForm() on JobLauncher. It will setup the
	 * paths and add the locations for the remote server addresses. It will call
	 * super.setupForm() prior to setting up the executable and hostnames.
	 * </p>
	 * <!-- end-UML-doc -->
	 */
	public void setupForm() {
		// begin-user-code
		// Setup the script to copy the data files for case 6
		// TableComponent hostTable = (TableComponent) form.getComponent(4);
		// CAEBAT_ROOT = hostTable.getRow(0).get(2).getValue();
		// String exportRoot = "export CAEBAT_ROOT=" + CAEBAT_ROOT + ";";
		// String copyCase =
		// "source `pwd`/${inputFile} >> /dev/null && cp -r $SIM_ROOT/* .;";
		String copyCase = "cp -r ${installDir}vibe/examples/case6/* .;";
		String fixSIMROOT = "sed -i.bak 's?SIM_ROOT=.*?"
				+ "SIM_ROOT='`pwd`'?g' ${inputFile};";
		// Setup the Caebat's launch script
		String CAEBATExec = "${installDir}ipsframework-code/install/bin/ips.py"
				+ " -a --log=temp.log --platform=" + IPS_ROOT
				+ "/workstation.conf --simulation=${inputFile};";
		// Setup the command stages. An explicit forward slash is used here, so
		// will only work on linux for now.
		fullExecCMD = copyCase + fixSIMROOT + CAEBATExec;
		// Setup form
		super.setupForm();
		// Stop the launcher from trying to append the input file
		setAppendInputFlag(false);
		// Setup the executable information
		setExecutable(getName(), getDescription(), this.fullExecCMD);
		// Add localhost
		addHost("localhost", "linux x86_64", CAEBAT_ROOT);
		
		// Add the input files types for the BatML files
		// Setup entries
		Entry entry = new Entry() {
			protected void setup() {
				this.setName("Use custom key-value pair file?");
				this.tag = "MODE";
				this.setDescription("Time loop's mode.  Can be Regular (true) or Explicit (false)");
				this.defaultValue = "false";
				this.value = this.defaultValue;
				this.allowedValues = new ArrayList<String>();
				this.allowedValues.add("true");
				this.allowedValues.add("false");
				this.allowedValueType = AllowedValueType.Discrete;
			}
		};

		// Add the selector to the form && make it listen for changes
		DataComponent fileComponent = (DataComponent) form.getComponent(1);
		fileComponent.addEntry(entry);
		fileComponent.retrieveEntry("Use custom key-value pair file?").register(this);
		form.removeComponent(1);
		form.addComponent(fileComponent);
		update(fileComponent.retrieveEntry("Use custom key-value pair file?"));
		
		return;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Overrides process by setting the executable correctly and then forwarding
	 * later. Still calls super.process(actionName) once the executable is set
	 * correctly for the workstation.conf file.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @param the
	 *            action name
	 * 
	 * @return The status of the action
	 */
	public FormStatus process(String actionName) {

		// begin-user-code
		/*
		 * This section will be used in future iterations String separator =
		 * System.getProperty("file.separator"); IPSReader reader = new
		 * IPSReader(); IPSWriter writer = new IPSWriter();
		 * 
		 * DataComponent fileComponent = (DataComponent) form.getComponent(1);
		 * Entry inputFileEntry = fileComponent.retrieveEntry("Input File");
		 * 
		 * IPath fileIPath = new Path(project.getLocation().toOSString() +
		 * separator + inputFileEntry.getValue()); IFile inputFile =
		 * ResourcesPlugin.getWorkspace().getRoot().getFile(fileIPath);
		 * ArrayList<Entry> simRootMatches = reader.findAll(inputFile,
		 * "SIM_ROOT=.*"); dataDir =
		 * simRootMatches.get(0).getName().split("=")[1];
		 * 
		 * writer.replace(inputFile, "SIM_ROOT=.*", "SIM_ROOT=" +
		 * getLaunchDirectory());
		 */
		// Local Declarations
		String separator = System.getProperty("file.separator");
		IPSReader reader = new IPSReader();
		IPSWriter writer = new IPSWriter();
		DataComponent fileComponent = (DataComponent) form.getComponent(1);
		Entry inputFileEntry = fileComponent.retrieveEntry("Input File");
		Entry kvPairFileEntry = fileComponent.retrieveEntry("Use custom key-value pair file?");
		IFile inputFile = project.getFile(inputFileEntry.getValue());

		// Get the Run ID that may be used to locate the simulation files
		String runID = "";
		ArrayList<Entry> runIDMatches = reader.findAll(inputFile, "RUN_ID=.*");
		if (runIDMatches != null && !runIDMatches.isEmpty()) {
			runID = runIDMatches.get(0).getName().split("=")[1];
		}

		// Get the Case Name which may also be used to locate the simulation
		// files
		String caseName = "";
		ArrayList<Entry> caseNameMatches = reader.findAll(inputFile,
				"SIM_NAME=.*");
		if (caseNameMatches != null && !caseNameMatches.isEmpty()) {
			caseName = caseNameMatches.get(0).getName().split("=")[1];
		}
		// Determine if we need to use the Run ID or the Case Name to find the
		// files
		if (caseName.contains("${RUN_ID}")) {
			caseName = runID;
		}

		// Get the base path for the simulation files
		String dataDir = "";
		ArrayList<Entry> simRootMatches = reader.findAll(inputFile,
				"SIM_ROOT=.*");
		if (simRootMatches != null && !simRootMatches.isEmpty()) {
			dataDir = simRootMatches.get(0).getName().split("=")[1];
		}
		if (dataDir.endsWith("/$SIM_NAME")) {
			dataDir = dataDir.substring(0, dataDir.length() - 10);
		} else if (dataDir.endsWith("${SIM_NAME}")) {
			dataDir = dataDir.substring(0, dataDir.length() - 12);
		} else if (dataDir.endsWith(caseName)) {
			dataDir = dataDir.substring(0, dataDir.length() - (caseName.length()+1));
		}

		// Get the input file directory for the simulation
		String inputDir = "";
		ArrayList<Entry> inputDirMatches = reader.findAll(inputFile,
				".*INPUT_DIR.*");
		if (inputDirMatches != null && !inputDirMatches.isEmpty()) {
			inputDir = inputDirMatches.get(0).getName().split("=")[1];
		}

		// If we are supplying a new KV Pair file replace it in the input file
		update(fileComponent.retrieveEntry("Use custom key-value pair file?"));		
		String setKVPerms = "";
		String backupKVFile = "";
		String mvKVPairFile = "";
		if (kvPairFileEntry.getValue() != "false") {
			String kvFileName = fileComponent.retrieveEntry("Key-value pair file").getValue();
			writer.replace(inputFile, "input_keyvalue", kvFileName);
			setKVPerms = "chmod 775 " + kvFileName + " && ";
			backupKVFile = "mv input/input_keyvalue input/input_keyvalue.bak && ";
			mvKVPairFile = "mv " + kvFileName + " input/input_keyvalue && ";
		}
		


		// Pull some information from the form
		TableComponent hostTable = (TableComponent) form.getComponent(4);
		CAEBAT_ROOT = hostTable.getRow(0).get(2).getValue();

		// Set up the execution command
		String exportRoot = "export CAEBAT_ROOT=" + CAEBAT_ROOT
				+ "/vibe/components && ";
		String copyCase = "cp -r " + dataDir + "/" + caseName + "/* . && ";
		String fixSIMROOT = "sed -i.bak 's?SIM_ROOT\\ *=\\ *.*?"
				+ "SIM_ROOT\\ =\\ '`pwd`'?g' ${inputFile} && ";
		
		// The main execution of the simulation.
		String CAEBATExec = "${installDir}ipsframework-code/install/bin/ips.py"
				+ " -a --log=temp.log --platform=" + CAEBAT_ROOT + "/vibe/examples/config/batsim.conf"
				+ " --simulation=${inputFile}; ";
		fullExecCMD = exportRoot + copyCase + setKVPerms + backupKVFile + mvKVPairFile
				+ fixSIMROOT + CAEBATExec;

		// Setup the executable information
		setExecutable(getName(), getDescription(), this.fullExecCMD);

		return super.process(actionName);

		// end-user-code

	}

	/**
	 * Override of update so that the CaebatLauncher can check if the user wants to select a 
	 * custom KV Pair file.
	 */
	public void update(IUpdateable component) {
		refreshProjectSpace();
		super.update(component);
		
		// Determine whether the file selector needs to be added to or removed from the form
		if (component.getName() == "Use custom key-value pair file?" 
				&& ((Entry) component).getValue() == "true") {
			 addInputType("Key-value pair file", "keyValueFile",
					 "Key-value pair with case parameters", ".dat");
		} else if (component.getName() == "Use custom key-value pair file?" 
				&& ((Entry) component).getValue() == "false") {
			removeInputType("Key-value pair file");
		}

	}

	/**
	 * Recursively copies a directory to a destination. This method is used to
	 * pull the simulation input files into the ICE Launch directory.
	 * 
	 * @param src
	 *            The directory to copy over
	 * @param dest
	 *            Where to put the directory
	 */
	public void copyInputDirectory(String src, String dest) {
		copyDirectory(src, dest);
	}
}