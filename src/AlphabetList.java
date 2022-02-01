//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Alphabet List
// Files: Cart.java, AlphabetListTester.java, LinkedCart.java, SortedListADT.java
// Course: CS 300, Spring 2020
//
// Author: Luis Cazarin Quiroga
// Email: cazarinquiro@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////// PAIR PROGRAMMING (MAY SKIP WHEN WORKING INDIVIDUALLY) ////////////
//
// Partner Name: Alyssa Santoso
// Partner Email: amsantoso@wisc.edu
// Partner Lecturer's Name: Gary Dahl
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// _X_ Write-up states that pair programming is allowed for this assignment.
// _X_ We have both read and understood the course Pair Programming Policy.
// _X_ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Students who get help from sources other than their partner and the course
// staff must fully acknowledge and credit those sources here. If you did not
// receive any help of any kind from outside sources, explicitly indicate NONE
// next to each of the labels below.
//
// Persons: N/A
// Online Sources: N/A
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

/**
 * This class models a sorted doubly linked list of carts. Each cart carries one upper case alphabet
 * letter. Duplicate letters are not allowed in this list.
 * 
 * @author Luis Cazarin & Alyssa Santoso
 */
public class AlphabetList implements SortedListADT<Cart> {

    private static final Cart MIN_CART = new Cart("A"); // The smallest cart that
    // can be added to this sorted list
    private static final Cart MAX_CART = new Cart("Z"); // The largest cart that
    // can be added to this sorted list
    private LinkedCart head; // head of this doubly linked list
    private LinkedCart tail; // tail of this doubly linked list
    private int size; // size of this list
    private int capacity; // maximum number of carts which can be stored in this list

    public AlphabetList() {
        this.capacity = 26;
        size = 0;
        head = null;
        tail = null;

    }

