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

package com.geofx.vtk.plugin;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import vtk.vtkNativeLibrary;

import com.geofx.vtk.scene.PluginConstants;
import com.geofx.vtk.scene.SceneView;
import com.geofx.vtk.scene.VTKComposite;
import com.geofx.vtk.scene.VTKScene;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{
	// The plug-in ID
	public static final String PLUGIN_ID = "com.geofx.vtk.scene";

	// The shared instance
	private static Activator 	plugin;
	private static String		sceneName;
	private static SceneView	sceneView;

	private static ArrayList<ClassInfo> classInfo = new ArrayList<ClassInfo>();

	/**
	 * The constructor
	 */
	public Activator()
	{
		plugin = this;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		System.out.println("Activator:start()");

		super.start(context);

		loadLibraries();
		
		enumClasses();
		
		// fetch the scene used the last time, if any
		sceneName = getDefault().getPreferenceStore().getString(PluginConstants.SCENENAME);
		if (sceneName.isEmpty())
		{
			sceneName = PluginConstants.DEFAULT_SCENENAME;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		// save the scene we used this time
		getDefault().getPreferenceStore().setValue(PluginConstants.SCENENAME, getSceneName());

		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	private void loadLibraries()
	{
		if (!vtkNativeLibrary.LoadAllNativeLibraries())
		{
			for (vtkNativeLibrary lib : vtkNativeLibrary.values())
			{
				if (!lib.IsLoaded())
				{
					System.out.println(lib.GetLibraryName() + " not loaded");
				}
			}
		}
		vtkNativeLibrary.DisableOutputWindow(null);
	}
	
	/**
	 * Using the bundle, enumerate all the classes in this plugin and see which ones are in the
	 * examples package. Those are the ones we will allow the user to choose.
	 * 
	 * @return list of classes to choose from
	 */
	public void enumClasses()
	{
		try
		{
			Enumeration<?> entries = Platform.getBundle(PluginConstants.PLUGIN_ID).findEntries("/", "*" + ".class", true);
			while (entries.hasMoreElements())
			{
				URL entry = (URL) entries.nextElement();
				// Change the URLs to have Java class names
				String path = entry.getPath().replace('/', '.');
				// see if the class is in the package we are interested in and isn't a subclass
				int start = -1;
				for (String pkgName : PluginConstants.EXAMPLE_PACKAGES)
				{
					start = path.indexOf(pkgName);
					if (start >= 0)
						break;
				}

				int subClass = path.indexOf("$");
				if (start >= 0 && subClass == -1)
				{
					// strip off the class suffix and we are done
					String name = path.substring(start, path.length() - ".class".length());

					VTKScene scene = constructScene(name, null);
					classInfo.add(getClassInfo(name, scene));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param name
	 * @param scene
	 * @return
	 */
	private ClassInfo getClassInfo(String name, VTKScene scene)
	{
		ClassInfo info = new ClassInfo(name, scene.getDescription(), scene.getLabel());
		System.out.println("info = " + info);

		return info;
	}

	public static ArrayList<ClassInfo> getClassInfo()
	{
		return classInfo;
	}

	/**
	 * 
	 * @param name
	 * @param composite
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static VTKScene constructScene(String name, VTKComposite composite)
	{
		VTKScene newScene = null;
		Object[] args = {};
		Class[] types = {};
		Class<VTKScene> classe;

		// System.out.println(System.getProperty("java.library.path"));

		try
		{
			classe = (Class<VTKScene>) Class.forName(name);

			if (composite != null) // && frame != null)
			{
				args = new Object[1];
				args[0] = composite;
				types = new Class[1];
				types[0] = VTKComposite.class;
				newScene = (VTKScene) classe.getConstructor(types).newInstance(args);
			}
			else
				newScene = (VTKScene) classe.getConstructor(types).newInstance();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}

		return newScene;
	}

	// getters and setters for the sceneName and the View objects
	public static String getSceneName()
	{
		return sceneName;
	}

	public static void setSceneName( String name )
	{
		sceneName = name;
	}

	public static void setView( SceneView view )
	{
		Activator.sceneView = view;
	}

	public static SceneView getView()
	{
		return sceneView;
	}
	/**
	 * Just a simple holder for our ClassInfo
	 * 
	 */
	public class ClassInfo
	{
		public String name;
		public String description;
		public String label;

		public ClassInfo(String n, String d, String l)
		{
			name = n;
			description = d;
			label = l;
		}
	}
	
	/**
	 * Create view from the view ID if it hasn't already been opened.
	 * @param event
	 */
	public static void createView(ExecutionEvent event)
	{
		try
		{
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(PluginConstants.VIEW_ID);
		}
		catch (PartInitException e)
		{
			e.printStackTrace();
		}
	}
}