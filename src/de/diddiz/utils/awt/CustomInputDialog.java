package de.diddiz.utils.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

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
	 * <p>
	 * Assumes the dialog was cancelled.
	 */
	protected final void close() {
		getDialog().setVisible(false);
	}

	/**
	 * Returns the {@link Dialog} of this dialog.
	 * 
	 * @throws IllegalStateException if called before {@link #showDialog()}
	 */
	protected final JDialog getDialog() {
		for (Container p = btnOkay.getParent(); p != null; p = p.getParent())
			if (p instanceof JDialog)
				return (JDialog)p;
		throw new IllegalStateException();
	}

	/**
	 * Returns the {@link JOptionPane} of this dialog.
	 * 
	 * @throws IllegalStateException if called before {@link #showDialog()}
	 */
	protected final JOptionPane getOptionPane() throws IllegalStateException {
		for (Container p = btnOkay.getParent(); p != null; p = p.getParent())
			if (p instanceof JOptionPane)
				return (JOptionPane)p;
		throw new IllegalStateException();
	}

	/**
	 * Sets the enabled status of the ok button.
	 * <p>
	 * When enabling also requests focus.
	 */
	protected final void setOkEnabled(boolean enable) {
		btnOkay.setEnabled(enable);
		if (enable)
			btnOkay.requestFocus();
	}

	protected final void setTitle(String title) {
		getDialog().setTitle(title);
	}

	protected abstract int showDialog();

	protected final int showDialog(Component parentComponent, Object message, String title, int messageType, Icon icon) {
		return JOptionPane.showOptionDialog(parentComponent, message, title, JOptionPane.OK_CANCEL_OPTION, messageType, icon, new Object[]{btnOkay, btnCancel}, btnOkay);
	}
}
