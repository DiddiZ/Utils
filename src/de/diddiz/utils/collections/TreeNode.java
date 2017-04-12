package de.diddiz.utils.collections;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Robin Kupper
 */
public class TreeNode<T>
{
	protected final T value;
	private TreeNode<T> parent;
	protected final List<TreeNode<T>> children = new LinkedList<>();

	public TreeNode(T value) {
		this.value = value;
	}

	public void addChild(TreeNode<T> child) {
		children.add(child);
		child.parent = this;
	}

	public void addChildren(Iterable<TreeNode<T>> newChildren) {
		for (final TreeNode<T> child : newChildren)
			addChild(child);
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public T getValue() {
		return value;
	}

	public void removeChild(TreeNode<T> child) {
		children.remove(child);
		child.parent = null;
	}

	public void removeChildren(Iterable<TreeNode<T>> childrenToRemove) {
		for (final TreeNode<T> child : childrenToRemove)
			removeChild(child);
	}

	@Override
	public String toString() {
		return "TreeNode [value=" + value + ", children=" + children + "]";
	}
}
