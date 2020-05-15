package hw5;

import java.util.Comparator;

/**
 * Instantiate the ListPriorityQueue to test.
 */
public class ListPriorityQueueTest extends PriorityQueueTest {
  @Override
  protected PriorityQueue<Integer> createUnit() {
    return new ListPriorityQueue<>();
  }

  @Override
  protected PriorityQueue<Integer> createUnit(Comparator<Integer> comp) {
    return new ListPriorityQueue<>(comp);
  }
}