    public AlphabetList(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "The capacity of this list must be a non-zero positive integer");
        }
        this.capacity = capacity;
        size = 0;
        head = null;
        tail = null;
    }



    /**
     * Adds a new cart to this sorted list.
     */
    @Override
    public void add(Cart newCart) throws IllegalArgumentException, IllegalStateException {
        if (newCart == null || newCart.getCargo().length() != 1
            || !(newCart.getCargo().charAt(0) <= MAX_CART.getCargo().charAt(0)
                && newCart.getCargo().charAt(0) >= MIN_CART.getCargo().charAt(0))) {
            // checks uppercase, alphabetic, and single letter conditions
            throw new IllegalArgumentException(
                "Can only add carts carrying one upper-case alphabetic letter in the range A..Z");
        }

        if (size == capacity) {
            throw new IllegalStateException("This list is full. Canot add another cart.");
        }

        LinkedCart cartPointer;

        if (size > 0) { // if non empty list
            // shallow copy of head - used to trace through linked list w/o changing contents
            cartPointer = head;
            while (cartPointer != null) {
                if (newCart.compareTo(cartPointer.getCart()) == 0) { // checks for duplicate
                    throw new IllegalArgumentException(
                        "Cannot duplicate letters of carts in this list.");
                }
                cartPointer = cartPointer.getNext(); // moves pointer to next cart in list
            }

            cartPointer = head; // resetting head copy for later

        } else {
            LinkedCart firstCart = new LinkedCart(newCart);
            head = firstCart;
            tail = firstCart;
            size++;
            return;
        }

        if (cartPointer.getNext() == null) { // special case - only one cart in list
            if (newCart.compareTo(cartPointer.getCart()) > 0) { // appending cart
                head.setNext​(new LinkedCart(newCart));
                tail = head.getNext();
                tail.setPrevious​(head);
                size++;
                return;
            } else { // prepending cart
                head.setPrevious​(new LinkedCart(newCart));
                head = head.getPrevious();
                head.setNext​(tail);
                size++;
                return;
            }

        } else if (newCart.getCargo().compareTo(head.getCart().getCargo()) < 0) {
            // special case - prepending head
            head.setPrevious​(new LinkedCart(newCart));
            head.getPrevious().setNext​(head);
            head = head.getPrevious();

            size++;
            return;

        } else { // list has 2 or more carts - can compare back and front

            while (cartPointer != null) {

                if (cartPointer == tail) {
                    // special case - gotten to end of list without inserting newCart
                    cartPointer.setNext​(new LinkedCart(newCart));
                    cartPointer.getNext().setPrevious​(cartPointer);
                    size++;
                    tail = cartPointer.getNext();
                    break;
                } else if (newCart.compareTo(cartPointer.getNext().getCart()) < 0) {

                    LinkedCart tempCart = cartPointer.getNext(); // temporary var. to store
                                                                 // non-referenced cart
                    // inserts newCart
                    cartPointer.setNext​(new LinkedCart(newCart));
                    // sets newCart's pointer to next cart
                    cartPointer.getNext().setNext​(tempCart);
                    // sets newCart's pointer to previous cart
                    cartPointer.getNext().setPrevious​(cartPointer);
                    // sets pointer to newCart
                    cartPointer.getNext().getNext().setPrevious​(cartPointer.getNext());

                    size++;
                    break;
                }
                cartPointer = cartPointer.getNext();
            }
        }
    }

    /**
     * isEmpty() Checks whether this list is empty.
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if (head == null || size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the size of this list
     * @return the number of carts linked in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all the carts from this list. This list must be empty after this method
     * returns.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the cart at position index of this list without removing it
     * @return specified cart
     */
    @Override
    public Cart get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        // pointer needed to search through list
        LinkedCart pointer = head;

        for (int i = 0; i < size; i++) {

            if (index == i) {
                return pointer.getCart();
            }
            pointer = pointer.getNext();
        }
        return null;
    }

    /**
     * Returns the index of the cart carrying data within this list
     * @return the index of findCart within this list or -1 if this list does 
     * not contain that cart.
     */
    @Override
    public int indexOf(Cart findObject) {
        int counter = 0;
        LinkedCart pointer = new LinkedCart(head.getCart(), head.getPrevious(), head.getNext());
        while (pointer != null) {
            if (pointer.getCart().compareTo(findObject) == 0) {
                return counter;
            }
            pointer = pointer.getNext();
            counter++;
        }
        return -1;
    }

    /**
     * Returns and removes the cart from this sorted list at the given index position
     * @return cart at specified index
     */
    @Override
    public Cart remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        LinkedCart pointer = head;

        for (int i = 0; i < index; i++) {
            pointer = pointer.getNext();
        }

        // removing head when only head is in list
        if (pointer.getPrevious() == null && pointer.getNext() == null) {
            head = null;
            tail = null;
            size--;
            return pointer.getCart();
        } else if (pointer.getNext() == null && pointer.getPrevious() != null) {
            // removing tail
            tail = pointer.getPrevious();
            tail.setNext​(null);
            // pointer.getPrevious().setNext​(null);
            size--;
            return pointer.getCart();
        } else if (pointer.getNext() != null && pointer.getPrevious() == null) {
            // removing head when two carts in list
            head = pointer.getNext();
            head.setPrevious​(null);
            size--;
            return pointer.getCart();
        } else {
            // removing from middle

            pointer.getPrevious().setNext​(pointer.getNext()); // hol up
            pointer.getNext().setPrevious​(pointer.getPrevious());
            size--;
            return pointer.getCart();
        }
    }

    /**
     * Returns a String representation of this list.
     */
    public String toString() {

        String string = "This list contains " + size + " cart(s)";
        if (size == 0) {
            return string;
        }
        string += ": [ ";
        LinkedCart currentCart = head;
        while (currentCart != null) {
            string += currentCart.getCart().toString() + " ";
            currentCart = currentCart.getNext();
        }
        string += "]";
        return string;
    }

    /**
     * Reads the contents of this list in the forward direction starting from its head.
     * @return a String representation of the contents of this list read in the forward 
     * direction or an empty string if this list is empty. For instance, if the list 
     * contains the following letters "A", "B", "D", "M", and "O". This method MUST 
     * return the following string "ABDMO".
     */
    public String readForward() {
        LinkedCart pointer = new LinkedCart(head.getCart(), head.getPrevious(), head.getNext());
        String string = "";
        while (pointer != null) {
            string += pointer.getCart().toString();
            pointer = pointer.getNext();
        }

        return string;
    }

    /**
     * Reads the contents of this list in the backward direction starting from its tail
     * @return a String representation of the contents of this list read in the backward 
     * direction or an empty string if this list is empty. For instance, if the list 
     * contains the following letters "A", "B", "D", "M", and "O". This method MUST return
     *  the following string "OMDBA".
     */
    public String readBackward() {
        LinkedCart pointer = new LinkedCart(tail.getCart(), tail.getPrevious(), tail.getNext());
        String string = "";
        while (pointer != null) {
            string += pointer.getCart().toString();
            pointer = pointer.getPrevious();
        }

        return string;
    }


}
