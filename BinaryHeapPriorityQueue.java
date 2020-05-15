package hw5;

import exceptions.EmptyException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Priority queue implemented as a binary heap.
 *
 * <p> Use the ranked array representation of a binary heap!
 * Keep the arithmetic simple by sticking a null into slot 0 of the
 * ArrayList; wasting one reference is an okay price to pay for nicer
 * code.</p>
 *
 * @param <T> Element type.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<T>>
    implements PriorityQueue<T> {

  private ArrayList<T> list;
  private Comparator<T> cmp;

  /**
   * A binary heap using the "natural" ordering of T.
   */
  public BinaryHeapPriorityQueue() {

    this(new DefaultComparator<>());
  }

  /**
   * A binary heap using the given comparator for T.
   *
   * @param cmp Comparator to use.
   */
  public BinaryHeapPriorityQueue(Comparator<T> cmp) {
    list = new ArrayList<>();
    this.cmp = cmp;
  }

  @Override
  public void insert(T t) {
    if (list.isEmpty()) {
      list.add(t);
    } else {
      list.add(t); // insert at end
      int curr = list.size() - 1;
      while (curr > 0 && cmp.compare(list.get((curr - 1) / 2), t) < 0) {
        //swim up
        T temp = list.get(curr);
        list.set(curr, list.get((curr - 1) / 2));
        list.set((curr - 1) / 2, temp);
        curr = (curr - 1) / 2;
      }
    }
  }

  /**
   * checks if position has a left child.
   * @param i is current position in list which is being
   *          checked for a having a left child or not.
   * @return true if has a left child
   */
  public boolean hasLeftChild(int i) {
    return 2 * i + 1 < list.size();
  }

  /**
   * checks if position has a right child.
   * @param i is current position in list which is being
   *          checked for a having a right child or not.
   * @return true if has a right child
   */
  public boolean hasRightChild(int i) {
    return 2 * i + 2 < list.size();
  }

  /**
   * Checks if an element at a position needs to
   * sink down according to the min/max
   * property of queue.
   * @param i is current position in list
   * @return true if sink operation us required
   */
  public boolean sinkRequired(int i) {
    return leftSinkRequired(i) || rightSinkRequired(i);
  }

  /**
   * Checks if an element at a position needs to sink
   * down to the left according to the min/max property of queue.
   * @param i is current position in list
   * @return true if left sink operation us required
   */
  public boolean leftSinkRequired(int i) {
    if (hasLeftChild(i)) {
      return cmp.compare(list.get(i), list.get(2 * i + 1)) < 0;
    }
    return false;
  }

  /**
   * Checks if an element at a position needs to sink
   * down to the right according to the min/max property of queue.
   * @param i is current position in list
   * @return true if right sink operation us required
   */
  public boolean rightSinkRequired(int i) {
    if (hasRightChild(i)) {
      return cmp.compare(list.get(i),list.get(2 * i + 2)) < 0;
    }
    return false;
  }

  /**
   * sinks the element to the left.
   * @param curr is current position in list
   */
  public void leftSink(int curr) {
    T temp = list.get(curr);
    list.set(curr, list.get(2 * curr + 1));
    list.set(2 * curr + 1, temp);
  }

  /**
   * sinks the element to the right.
   * @param curr is current position in list
   */
  public void rightSink(int curr) {
    T temp = list.get(curr);
    list.set(curr, list.get(2 * curr + 2));
    list.set(2 * curr + 2, temp);
  }

  /**
   * swaps the element at the root with the last leaf in the heap.
   */
  public void swapRootWithLastLeafAndRemoveLeaf() {
    T temp = list.get(0);
    list.set(0, list.get(list.size() - 1));
    list.set(list.size() - 1, temp);
    list.remove(list.size() - 1);
  }

  /**
   * Performs the sink operation on the entire heap.
   */
  public void sinkRootTillApplicable() {
    int root = 0;
    while (sinkRequired(root)) { //sink root
      int curr = root;
      while (curr < list.size() && sinkRequired(curr)) {
        if (leftSinkRequired(curr)) {
          leftSink(curr);
          curr = 2 * curr + 1;
        }
        if (rightSinkRequired(curr)) {
          rightSink(curr);
          curr = 2 * curr + 2;
        }
      }
    }
  }


  @Override
  public void remove() throws EmptyException {
    if (list.isEmpty()) {
      throw new EmptyException();
    }
    if (list.size() == 1) {
      list.remove(0);
    } else {
      swapRootWithLastLeafAndRemoveLeaf();
      sinkRootTillApplicable();
    }
  }

  @Override
  public T best() throws EmptyException {
    if (list.isEmpty()) {
      throw new EmptyException();
    }
    return list.get(0);
  }

  @Override
  public boolean empty() {
    return list.isEmpty();
  }

  // The default comparator uses the "natural" ordering.
  private static class DefaultComparator<T extends Comparable<T>>
      implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }

}
