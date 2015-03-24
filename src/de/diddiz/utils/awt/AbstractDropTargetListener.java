package de.diddiz.utils.awt;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

/**
 * Empty implementation of {@link DropTargetListener} that does nothing at all.
 * <p>
 * Extending this allows subclasses to just override the necessary methods, instead of all.
 */
public class AbstractDropTargetListener implements DropTargetListener
{
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {}

	@Override
	public void dragExit(DropTargetEvent dte) {}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {}

	@Override
	public void drop(DropTargetDropEvent dtde) {}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {}
}
