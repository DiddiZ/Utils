package de.diddiz.utils;

import java.awt.Component;
import java.awt.Container;
import javax.swing.RootPaneContainer;

/**
 * @author Robin Kupper
 */
public class SwingUtils
{
	/**
	 * Disables/enables the components and all of its children.
	 */
	public static void setEnabledRecursively(Component component, boolean enabled) {
		component.setEnabled(enabled);
		if (component instanceof RootPaneContainer)
			setEnabledRecursively(((RootPaneContainer)component).getContentPane(), enabled);
		if (component instanceof Container)
			for (final Component c : ((Container)component).getComponents())
				setEnabledRecursively(c, enabled);
	}
}
