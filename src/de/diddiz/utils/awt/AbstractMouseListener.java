package de.diddiz.utils.awt;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Default implementation of {@link MouseListener} that doesn't nothing at all.
 * <p>
 * Extending this allows to avoid creating an empty method body for event.
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
