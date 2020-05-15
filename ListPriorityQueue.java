package hw5;

import exceptions.EmptyException;
import java.util.Comparator;

/**
 * Priority queue implemented using our (unordered) abstract List,
 * specifically with a SentinelList implementation.
 *
 * @param <T> Element type.
 */
public class ListPriorityQueue<T extends Comparable<T>>
    implements PriorityQueue<T> {

  private List<T> list;
  private Comparator<T> cmp;

  /**
   * An unordered List PQ using the "natural" ordering of T.
   */
  public ListPriorityQueue() {

    this(new DefaultComparator<>());
  }

  /**
   * An unordered List PQ using the given comparator for T.
   *
   * @param cmp Comparator to use.
   */
  public ListPriorityQueue(Comparator<T> cmp) {
    list = new SentinelList<>();
    this.cmp = cmp;
  }

  @Override
  public void insert(T t) {

    list.insertBack(t);
  }

  @Override
  public void remove() throws EmptyException {
    if (list.empty()) {
      throw new EmptyException();
    }
    Position<T> p = list.front();
    int i = 0;
    while (i < list.length()) {
      if (p.get() == best()) {
        list.remove(p);
        return;
      }
      p = list.next(p);
    }
  }

  @Override
  public T best() throws EmptyException {
    if (list.empty()) {
      throw new EmptyException();
    }
    Position<T> p = list.front();
    T best = p.get();
    for (T value: list) {
      if (cmp.compare(best, value) < 0) {
        best = value;
      }
    }
    return best;
  }

  @Override
  public boolean empty() {
    return list.empty();
  }

  // The default comparator uses the "natural" ordering.
  private static class DefaultComparator<T extends Comparable<T>>
      implements Comparator<T> {
    public int compare(T t1, T t2) {

      return t1.compareTo(t2);
    }
  }
}
