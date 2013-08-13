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
import vtk.vtkInteractorStyleTrackballCamera;
import vtk.vtkPolyDataMapper;
import vtk.vtkProperty;
import vtk.vtkRenderWindowInteractor;
import vtk.vtkRenderer;

import com.geofx.vtk.scene.VTKComposite;
import com.geofx.vtk.scene.VTKScene;

public class Cone5Scene extends VTKScene
{
	protected vtkRenderWindowInteractor iren;
	protected vtkRenderer 				ren1;
	
	public Cone5Scene(VTKComposite composite) throws InterruptedException
	{
		super(composite);

	    // Next we create an instance of vtkConeSource and set some of its
	    // properties. The instance of vtkConeSource "cone" is part of a
	    // visualization pipeline (it is a source process object); it produces
	    // data (output type is vtkPolyData) which other filters may process.
	    //
	    vtkConeSource cone = new vtkConeSource();
	    cone.SetHeight(0.3 );
	    cone.SetRadius( 0.1 );
	    cone.SetResolution( 10 );

	    //
	    // In this example we terminate the pipeline with a mapper process object.
	    // (Intermediate filters such as vtkShrinkPolyData could be inserted in
	    // between the source and the mapper.)  We create an instance of
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

		composite.getRenderer().AddActor(coneActor);
		composite.getRenderer().SetBackground(0.1, 0.4, 0.1);

	    //
	    // Create the Renderer and assign actors to it. A renderer is like a
	    // viewport. It is part or all of a window on the screen and it is
	    // responsible for drawing the actors it has.  We also set the
	    // background color here.
	    //
	    //ren1 = new vtkRenderer();
	    //ren1.AddActor(coneActor);
	    //ren1.SetBackground(0.1, 0.2, 0.4);

	    //
	    // Finally we add our renderer to the existing window
		//composite.getPanel().GetRenderWindow().AddRenderer(ren1);

	    // Set the camera azimuth to 90 degrees
	    //ren1.ResetCamera();
	    //ren1.GetActiveCamera().Azimuth(90);
		
		composite.getPanel().GetRenderer().ResetCamera();
		composite.getPanel().GetRenderer().GetActiveCamera().Azimuth(90);

	    //
	    // The vtkRenderWindowInteractor class watches for events (e.g., keypress,
	    // mouse) in the vtkRenderWindow. These events are translated into event
	    // invocations that VTK understands (see VTK/Common/vtkCommand.h for all
	    // events that VTK processes). Then observers of these VTK events can
	    // process them as appropriate.
	    System.out.println("isValid = " + composite.getPanel().isValid());
	    iren = new vtkRenderWindowInteractor();
	    iren.SetRenderWindow(composite.getPanel().GetRenderWindow());

	    //
	    // By default the vtkRenderWindowInteractor instantiates an instance
	    // of vtkInteractorStyle. vtkInteractorStyle translates a set of events
	    // it observes into operations on the camera, actors, and/or properties
	    // in the vtkRenderWindow associated with the vtkRenderWinodwInteractor.
	    // Here we specify a particular interactor style.
	    // vtkInteractorStyleTrackballCamera style = new vtkInteractorStyleTrackballCamera();
	    // iren.SetInteractorStyle(style);

	    //
	    // Unlike the previous examples where we performed some operations and then
	    // exited, here we leave an event loop running. The user can use the mouse
	    // and keyboard to perform the operations on the scene according to the
	    // current interaction style.
	    //

	    //
	    // Initialize and start the event loop. Once the render window appears,
	    // mouse in the window to move the camera. The Start() method executes
	    // an event loop which listens to user mouse and keyboard events. Note
	    // that keypress-e exits the event loop. (Look in vtkInteractorStyle.h
	    // for a summary of events, or the appropriate Doxygen documentation.)
	    //
	    System.out.println("isValid = " + composite.getPanel().isValid());

		//composite.getPanel().Render();

	    /*
		if (composite.getPanel().isValid())
		{
			iren.Initialize();
			iren.Start();
		}
		*/
	}

	/**
	 * a default constructor used by the ClassInfo enumerator
	 */
	public Cone5Scene()
	{
		super();
	}

	@Override
	public String getLabel()
	{
		return "VTK Sample Cone 5";
	}

	@Override
	public String getDescription()
	{
		return "This example introduces the concepts of user interaction with VTK. First, a different"
				+ " interaction style (than the default) is defined. Second, the interaction is started.";
	}
}
