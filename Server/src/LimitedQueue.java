import java.util.LinkedList;

/**
 * Turns the arraylist class into a fixed length queue which replaces elements when added to
 * @param <E> Takes a generic type, in my case, an arraylist
 */
public class LimitedQueue<E> extends LinkedList<E> {

  private int limit;

  /**
   * Define the max amount of items that can be in the queue
   * @param limit the max amount of items that can be in the queue
   */
  public LimitedQueue(int limit) {
    this.limit = limit;
  }

  /**
   * Adds a new item to the queue, removing the head if the queue is full
   * @param o The item to be added to the queue
   * @return if the item was added successfully
   */
  @Override
  public boolean add(E o) {
    super.add(o);
    while (size() > limit) {
      super.remove();
    }
    return true;
  }
}