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

import java.util.ArrayList;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.dialogs.ListDialog;

import com.geofx.vtk.plugin.Activator;
import com.geofx.vtk.plugin.Activator.ClassInfo;



/**
 * This code implements a workbench action delegate to handle
 * the event triggered by the OpenGL Scene-selector UI.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SceneSelect implements IWorkbenchWindowActionDelegate
{
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public SceneSelect()
	{
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.  We don't really do anything with the
	 * action, we just use it as trigger.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action)
	{
		ArrayList<?> classList = Activator.getClassInfo();  
		
		ListDialog dialog = new ListDialog(window.getShell());
		dialog.setTitle("Select Scene");
		dialog.setMessage("Choose a scene from the list...");
		dialog.setContentProvider(new ListContentProvider(classList));
		
		dialog.setLabelProvider( new ListLabelProvider() );
		
		dialog.setInput(classList);
		dialog.setInitialSelections(new Object[] {Activator.getSceneName()});

		if ( dialog.open() == Window.OK)
		{
			Object[] results = dialog.getResult();
			if (results.length > 0 && results[0] instanceof ClassInfo)
			{
				ClassInfo info = (ClassInfo)results[0];
				Activator.setSceneName( info.name );
				Activator.getView().updateView();
			}
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state 
	 * of the 'real' action here if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection)
	{
	}

	/**
	 * We can use this method to dispose of any system resources we previously 
	 * allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose()
	{
	}

	/**
	 * We will cache window object in order to be able to provide parent shell 
	 * for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window)
	{
		this.window = window;
	}
	
	/**
	 * 	 the generic implementation is sufficient
	 */
	private class ListContentProvider implements IStructuredContentProvider
	{
		private ArrayList<ClassInfo> mList = null;
		
		@SuppressWarnings("unchecked")
		public ListContentProvider ( ArrayList<?> classList )
		{
			mList = (ArrayList<ClassInfo>) classList;
		}
		
		public Object[] getElements(Object inputElement) 
		{
			return mList.toArray();
		}	

		public void dispose()
		{
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
		}
	}
	
	/**
	 * We just need to fetch the label from the ClassInfo object we get passed
	 *
	 */
	protected class ListLabelProvider extends LabelProvider 
	{
		public String getText(Object obj) 
		{
			ClassInfo	info = (ClassInfo)obj;
			return info.label;
		}
	}
}