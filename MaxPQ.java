
/*
 * Implementation of a max-heap priority queue.
 */
import java.util.ArrayList;

/*
 * TODO: This file should contain your priority queue backed by a binary
 * max-heap.
 */
public class MaxPQ {
	public MaxPQ() {
	}

	private void siftUp(int idx) {
		for (; idx > 0 && this.arrList.get(idx).compareTo(this.arrList.get(father(idx))) > 0;) {
			swap(idx, father(idx));
			idx = father(idx);
		}
	}
	/*
	 * This method is used to swap two elements in the arraylist at i and at j.
	 */

	private void swap(int i, int j) {
		LinkVO tmp = this.arrList.get(i);
		this.arrList.set(i, this.arrList.get(j));
		this.arrList.set(j, tmp);
	}

	/*
	 * When a new link add in to the ArrayList, this function is called. In this
	 * function, siftUP is called to be bubble up.
	 * 
	 */
	public void enqueue(LinkVO e) {
		this.arrList.add(this.arrList.size(), e);
		siftUp(this.arrList.size() - 1);
	}

	/*
	 * This method is use to bubble down, from the front to the end. Sort the
	 * Arraylist.
	 * 
	 */
	private void siftDown(int k) {
		for (; left(k) < this.arrList.size();) {
			int j = left(k);
			if (j + 1 < this.arrList.size() && this.arrList.get(j).compareTo(this.arrList.get(j + 1)) < 0) {
				j = right(k);
			}
			if (this.arrList.get(k).compareTo(this.arrList.get(j)) >= 0) {
				break;
			} else {
				swap(k, j);
			}
			k = j;

		}
	}

	/*
	 * This medthod is use to remove the first element in the arraylist, and the
	 * last element goes to the front.
	 */
	public LinkVO dequeue() {
		LinkVO data = this.arrList.get(0);
		int size = this.arrList.size();
		swap(0, size - 1);
		this.arrList.remove(size - 1);
		siftDown(0);

		return data;
	}

	/*
	 * It returns if the arraylist is empty.
	 */
	public boolean isEmpty() {
		return this.arrList.isEmpty();
	}

	/*
	 * Passing in a index of variable, find its parent of this index.
	 */
	private int father(int idx) {
		return (idx - 1) / 2;
	}

	/*
	 * Find the left child.
	 */
	private int left(int idx) {
		return idx * 2 + 1;
	}

	/*
	 * Find the right child.
	 */
	private int right(int idx) {
		return idx * 2 + 2;
	}

	private ArrayList<LinkVO> arrList = new ArrayList<>();

}
