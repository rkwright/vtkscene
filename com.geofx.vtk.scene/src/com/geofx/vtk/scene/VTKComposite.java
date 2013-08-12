/*
 * Copyright (c) 2013 Ric Wright 
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/epl-v10.html
 * 
 * Contributors:
 *     Ric Wright - initial implementation
 */

package com.geofx.vtk.scene;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import vtk.vtkActor;
import vtk.vtkPanel;
import vtk.vtkRenderer;

public class VTKComposite extends Composite
{
	private java.awt.Frame 		awtFrame;
	private vtkPanel 			panel;
	
	public VTKComposite(Composite parent)
	{
		super(parent, SWT.EMBEDDED |SWT.NO_BACKGROUND);

		// this is perhaps not always necessary, but some implementations have 
		// weird artifacts (blinking, incomplete erasure) if this is not set
		System.setProperty("sun.awt.noerasebackground","true");
		
		// this tells the SWT composite to simply use the entire layout surface
		this.setLayout( new FillLayout() );   
		
		// create the bridge between Swing and SWT
		awtFrame = SWT_AWT.new_Frame(this); 		

		// create the VTK rendering surface
		panel = new vtkPanel();

	    //and add it as a child of the frame
	    awtFrame.add(panel);
	}

	public void addActor ( vtkActor actor )
	{
		panel.GetRenderer().AddActor(actor);
	}

	public void initComposite()
	{
		
	}

	public void addView(String sceneName)
	{
		
	}

	public java.awt.Frame getFrame()
	{
		return awtFrame;
	}
	
	public vtkRenderer getRenderer()
	{
		return panel != null ? panel.GetRenderer() : null;
	}
	
	public vtkPanel getPanel()
	{
		return panel;	
	}
}
