package hw5;

import exceptions.EmptyException;
import org.junit.Before;
import org.junit.Test;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing implementations of the PriorityQueue interface.
 */
public abstract class PriorityQueueTest {
  protected PriorityQueue<Integer> unit;
  protected PriorityQueue<Integer> reverseUnit;

  //A comparator using the reverse of the natural ordering for class T
  private static class ReverseComparator<T extends Comparable<T>>
      implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t2.compareTo(t1);
    }
  }

  ReverseComparator<Integer> reverseComp = new ReverseComparator<>();

  protected abstract PriorityQueue<Integer> createUnit();

  protected abstract PriorityQueue<Integer> createUnit(Comparator<Integer> comp);

  @Before
  public void setupTests() {
    unit = this.createUnit();
    reverseUnit = this.createUnit(reverseComp);
  }

  @Test
  public void newQueueEmpty() {
    assertTrue(unit.empty());
    assertTrue(reverseUnit.empty());
  }

  @Test(expected = EmptyException.class)
  public void bestThrowsEmptyExceptionForUnit() {
    unit.best();
  }

  @Test(expected = EmptyException.class)
  public void bestThrowsEmptyExceptionForReverseUnit() {
    reverseUnit.best();
  }

  @Test(expected = EmptyException.class)
  public void removeThrowsEmptyExceptionForUnit() {
    unit.remove();
  }

  @Test(expected = EmptyException.class)
  public void removeThrowsEmptyExceptionForReverseUnit() {
    reverseUnit.remove();
  }

  @Test
  public void QueueNotEmptyAfterInsert() {
    unit.insert(1);
    assertFalse(unit.empty());

    reverseUnit.insert(1);
    assertFalse(reverseUnit.empty());
  }

  @Test
  public void testInsert() {
    unit.insert(1);
    assertEquals(1, unit.best().intValue());

    reverseUnit.insert(1);
    assertEquals(1, reverseUnit.best().intValue());
  }

  @Test
  public void testInsertUpdatesBest() {
    unit.insert(2);
    unit.insert(1);
    assertEquals(2, unit.best().intValue());
    unit.insert(4);
    unit.insert(3);
    assertEquals(4, unit.best().intValue());

    reverseUnit.insert(2);
    reverseUnit.insert(1);
    assertEquals(1, reverseUnit.best().intValue());
    reverseUnit.insert(3);
    reverseUnit.insert(4);
    assertEquals(1, reverseUnit.best().intValue());
  }

  @Test
  public void testRemove() {
    unit.insert(1);
    unit.remove();
    assertTrue(unit.empty());

    reverseUnit.insert(1);
    reverseUnit.remove();
    assertTrue(reverseUnit.empty());
  }


  @Test
  public void testRemoveUpdatesBest() {
    unit.insert(1);
    unit.insert(4);
    unit.insert(0);
    unit.insert(8);
    unit.insert(2);
    assertEquals(8, unit.best().intValue());
    unit.remove();
    assertEquals(4, unit.best().intValue());
    unit.remove();
    assertEquals(2, unit.best().intValue());
    unit.remove();
    assertEquals(1, unit.best().intValue());
    unit.remove();
    assertEquals(0, unit.best().intValue());

    reverseUnit.insert(1);
    reverseUnit.insert(4);
    reverseUnit.insert(0);
    reverseUnit.insert(8);
    reverseUnit.insert(2);
    assertEquals(0, reverseUnit.best().intValue());
    reverseUnit.remove();
    assertEquals(1, reverseUnit.best().intValue());
    reverseUnit.remove();
    assertEquals(2, reverseUnit.best().intValue());
    reverseUnit.remove();
    assertEquals(4, reverseUnit.best().intValue());
    reverseUnit.remove();
    assertEquals(8, reverseUnit.best().intValue());

  }

  @Test
  public void testWithDuplicateElements() {
    unit.insert(1);
    unit.insert(4);
    unit.insert(0);
    unit.insert(8);
    unit.insert(8);
    unit.insert(2);
    assertEquals(8, unit.best().intValue());
    unit.remove();
    assertEquals(8, unit.best().intValue());

    reverseUnit.insert(1);
    reverseUnit.insert(4);
    reverseUnit.insert(0);
    reverseUnit.insert(0);
    reverseUnit.insert(8);
    reverseUnit.insert(2);
    assertEquals(0, reverseUnit.best().intValue());
    reverseUnit.remove();
    assertEquals(0, reverseUnit.best().intValue());
  }
}
