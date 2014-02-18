package de.diddiz.utils.awt;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Empty implementation of {@link WindowListener} that doesn't nothing at all.
 * <p>
 * Extending this allows subclasses to just override the necessary methods, instead of all.
 */
public abstract class AbstractWindowListener implements WindowListener
{
	@Override
	public void windowActivated(WindowEvent event) {}

	@Override
	public void windowClosed(WindowEvent event) {}

	@Override
	public void windowClosing(WindowEvent event) {}

	@Override
	public void windowDeactivated(WindowEvent event) {}

	@Override
	public void windowDeiconified(WindowEvent event) {}

	@Override
	public void windowIconified(WindowEvent event) {}

	@Override
	public void windowOpened(WindowEvent event) {}
}
