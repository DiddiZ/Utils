package de.diddiz.utils.collections;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T>
{
	private final T value;
	private TreeNode<T> parent;
	final private List<TreeNode<T>> children = new LinkedList<>();

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

	@SuppressWarnings("unchecked")
	public void addChildren(TreeNode<T>... newChildren) {
		for (final TreeNode<T> child : newChildren)
			addChild(child);
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "TreeNode [value=" + value + ", children=" + children + "]";
	}
}
