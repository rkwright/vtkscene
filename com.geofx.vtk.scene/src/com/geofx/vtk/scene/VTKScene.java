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

/**
 * Creates a VTK scene. 
 * 
 */
public abstract class VTKScene 
{
	protected VTKComposite 		vtkComposite;

	/**
	 * Creates a new scene owned by the specified parent component.
	 * 
	 * @param parent   The Composite which holds the actual GLCanvas
	 */
	public VTKScene( VTKComposite composite )
	{
		System.out.println("VTKScene - constructor");

		vtkComposite = composite;  
		
		//vtkComposite.initComposite();
	}

	// a default constructor used by the ClassInfo enumerator
	public VTKScene()
	{		
	}

	public abstract String getLabel();
	
	public abstract String getDescription();

	// normally nothing to do here, but can be overridden to dispose of resources properly
	public void dispose()
	{
	}

	public void render()
	{
	}
}

