/*******************************************************************************
 * Copyright (c) 2012, 2014 UT-Battelle, LLC.
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
package org.eclipse.ice.materials.ui;

import org.eclipse.ice.datastructures.form.Material;
import org.eclipse.ice.materials.IMaterialsDatabase;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This class presents a Material as a table with properties.
 * 
 * @author Jay Jay Billings
 *
 */
public class MaterialDetailsPage implements IDetailsPage {

	/**
	 * The Materials Database that needs to be updated if properties change.
	 */
	IMaterialsDatabase database;

	/**
	 * The currently presented material
	 */
	Material material;

	/**
	 * The Form that manages this details page
	 */
	IManagedForm managedForm;

	/**
	 * The table viewer that shows the material's properties.
	 */
	TableViewer tableViewer;

	/**
	 * The constructor
	 * 
	 * @param materialsDatabase
	 *            The Materials Database
	 */
	public MaterialDetailsPage(IMaterialsDatabase materialsDatabase) {
		database = materialsDatabase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.IFormPart#initialize(org.eclipse.ui.forms.IManagedForm
	 * )
	 */
	@Override
	public void initialize(IManagedForm form) {
		managedForm = form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#commit(boolean)
	 */
	@Override
	public void commit(boolean onSave) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#setFormInput(java.lang.Object)
	 */
	@Override
	public boolean setFormInput(Object input) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#isStale()
	 */
	@Override
	public boolean isStale() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse
	 * .ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {

		// Grab the selection
		Object structuredSelection = ((IStructuredSelection) selection)
				.getFirstElement();
		if (structuredSelection instanceof Material) {
			// Set the input to the properties
			material = (Material) structuredSelection;
			tableViewer.setInput(material.getProperties());
			// Fix the column width
			Table table = tableViewer.getTable();
			for (TableColumn column : table.getColumns()) {
				column.pack();
			}
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createContents(Composite parent) {

		// Set the layout for the parent
		GridLayout parentGridLayout = new GridLayout(1, true);
		parent.setLayout(parentGridLayout);

		// Put in an overall section to hold the master details block. It is
		// just a stylistic thing for the most part.
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.TITLE_BAR
				| Section.DESCRIPTION | Section.EXPANDED);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		section.setLayoutData(gridData);
		section.setLayout(new GridLayout(1, true));
		section.setText("Material Properties");
		section.setDescription("Edit the material's properties");

		// Create the area in which the block will be rendered - the
		// "section client"
		Composite sectionClient = toolkit.createComposite(section);
		// Configure the layout to be greedy.
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginWidth = 2;
		gridLayout.marginHeight = 2;
		// Set the layout and the layout data
		sectionClient.setLayout(gridLayout);
		sectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));
		// Finally tell the section about its client
		section.setClient(sectionClient);

		// Create a table viewer to display the materials properties
		tableViewer = new TableViewer(sectionClient, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);

		// Create the property name column
		TableViewerColumn nameColumn = new TableViewerColumn(tableViewer,
				SWT.CENTER);
		nameColumn.getColumn().setText("Property");
		nameColumn.getColumn().setToolTipText(
				"The name of the material property");
		nameColumn.setLabelProvider(new MaterialCellLabelProvider());
		nameColumn.getColumn().pack();
		// Create the property value column
		TableViewerColumn valueColumn = new TableViewerColumn(tableViewer,
				SWT.CENTER);
		valueColumn.getColumn().setText("Value");
		valueColumn.getColumn().setToolTipText(
				"The value of the material property");
		valueColumn.getColumn().pack();
		valueColumn.setLabelProvider(new MaterialCellLabelProvider());
		// Add the columns
		String[] names = { "Property", "Value" };
		tableViewer.setColumnProperties(names);

		// Set the content provider and the layout information on the
		// tableViewer
		tableViewer.setContentProvider(new MaterialPropertyContentProvider());
		Table table = tableViewer.getTable();
		table.setLayout(new GridLayout(1, true));
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Add a composite for holding the Add and Delete buttons for adding
		// or removing properties
		Composite buttonComposite = new Composite(sectionClient, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(1, false));
		buttonComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false,
				true, 1, 1));
		// Create a listener that will throw up an error message since the Add
		// and Delete operations are not yet supported. The error message is
		// just a simple JFace message dialog that is opened when either button
		// is pressed.
		String title = "Operation Unsupported";
		String msg = "Adding and deleting properties"
				+ " is not yet supported.";
		String[] labels = { "OK" };
		final MessageDialog dialog = new MessageDialog(parent.getShell(),
				title, null, msg, MessageDialog.ERROR, labels, 0);
		SelectionListener errorListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				dialog.open();
			}
		};
		// Create the Add button
		Button addMaterialButton = new Button(buttonComposite, SWT.PUSH);
		addMaterialButton.setText("Add");
		// Set the error listener for now until the delete operation is
		// supported.
		addMaterialButton.addSelectionListener(errorListener);
		// Create the Delete button
		Button deleteMaterialButton = new Button(buttonComposite, SWT.PUSH);
		deleteMaterialButton.setText("Delete");
		// Set the error listener for now until the delete operation is
		// supported
		deleteMaterialButton.addSelectionListener(errorListener);

		return;
	}

}
