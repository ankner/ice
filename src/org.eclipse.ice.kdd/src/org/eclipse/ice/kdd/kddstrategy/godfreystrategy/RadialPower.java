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
package org.eclipse.ice.kdd.kddstrategy.godfreystrategy;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ice.kdd.kddmath.IDataMatrix;
import org.eclipse.ice.kdd.kddmath.KDDMatrix;

/**
 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
 * 
 * @author Alex McCaskey
 * @generated 
 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class RadialPower extends GodfreySubStrategy {
	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private PinPowerDifference differences;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Reference to the radial power.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private ArrayList<KDDMatrix> radialPower;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Reference to the generated radial power for the pin power differences.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private ArrayList<KDDMatrix> radialPowerDiff;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Reference to the average radial power.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Double radialPowerAverage;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Reference to the average radial power difference.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Double radialPowerDiffAverage;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Reference to the RMS average radial power.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Double radialPowerRMS;

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Reference to the RMS radial power difference.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private Double radialPowerDiffRMS;

	private boolean calculateDiffs;

	/**
	 * <!-- begin-UML-doc --> <!-- end-UML-doc -->
	 * 
	 * @param difference
	 * @param pinPowers
	 * @param refPinPowers
	 * @param weights
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public RadialPower(PinPowerDifference difference,
			HashMap<Integer, ArrayList<IDataMatrix>> pinPowers,
			HashMap<Integer, ArrayList<IDataMatrix>> refPinPowers,
			HashMap<Integer, ArrayList<KDDMatrix>> weights) {
		// begin-user-code
		super(pinPowers, refPinPowers, weights);
		differences = difference;

		assetName = "Radial Power Godfrey Sub-Strategy";
		radialPower = new ArrayList<KDDMatrix>();
		radialPowerDiff = new ArrayList<KDDMatrix>();

		if (difference == null) {
			calculateDiffs = false;
		} else {
			differences = difference;
			calculateDiffs = true;
		}
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This method allows subclasses to implement a unique clustering or anomaly
	 * detection algorithm and produce a KDDAnalysisAsset for clients to display
	 * and manipulate.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public boolean executeStrategy() {
		// begin-user-code
		// Local Declarations
		int nAxial = loadedPinPowers.get(0).size();
		int nAssemblies = loadedPinPowers.size();
		int nRows = loadedPinPowers.get(0).get(0).numberOfRows();
		int nCols = loadedPinPowers.get(0).get(0).numberOfColumns();
		double sum = 0.0, weightSum = 0.0;
		double diffSum = 0.0, rmsSum = 0.0, diffSumRms = 0.0;
		ArrayList<KDDMatrix> dr = new ArrayList<KDDMatrix>();
		KDDMatrix matrix, diffMat, drMat;

		// Make sure we run the difference algorithm.
		if (calculateDiffs && !differences.executeStrategy()) {
			return false;
		}

		// Calculate the Radial Power and Radial Power Diff
		for (int l = 0; l < nAssemblies; l++) {
			matrix = new KDDMatrix(nRows, nCols);
			diffMat = new KDDMatrix(nRows, nCols);
			drMat = new KDDMatrix(nRows, nCols);
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					for (int k = 0; k < nAxial; k++) {
						sum = sum
								+ loadedPinPowers.get(l).get(k)
										.getElementValue(i, j)
								* weights.get(l).get(k).getElement(i, j);
						if (calculateDiffs) {
							diffSum = diffSum
									+ differences.getPinPowerDifference()
											.get(l).get(k).getElement(i, j)
									* weights.get(l).get(k).getElement(i, j);
						}
						weightSum = weightSum
								+ weights.get(l).get(k).getElement(i, j);
					}
					matrix.setElement(i, j,
							Math.sqrt(Math.abs(sum / weightSum)));
					if (calculateDiffs) {
						diffMat.setElement(i, j,
								Math.sqrt(Math.abs(diffSum / weightSum)));
						radialPowerDiff.add(diffMat);
					}
					drMat.setElement(i, j, weightSum);
					sum = 0.0;
					weightSum = 0.0;
					diffSum = 0.0;
					radialPower.add(matrix);
					dr.add(drMat);
				}
			}

		}

		// Calculate Radial avgs and rms
		for (int l = 0; l < nAssemblies; l++) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					for (int k = 0; k < nAxial; k++) {
						sum = sum
								+ loadedPinPowers.get(l).get(k)
										.getElementValue(i, j)
								* dr.get(l).getElement(i, j);

						rmsSum = rmsSum
								+ Math.pow(loadedPinPowers.get(l).get(k)
										.getElementValue(i, j), 2)
								* dr.get(l).getElement(i, j);
						if (calculateDiffs) {
							diffSum = diffSum
									+ differences.getPinPowerDifference()
											.get(l).get(k).getElement(i, j)
									* dr.get(l).getElement(i, j);
							diffSumRms = diffSumRms
									+ Math.pow(differences
											.getPinPowerDifference().get(l)
											.get(k).getElement(i, j), 2)
									* dr.get(l).getElement(i, j);
						}
						weightSum = weightSum + dr.get(l).getElement(i, j);
					}
				}
			}
		}

		// Set the averages and rms.
		radialPowerAverage = Math.abs(sum / weightSum);
		radialPowerRMS = Math.sqrt(Math.abs(rmsSum / weightSum));

		if (calculateDiffs) {
			radialPowerDiffAverage = Math.abs(diffSum / weightSum);
			radialPowerDiffRMS = Math.sqrt(Math.abs(diffSumRms / weightSum));
		}

		return createAsset();
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * This abstract method is for subclasses to implement their specific
	 * creation algorithms.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	private boolean createAsset() {
		// begin-user-code

		ArrayList<String> fileContents = new ArrayList<String>();
		String line = "", contents = "";
		DecimalFormat formatter = new DecimalFormat("#.####");

		// Get the default project, which should be
		// the only element in getProjects()
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root.getProjects().length == 0) {
			return false;
		}

		// Get the IProject
		IProject project = root.getProjects()[0];

		// Create a handle to the file we are going to write to
		IFile file = project.getFile("radialpower.txt");

		// If this file exists, then we've already used
		// it to write cluster data, so lets create a file
		// with a different name
		if (file.exists()) {
			int counter = 1;
			while (file.exists()) {
				file = project.getFile("radialpower_" + String.valueOf(counter)
						+ ".txt");
				counter++;
			}
		}

		// Get the number of axial levels
		int nAssemblies = loadedPinPowers.size();
		int nRows = loadedPinPowers.get(0).get(0).numberOfRows();
		int nCols = loadedPinPowers.get(0).get(0).numberOfColumns();

		fileContents.add("\n---------- Radial Results ----------");
		fileContents.add("\nRadial Power\n");
		for (int l = 0; l < nAssemblies; l++) {
			fileContents.add("Assembly " + l);
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					line = line
							+ formatter.format(radialPower.get(l).getElement(i,
									j)) + " ";
				}
				fileContents.add(line);
				line = "";
			}
		}

		if (calculateDiffs) {
			fileContents.add("\nRadial Power Difference\n");
			for (int l = 0; l < nAssemblies; l++) {
				fileContents.add("Assembly " + l);
				for (int i = 0; i < nRows; i++) {
					for (int j = 0; j < nCols; j++) {
						line = line
								+ formatter.format(radialPowerDiff.get(l)
										.getElement(i, j)) + " ";
					}
					fileContents.add(line);
					line = "";
				}
			}
		}

		fileContents.add("");
		fileContents.add("Average Radial Power: "
				+ formatter.format(radialPowerAverage));
		fileContents.add("RMS Radial Power: "
				+ formatter.format(radialPowerRMS));

		if (calculateDiffs) {
			fileContents.add("Average Radial Power Difference: "
					+ formatter.format(radialPowerDiffAverage));
			fileContents.add("RMS Radial Power Difference: "
					+ formatter.format(radialPowerDiffRMS));
		}

		// Convert the ArrayList to one string
		// so we can use the getBytes method
		for (String s : fileContents) {
			contents = contents + s + "\n";
		}

		// Create the IFile with a ByteArrayInputStream
		try {
			file.create(new ByteArrayInputStream(contents.getBytes()), false,
					null);
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}

		// set the URI
		uri = file.getLocationURI();

		return true;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return the radial power difference.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ArrayList<KDDMatrix> getRadialPower() {
		// begin-user-code
		return radialPower;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return the radial power pin power difference.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public ArrayList<KDDMatrix> getRadialPowerDifference() {
		// begin-user-code
		return radialPowerDiff;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return the average radial power.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Double getRadialPowerAverage() {
		// begin-user-code
		return radialPowerAverage;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return the average radial power difference.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Double getRadialPowerDifferenceAverage() {
		// begin-user-code
		return radialPowerDiffAverage;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return the RMS radial power.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Double getRadialPowerRMS() {
		// begin-user-code
		return radialPowerRMS;
		// end-user-code
	}

	/**
	 * <!-- begin-UML-doc -->
	 * <p>
	 * Return the RMS radial power difference.
	 * </p>
	 * <!-- end-UML-doc -->
	 * 
	 * @return
	 * @generated 
	 *            "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public Double getRadialPowerDifferenceRMS() {
		// begin-user-code
		return radialPowerDiffRMS;
		// end-user-code
	}
}