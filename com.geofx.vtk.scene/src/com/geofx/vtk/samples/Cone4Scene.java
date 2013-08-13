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
import vtk.vtkProperty;
import vtk.vtkRenderer;

import com.geofx.vtk.scene.VTKComposite;
import com.geofx.vtk.scene.VTKScene;

public class Cone4Scene extends VTKScene
{
	public Cone4Scene(VTKComposite composite) throws InterruptedException
	{
		super(composite);

		// Next we create an instance of vtkConeSource and set some of its
	    // properties. The instance of vtkConeSource "cone" is part of a
	    // visualization pipeline (it is a source process object); it produces
	    // data (output type is vtkPolyData) which other filters may process.
	    vtkConeSource cone = new vtkConeSource();
	    cone.SetHeight( 3.0 );
	    cone.SetRadius( 1.0 );
	    cone.SetResolution( 10 );

	    // In this example we terminate the pipeline with a mapper process object.
	    // (Intermediate filters such as vtkShrinkPolyData could be inserted in
	    // between the source and the mapper.)  We create an instance of
	    // vtkPolyDataMapper to map the polygonal data into graphics primitives. We
	    // connect the output of the cone souece to the input of this mapper.
	    vtkPolyDataMapper coneMapper = new vtkPolyDataMapper();
	    coneMapper.SetInputConnection(cone.GetOutputPort());

	    // Create an actor to represent the first cone. The actor's properties are
	    // modified to give it different surface properties. By default, an actor
	    // is create with a property so the GetProperty() method can be used.
	    vtkActor coneActor = new vtkActor();
	    coneActor.SetMapper(coneMapper);
	    coneActor.GetProperty().SetColor(0.2, 0.63, 0.79);
	    coneActor.GetProperty().SetDiffuse(0.7);
	    coneActor.GetProperty().SetSpecular(0.4);
	    coneActor.GetProperty().SetSpecularPower(20);

	    // Create a property and directly manipulate it. Assign it to the
	    // second actor.
	    vtkProperty property = new vtkProperty();
	    property.SetColor(1.0, 0.3882, 0.2784);
	    property.SetDiffuse(0.7);
	    property.SetSpecular(0.4);
	    property.SetSpecularPower(20);

	    //
	    // Create a second actor and a property. The property is directly
	    // manipulated and then assigned to the actor. In this way, a single
	    // property can be shared among many actors. Note also that we use the
	    // same mapper as the first actor did. This way we avoid duplicating
	    // geometry, which may save lots of memory if the geoemtry is large.
	    vtkActor coneActor2 = new vtkActor();
	    coneActor2.SetMapper(coneMapper);
	    coneActor2.GetProperty().SetColor(0.2, 0.63, 0.79);
	    coneActor2.SetProperty(property);
	    coneActor2.SetPosition(0, 2, 0);

	    //
	    // Create the Renderer and assign actors to it. A renderer is like a
	    // viewport. It is part or all of a window on the screen and it is
	    // responsible for drawing the actors it has.  We also set the
	    // background color here.
	    //
	    vtkRenderer ren1 = new vtkRenderer();
	    ren1.AddActor(coneActor);
	    ren1.AddActor(coneActor2);
	    ren1.SetBackground(0.1, 0.2, 0.4);

		composite.getPanel().GetRenderWindow().AddRenderer(ren1);

	    // Make one camera view 90 degrees from other.
	    ren1.ResetCamera();
		ren1.GetActiveCamera().Azimuth(90);

		// now we loop over 360 degrees and render the cone each time
		for (int i = 0; i < 360; i++)
		{
			Thread.sleep(10);
			// render the image
			composite.getPanel().Render();
			// rotate the active camera by one degree
			ren1.GetActiveCamera().Azimuth(1);
		}
	}

	/**
	 * a default constructor used by the ClassInfo enumerator
	 */
	public Cone4Scene()
	{
		super();
	}

	@Override
	public String getLabel()
	{
		return "VTK Sample Cone 4";
	}

	@Override
	public String getDescription()
	{
		return "This example demonstrates the creation of multiple actors and the manipulation of their properties and transformations";
	}
}
