package de.diddiz.utils.awt;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Empty implementation of {@link MouseListener} that does nothing at all.
 * <p>
 * Extending this allows subclasses to just override the necessary methods, instead of all.
 * 
 * @author Robin Kupper
 */
public abstract class AbstractMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent event) {}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}
}
