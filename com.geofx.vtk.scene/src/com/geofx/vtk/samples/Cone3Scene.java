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
import vtk.vtkRenderer;

import com.geofx.vtk.scene.VTKComposite;
import com.geofx.vtk.scene.VTKScene;

public class Cone3Scene extends VTKScene
{
	public Cone3Scene(VTKComposite composite) throws InterruptedException
	{
		super(composite);

		//
		// Next we create an instance of vtkConeSource and set some of its
		// properties. The instance of vtkConeSource "cone" is part of a
		// visualization pipeline (it is a source process object); it produces
		// data (output type is vtkPolyData) which other filters may process.
		//
		vtkConeSource cone = new vtkConeSource();
		cone.SetHeight(3.0);
		cone.SetRadius(1.0);
		cone.SetResolution(10);

		//
		// In this example we terminate the pipeline with a mapper process object.
		// (Intermediate filters such as vtkShrinkPolyData could be inserted in
		// between the source and the mapper.) We create an instance of
		// vtkPolyDataMapper to map the polygonal data into graphics primitives. We
		// connect the output of the cone source to the input of this mapper.
		//
		vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
		coneMapper.SetInputConnection(cone.GetOutputPort());

		//
		// Create an actor to represent the cone. The actor orchestrates rendering of
		// the mapper's graphics primitives. An actor also refers to properties via a
		// vtkProperty instance, and includes an internal transformation matrix. We
		// set this actor's mapper to be coneMapper which we created above.
		//
		vtkActor coneActor = new vtkActor();
		coneActor.SetMapper(coneMapper);

		//
		// Create two renderers and assign actors to them. A renderer renders into a
		// viewport within the vtkRenderWindow. It is part or all of a window on the
		// screen and it is responsible for drawing the actors it has. We also set
		// the background color here. In this example we are adding the same actor
		// to two different renderers; it is okay to add different actors to
		// different renderers as well.
		//
		vtkRenderer ren1 = new vtkRenderer();
		ren1.AddActor(coneActor);
		ren1.SetBackground(0.1, 0.2, 0.4);
		ren1.SetViewport(0.0, 0.0, 0.5, 1.0);

		vtkRenderer ren2 = new vtkRenderer();
		ren2.AddActor(coneActor);
		ren2.SetBackground(0.1, 0.2, 0.4);
		ren2.SetViewport(0.5, 0.0, 1.0, 1.0);

		composite.getPanel().GetRenderWindow().AddRenderer(ren1);
		composite.getPanel().GetRenderWindow().AddRenderer(ren2);
		
		//
		// Finally we create the render window which will show up on the screen.
		// We add our two renderers into the render window using AddRenderer. We also
		// set the size to be 600 pixels by 300.
		//
		/*vtkRenderWindow renWin = new vtkRenderWindow();
		renWin.AddRenderer(ren1);
		renWin.AddRenderer(ren2);
		renWin.SetSize(600, 300);*/

		//
		// Make one camera view 90 degrees from other.
		//
		ren1.ResetCamera();
		ren1.GetActiveCamera().Azimuth(90);

		//
		// now we loop over 360 degrees and render the cone each time
		//
		for ( int i = 0; i < 360; i++ )
		{
			Thread.sleep(10);
			// render the image
			composite.getPanel().Render();
			// rotate the active camera by one degree
			ren1.GetActiveCamera().Azimuth(1);
			ren2.GetActiveCamera().Azimuth(1);
		}
	}

	/**
	 * a default constructor used by the ClassInfo enumerator
	 */
	public Cone3Scene()
	{
		super();
	}

	@Override
	public String getLabel()
	{
		return "VTK Sample Cone 3";
	}

	@Override
	public String getDescription()
	{
		return "Set up two renderers in one window";
	}
}
