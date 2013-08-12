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

package com.geofx.vtk.samples;

import vtk.vtkActor;
import vtk.vtkConeSource;
import vtk.vtkPolyDataMapper;

import com.geofx.vtk.scene.VTKComposite;
import com.geofx.vtk.scene.VTKScene;

public class Cone1Scene extends VTKScene 
{
	public Cone1Scene( VTKComposite composite )
	{
		super(composite);
		
		// build VTK Pipeline
		vtkConeSource cone = new vtkConeSource();
		cone.SetResolution(20);
		cone.SetHeight(0.3);
		cone.SetRadius(0.1);

		composite.getRenderer().SetBackground(0.4, 0.2, 0.1);

		vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
		coneMapper.SetInputConnection(cone.GetOutputPort());

		vtkActor coneActor = new vtkActor();
		coneActor.SetMapper(coneMapper);

		composite.addActor(coneActor);
	}
	/**
	 * a default constructor used by the ClassInfo enumerator
	 */ 
	public Cone1Scene()
	{
		super();
	}

	
	@Override
	public String getLabel()
	{
		return "VTK Sample Cone 1";
	}

	@Override
	public String getDescription()
	{
		return "The first VTK sample file of a simple cone";
	}

}
