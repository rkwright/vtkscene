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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.geofx.vtk.plugin.Activator;

/**
 * Implementation of the actual view, derived from ViewPart
 */

public class SceneView extends ViewPart
{

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.geofx.vtk.scene.VTKScene";

	private VTKComposite 		vtkComposite;
	private VTKScene			scene;
	private String 				sceneName;

	/**
	 * The constructor.
	 */
	public SceneView()
	{
		Activator.setView( this );
	}

	public void createPartControl( Composite parent )
	{		
		sceneName = Activator.getSceneName();

		vtkComposite = new VTKComposite(parent); 
		
		this.scene = Activator.constructScene(sceneName, vtkComposite);
		
	//	vtkComposite.addView(sceneName);
		
		// we need to explicitly request the focus or we never get it
		vtkComposite.getFrame().requestFocus();		
	}

	public void dispose()
	{
		System.out.println("SceneView.dispose called");
		if (this.scene != null)
			this.scene.dispose();
		if (Activator.getView() != null)
			Activator.setView(null);
	}
	
	/**
	 * A public method to update the view.  
	 *
	 */
	public void updateView()
	{
		if (!sceneName.equals(Activator.getSceneName()))
		{
			sceneName = Activator.getSceneName();
			
			System.out.println("SceneView - disposing old scene: "+ scene.getLabel());
			this.scene.dispose();
			
			System.out.println("SceneView - constructing new scene: " + sceneName);
			this.scene = Activator.constructScene(sceneName, vtkComposite);
			
			//vtkComposite.addView(sceneName);		
		}
		
		this.scene.render();
	}


	/**
	 * We don't need this but we have to implement it since ViewPart requires it
	 */
	@Override
	public void setFocus() 
	{
	}
}