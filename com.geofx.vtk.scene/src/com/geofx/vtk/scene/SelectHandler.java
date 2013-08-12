package com.geofx.vtk.scene;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

import com.geofx.vtk.plugin.Activator;
import com.geofx.vtk.plugin.Activator.ClassInfo;

public class SelectHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ArrayList<?> classList = Activator.getClassInfo();  
		
		ListDialog dialog = new ListDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
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
		
		return null;
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
