package ADTPackage.Stack;
import java.util.EmptyStackException;
import java.util.Arrays;

/**
 * A class of stacks whose entries are stored in an array.
 *
 * @param <T> The type of elements stored in the stack
 */
public final class ResizableArrayStack<T> implements StackInterface<T> {
    private T[] stack; // Array of stack entries
    private int topIndex; // Index of top entry
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;

    /**
     * Default constructor that initializes the stack with a default capacity.
     */
    public ResizableArrayStack() {
        this(DEFAULT_CAPACITY);
    } // end default constructor

    /**
     * Constructor that initializes the stack with a specified initial capacity.
     * @param initialCapacity The initial capacity of the stack
     */
    public ResizableArrayStack(int initialCapacity) {
        integrityOK = false;
        checkCapacity(initialCapacity);

        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempStack = (T[]) new Object[initialCapacity];
        stack = tempStack;
        topIndex = -1;
        integrityOK = true;
    } // end constructor

    //  < Implementations of the stack operations go here. >

    /**
     * Adds a new entry to the top of the stack.
     * @param newEntry The entry to be added to the stack
     */
    public void push(T newEntry) {
        checkIntegrity();
        ensureCapacity();
        stack[topIndex + 1] = newEntry;
        topIndex++;
    }

    /**
     * Removes and returns the top entry of the stack.
     * @return The top entry of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() {
        checkIntegrity();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            T top = stack[topIndex];
            stack[topIndex] = null;
            topIndex--;
            return top;
        }
    }

    /**
     * Retrieves the top entry of the stack without removing it.
     * @return The top entry of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() {
        checkIntegrity();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack[topIndex];
        }
    }

    /**
     * Checks whether the stack is empty.
     * @return True if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return topIndex < 0;
    }

    /**
     * Removes all entries from the stack.
     */
    public void clear() {
        checkIntegrity();
        while (topIndex > -1) {
            stack[topIndex] = null;
            topIndex--;
        }
    }

    //  < Implementations of the private methods go here; checkCapacity and checkIntegrity
    //    are analogous to those in Chapter 2. >

    /**
     * Ensures the stack has enough capacity to accommodate additional entries.
     */
    private void ensureCapacity() {
        if (topIndex >= stack.length - 1) {
            int newLength = 2 * stack.length;
            checkCapacity(newLength);
            stack = Arrays.copyOf(stack, newLength);
        }
    }

    /**
     * Checks the integrity of the stack.
     * @throws SecurityException if the stack object is corrupt
     */
    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("ResizableArrayStack object is corrupt.");
        }
    }

    /**
     * Checks if the specified capacity exceeds the maximum allowed capacity.
     * @param capacity The capacity to be checked
     * @throws IllegalStateException if the capacity exceeds the maximum allowed
     */
    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Attempt to create a stack whose " +
                                            "capacity exceeds allowed maximum of " + MAX_CAPACITY);
        }
    }
} // end ArrayStack
