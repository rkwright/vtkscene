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

import org.eclipse.core.runtime.QualifiedName;

/**
 * A placeholder for the various constants needed throughout the plugin
 * project. Most of these are references to ids defined in the plugin.xml
 * file.
 */
public final class PluginConstants
{
	/**
	 * plugin id from plugin.xml
	 */
	public static final String			PLUGIN_ID				= "com.geofx.vtk.scene";
	
	/*
	 * The ID of the view itself
	 */
	public static final String			VIEW_ID				    = "com.geofx.vtk.scene.VTKScene";

	/**
	 * namespace URI for the properties
	 */
	public static final String			PROPERTY_NAMESPACE		= "http://www.geofx.com/vtk";
	
	/**
	 * property name for the scene's name
	 */
	public static final String			SCENENAME    			= "scenename";
	
	/**
	 * property name for the examples package
	 */
	public static final String			VTK_SAMPLES_PACKAGE    		= "com.geofx.vtk.samples";

	/**
	 * All the examples packages
	 */
	public static final String[] EXAMPLE_PACKAGES = { VTK_SAMPLES_PACKAGE };
	
	/**
	 * property name for the default scene name
	 */
	public static final String			DEFAULT_SCENENAME    	= VTK_SAMPLES_PACKAGE + "." + "Cone1Scene";


	/**
	 * property qualified name for the scene name
	 */	
	public static final QualifiedName	SCENENAME_PROPERTY_NAME	= new QualifiedName(PROPERTY_NAMESPACE, SCENENAME);
	
	
}
