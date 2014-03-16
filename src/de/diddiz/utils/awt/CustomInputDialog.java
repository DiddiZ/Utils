package de.diddiz.utils.awt;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public abstract class CustomInputDialog<T>
{
	private final JButton btnOkay = new JButton("Ok"), btnCancel = new JButton("Cancel");
	protected T result;

	public CustomInputDialog() {
		btnOkay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getOptionPane().setValue(btnOkay);
			}
		});
		btnOkay.setFocusable(true);
		btnOkay.setEnabled(false);

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getOptionPane().setValue(btnCancel);
			}
		});
	}

	/**
	 * @return null when cancelled
	 */
	public T getInput() {
		if (showDialog() == JOptionPane.OK_OPTION)
			return result;
		return null;
	}

	/**
	 * Closes the dialog.
	 */
	protected void close() {
		final Window w = SwingUtilities.getWindowAncestor(btnOkay);
		if (w != null)
			w.setVisible(false);
	}

	protected JOptionPane getOptionPane() {
		return getOptionPane(btnOkay);
	}

	/**
	 * Sets the enabled status of the ok button.
	 * <p>
	 * When enabling also requests focus.
	 */
	protected void setOkEnabled(boolean enable) {
		btnOkay.setEnabled(enable);
		if (enable)
			btnOkay.requestFocus();
	}

	protected abstract int showDialog();

	protected int showDialog(Component parentComponent, Object message, String title, int messageType, Icon icon) {
		return JOptionPane.showOptionDialog(parentComponent, message, title, JOptionPane.OK_CANCEL_OPTION, messageType, icon, new Object[]{btnOkay, btnCancel}, btnOkay);
	}

	private static JOptionPane getOptionPane(JComponent parent) {
		if (parent instanceof JOptionPane)
			return (JOptionPane)parent;
		return getOptionPane((JComponent)parent.getParent());
	}
}
