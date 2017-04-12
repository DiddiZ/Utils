package de.diddiz.utils.serialization;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import de.diddiz.utils.Utils;

/**
 * @author Robin Kupper
 */
public class DataNode extends SerializedData<String, Object>
{
	private final char pathSeperator;

	private final Map<String, Object> dataMap;

	public DataNode(char pathSeperator, Map<String, Object> dataMap) {
		this.pathSeperator = pathSeperator;
		this.dataMap = dataMap;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DataNode other = (DataNode)obj;
		if (dataMap == null) {
			if (other.dataMap != null)
				return false;
		} else if (!dataMap.equals(other.dataMap))
			return false;
		if (pathSeperator != other.pathSeperator)
			return false;
		return true;
	}

	/**
	 * Returns the object at the specified path.
	 * <p>
	 * Supplying an empty path will return the data map of this node.
	 * <p>
	 * Will return <code>null</code> when the path doesn't exist.
	 */
	@Override
	public Object get(String path) {
		if (path == null || path.length() == 0)
			return dataMap;
		final String[] steps = Utils.split(path, pathSeperator);
		Object current = dataMap;
		for (final String step : steps) {
			if (!(current instanceof Map))
				return null;
			current = ((Map<?, ?>)current).get(step);
		}
		return current;
	}

	/**
	 * Returns the internal storage tree of this node.
	 * <p>
	 * Changes to it will be reflected by this.
	 */
	public Map<String, Object> getData() {
		return dataMap;
	}

	/**
	 * Returns all keys present at this node.
	 *
	 * @see #getKeys(String)
	 */
	public Set<String> getKeys() {
		return getKeys(null);
	}

	/**
	 * Returns all keys present at the specified sub-node.
	 * <p>
	 * Will return an empty set for non-existing or empty sub-nodes.
	 * <p>
	 * Returned set will support remove actions.
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getKeys(String path) {
		final Object obj = get(path);
		if (obj instanceof Map)
			return ((Map<String, Object>)obj).keySet();
		return Collections.emptySet();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getList(String path) {
		final Object obj = get(path);
		if (obj instanceof List)
			return (List<Object>)obj;
		return Collections.emptyList();
	}

	/**
	 * Creates a new {@code YamlNode}, if none is present.
	 * <p>
	 * Will overwrite existing values, if path doesn't denote a map.
	 *
	 * @return New {@code YamlNode}, never {@code null}.
	 * @see #getNode(String)
	 */
	public DataNode getOrCreateSubNode(String path) {
		DataNode node = getSubNode(path);
		if (node == null) {
			node = wrap(new LinkedHashMap<String, Object>());
			set(path, node);
		}
		return node;
	}

	/**
	 * Returns the number of elements at this node.
	 */
	public int getSize() {
		return dataMap.size();
	}

	/**
	 * Checks if the element at the specified path is a map, and if so, returns a it {@code DataNode} as a view of it.
	 * <p>
	 * Changes to the sub node are reflected by the parent and vice versa.
	 * <p>
	 * Supplying an empty path will return this node.
	 *
	 * @return new {@code YamlNode} or {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public DataNode getSubNode(String path) {
		final Object obj = get(path);
		if (obj instanceof Map)
			return wrap((Map<String, Object>)obj);
		return null;
	}

	/**
	 * Checks if the element at the specified path is a list, and if so, wraps each map in the list into a {@code DataNode}.
	 * <p>
	 * Changes to the sub nodes are reflected by the parent and each other.
	 * <p>
	 * Supplying an empty path will return an empty list as this node isn't a list.
	 *
	 * @return a List, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public List<DataNode> getSubNodeList(String path) {
		final Object obj = get(path);
		if (obj instanceof List) {
			final List<Object> list = (List<Object>)obj;
			final List<DataNode> nodes = new ArrayList<>(list.size());
			for (final Object o : list)
				if (o instanceof Map)
					nodes.add(wrap((Map<String, Object>)o));
			return nodes;
		}
		return Collections.emptyList();
	}

	/**
	 * Return a list of all sub-nodes at this node.
	 *
	 * @see #getSubNodes(String)
	 * @return a {@code List}, never {@code null}
	 */
	public List<DataNode> getSubNodes() {
		return getSubNodes(null);
	}

	/**
	 * Return a list of all sub-nodes at the specified path.
	 * <p>
	 * Changes to the returned list aren't reflected.
	 *
	 * @return a {@code List}, never {@code null}
	 */
	@SuppressWarnings("unchecked")
	public List<DataNode> getSubNodes(String path) {
		final List<DataNode> nodes = new ArrayList<>();

		for (final Entry<String, Object> e : getEntries(path))
			if (e.getValue() instanceof Map)
				nodes.add(wrap((Map<String, Object>)e.getValue()));

		return nodes;
	}

	/**
	 * Return a list of all sub-nodes at this node, associated with their keys.
	 *
	 * @see #getSubNodesWithKeys(String)
	 */
	public List<Entry<String, DataNode>> getSubNodesWithKeys() {
		return getSubNodesWithKeys(null);
	}

	/**
	 * Return a list of all sub-nodes at the specified path, associated with their keys.
	 * <p>
	 * The keys are the name of the parent node, not the full path.
	 * <p>
	 * Changes to the returned list aren't reflected.
	 *
	 * @return a {@code List}, never {@code null}
	 */
	@SuppressWarnings("unchecked")
	public List<Entry<String, DataNode>> getSubNodesWithKeys(String path) {
		final List<Entry<String, DataNode>> nodes = new ArrayList<>();

		for (final Entry<String, Object> e : getEntries(path))
			if (e.getValue() instanceof Map)
				nodes.add(new AbstractMap.SimpleImmutableEntry<>(e.getKey(), wrap((Map<String, Object>)e.getValue())));

		return nodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dataMap == null ? 0 : dataMap.hashCode());
		result = prime * result + pathSeperator;
		return result;
	}

	/**
	 * Sets a value at the specified path.
	 * <p>
	 * Will overwrite existing values.
	 * <p>
	 * Supplying an empty path will result in doing nothing.
	 * <p>
	 * Supplied {@code DataNodes} will be safely unpacked before adding.
	 */
	@SuppressWarnings("unchecked")
	public void set(String path, Object value) {
		if (path == null || path.isEmpty())
			return;

		final String[] steps = path.split("\\.");
		Map<String, Object> parent = dataMap;
		for (int i = 0; i < steps.length - 1; i++) {
			Object child = parent.get(steps[i]);
			if (child == null || !(child instanceof Map)) { // Child not present, or not a node
				child = new LinkedHashMap<String, Object>(); // Create a new node
				parent.put(steps[i], child);
			}
			parent = (Map<String, Object>)child;
		}

		if (value instanceof DataNode) // Unpack DataNodes
			value = ((DataNode)value).dataMap;

		parent.put(steps[steps.length - 1], value); // Add the value to the last parent
	}

	/**
	 * Returns a set of all entries at the specified path.
	 * <p>
	 * Nested maps aren't wrapped as {@code DataNodes}
	 * <p>
	 * Returns an empty set if path doen't exist.
	 */
	@SuppressWarnings("unchecked")
	private Set<Entry<String, Object>> getEntries(String path) {
		final Object obj = get(path);
		if (obj instanceof Map)
			return ((Map<String, Object>)obj).entrySet();
		return Collections.emptySet();
	}

	/**
	 * Creates a new {@code DataNode} with the same {@code pathSeperator} as this.
	 */
	private DataNode wrap(Map<String, Object> data) {
		return new DataNode(pathSeperator, data);
	}
}
