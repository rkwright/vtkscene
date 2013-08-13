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

public class Cone2Scene extends VTKScene
{
	public Cone2Scene(VTKComposite composite)
	{
		super(composite);

		// set up the VTK pipeline
		vtkConeSource cone = new vtkConeSource();
		cone.SetHeight(0.3);
		cone.SetRadius(0.1);
		cone.SetResolution(10);

		vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
		coneMapper.SetInputConnection(cone.GetOutputPort());
		vtkActor coneActor = new vtkActor();
		coneActor.SetMapper(coneMapper);

		composite.getRenderer().AddActor(coneActor);
		composite.getRenderer().SetBackground(0.1, 0.2, 0.4);

		// now we loop over 360 degrees and render the cone each time
		// Note that the first time we are constructed the panel isn't ready
		if (composite.getPanel().isValid())
		{
			for (int i = 0; i < 360; i++)
			{
				// render the image
				composite.getPanel().Render();
				// rotate the active camera by one degree
				// composite.getRenderer().GetActiveCamera().Azimuth(1);
				composite.getRenderer().GetActiveCamera().Roll(1);
			}
		}
	}

	/**
	 * a default constructor used by the ClassInfo enumerator
	 */
	public Cone2Scene()
	{
		super();
	}

	@Override
	public String getLabel()
	{
		return "VTK Sample Cone 2";
	}

	@Override
	public String getDescription()
	{
		return "The second VTK sample file of a simple cone which rotates";
	}
}
