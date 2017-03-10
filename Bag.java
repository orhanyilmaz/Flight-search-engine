import java.util.Iterator;
import java.util.Date;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
	public Node<Item> next;
	public Node<Item> pre;
	public String name = "İ";
	public int child;
	public int num;
	public Date sTime;
	public Date eTime;
	public int price;
	public String flightId;

	public static class Node<Item> {
		public Item item;
		public String name;
		public String flightId;
		public Node<Item> next;
		public Node<Item> pre;
	}

	public Bag() {
		next = null;
		child = 0;
		num = -1;
	}

	public boolean isEmpty() {
		return next == null;
	}

	public void addnext(Item item, String id) {
		Node<Item> oldfirst = next;
		next = new Node<Item>();
		next.item = item;
		next.flightId = id;
		next.next = oldfirst;
	}

	public void addpre(Item item) {
		Node<Item> oldfirst = pre;
		pre = new Node<Item>();
		pre.item = item;
		pre.pre = oldfirst;
	}

	public Iterator<Item> iterator() {
		return new ListIterator<Item>(next);
	}

	@SuppressWarnings("hiding")
	public class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}
