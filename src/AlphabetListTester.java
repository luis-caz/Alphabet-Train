//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Alphabet List Tester
// Files: Cart.java, AlphabetList.java, LinkedCart.java, SortedListADT.java
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

/**
 * This class implements unit test methods to check the correctness of LinkedCart and AlphabetList
 * classes defined in the CS300 Spring 2020 - P06 Alphabet Train programming assignment.
 *
 */
public class AlphabetListTester {

    /**
     * This method should test and make use of at least the LinkedCart constructor, an accessor
     * (getter) method, and a mutator (setter) method defined in the LinkedCart class.
     * 
     * @return true when this test verifies a correct functionality, and false otherwise
     */
    public static boolean testLinkedCart() {
        Cart test = new Cart("A");
        LinkedCart cart = new LinkedCart(test);


        if (!cart.getCart().equals(test)) {
            return false;
        }

        if (cart.getNext() != null) {
            return false;
        }

        if (cart.getPrevious() != null) {
            return false;
        }

        // Test for second constructor
        Cart letterZ = new Cart("Z");
        Cart letterB = new Cart("B");
        LinkedCart previous = new LinkedCart(letterZ);
        LinkedCart next = new LinkedCart(letterB);
        cart = new LinkedCart(test, previous, next);

        if (!cart.getCart().equals(test)) {
            return false;
        }

        if (cart.getNext() != next) {
            return false;
        }

        if (cart.getPrevious() != previous) {
            return false;
        }

        return true;
    }

    /**
     * This method checks for the correctness of both AlphabetList constructors and the instance
     * method isEmpty() when called on an empty alphabet list object.
     * 
     * @return true when this test verifies a correct functionality, and false otherwise
     */
    public static boolean testAlphabetListConstructorIsEmpty() {
        try {
            AlphabetList test = new AlphabetList(0);

        } catch (IllegalArgumentException e) {
            if (!e.getMessage().toLowerCase().contains("capacity of this list")) {
                return false;
            }
        } catch (Exception e1) {
            return false;
        }

        AlphabetList testTwo = new AlphabetList();

        if (!testTwo.isEmpty() || testTwo.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * This method checks for the correctness of the AlphabetList(int) constructor when passed a
     * negative int value.
     * 
     * @return true when this test verifies a correct functionality, and false otherwise
     */
    public static boolean testAlphabetListConstructorBadInput() {
        try {
            AlphabetList test = new AlphabetList(-5);

        } catch (IllegalArgumentException e) {
            if (!e.getMessage().toLowerCase().contains("capacity of this list")) {
                return false;
            }
        } catch (Exception e1) {
            return false;
        }
        return true;
    }


    /**
     * This method checks for the correctness of the AlphabetList.add() method when it is passed bad
     * inputs. This method must consider at least the test scenarios provided in the detailed
     * description of these javadocs. (1) Try adding a null to the list; (2) Try adding a cart which
     * carries a non upper-case alphabet letter, for instance "Hello" or "1" or "a". (3) Try adding a
     * duplicate cart to the list.
     * 
     * @return true when this test verifies a correct functionality, and false otherwise
     */
    public static boolean testAlphabetListAddBadInput() {
        AlphabetList list = new AlphabetList();

        try {
            list.add(null);
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().toLowerCase().contains("can only add carts")) {
                return false;
            }
        } catch (Exception e1) {
            return false;
        }

        try {
            list.add(new Cart("Hello")); // tests non-single letter string & lower case
        } catch (IllegalArgumentException e2) {
            if (!e2.getMessage().toLowerCase().contains("can only add carts")) {
                return false;
            }
        } catch (Exception e3) {
            return false;
        }


        // testing adding duplicate
        list.add(new Cart("A"));
        list.add(new Cart("B"));
        list.add(new Cart("D"));
        list.add(new Cart("F"));
        list.add(new Cart("Z"));

        try {
            list.add(new Cart("Z"));
        } catch (IllegalArgumentException e4) {
            if (!e4.getMessage().toLowerCase().contains("cannot duplicate letters")) {
                return false;
            }
        } catch (Exception e5) {
            return false;
        }

        return true;
    }

    /**
     * This method checks for the correctness of the AlphabetList.add() considering at least the test
     * scenarios provided in the detailed description of these javadocs. (1) Try adding a cart to an
     * empty list; (2) Try adding a cart which is expected to be added at the head of a non-empty
     * alphabet list; (3) Try adding a cart which is expected to be added at the end of a non-empty
     * alphabet list; (4) Try adding a cart which is expected to be added at the middle of a non-empty
     * alphabet list. For each of those scenarios, make sure that the size of the list is
     * appropriately updated after a call without errors of the add() method, and that the contents of
     * the list is as expected whatever if list is read in the forward or backward directions. You can
     * also check the correctness of AlphabetList.get(int), AlphabetList.indexOf(Cart), and
     * AlphabetList.size() in this test method.
     * 
     * @return true when this test verifies a correct functionality, and false otherwise
     */
    public static boolean testAlphabetListAdd() {
        AlphabetList test = new AlphabetList();
        Cart cartH = new Cart("H");
        try {
            test.add(cartH); // Testing adding cart to empty list

            if (test.isEmpty() || test.size() != 1) {
                return false;
            }
            if (test.get(0).getCargo().compareTo("H") != 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        test = new AlphabetList();

        try {
            test.add(new Cart("B")); // Testing adding cart to end of a non-empty list
            test.add(new Cart("D"));
            test.add(new Cart("F"));
            test.add(new Cart("X"));
            test.add(new Cart("A"));
            test.add(new Cart("Z"));

            if (test.isEmpty() || test.size() != 6) {
                return false;
            }
            if (test.get(5).getCargo().compareTo("Z") != 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        test = new AlphabetList();

        try {
            test.add(new Cart("B")); // Testing adding cart to head of a non-empty list
            test.add(new Cart("D"));
            test.add(new Cart("F"));
            test.add(new Cart("Z"));
            test.add(new Cart("A"));

            if (test.isEmpty() || test.size() != 5) {
                return false;
            }
            if (test.get(0).getCargo().compareTo("A") != 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        test = new AlphabetList();

        try {
            test.add(new Cart("B")); // Testing adding cart to middle of non-empty list
            test.add(new Cart("D"));
            test.add(new Cart("F"));
            test.add(new Cart("Z"));
            test.add(new Cart("A"));
            test.add(new Cart("E"));

            if (test.isEmpty() || test.size() != 6) {
                return false;
            }
            if (test.get(3).getCargo().compareTo("E") != 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }



        return true;
    }

    /**
     * This method checks for the correctness of the AlphabetList.remove() considering at least the
     * test scenarios provided in the detailed description of these javadocs. (1) Try removing a cart
     * from an empty list or pass a negative index to AlphabetList.remove() method; (2) Try removing a
     * cart (at position index 0) from a list which contains only one cart; (3) Try to remove a cart
     * (position index 0) from a list which contains at least 2 carts; (4) Try to remove a cart from
     * the middle of a non-empty list containing at least 3 carts; (5) Try to remove the cart at the
     * end of a list containing at least two carts. For each of those scenarios, make sure that the 
     * size of the list is appropriately updated after a call without errors of the add() method, 
     * and that the contents of the list is as expected whatever if list is read in the forward or 
     * backward directions.
     * 
     * @return true when this test verifies a correct functionality, and false otherwise
     */
    public static boolean testAlphabetListRemove() {
        AlphabetList test = new AlphabetList();

        try {
            test.remove(1); // removing from empty list
        } catch (IndexOutOfBoundsException e) {
            if (!e.getMessage().toLowerCase().contains("invalid index")) {
                return false;
            }
        } catch (Exception e1) {
            return false;
        }


        try {
            test.remove(-1); // removing negative index
        } catch (IndexOutOfBoundsException e) {
            if (!e.getMessage().toLowerCase().contains("invalid index")) {
                return false;
            }
        } catch (Exception e1) {
            System.out.println("Bro");
            return false;
        }

        test.add(new Cart("A"));
        try { // removing from a list with one cart
            if (test.remove(0).compareTo(new Cart("A")) != 0) {
                return false;
            }
            if (!test.isEmpty() || test.size() != 0) {
                return false;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Bro2");
            return false;

        } catch (Exception e1) {
            System.out.println("Bro3");
            return false;
        }

        test.add(new Cart("A"));
        test.add(new Cart("C"));

        try { // removing from a list with two carts
            if (test.remove(0).compareTo(new Cart("A")) != 0) {
                System.out.println("Removing A2 failed");
                return false;
            }
            if (test.isEmpty() || test.size() != 1) {
                System.out.println("Did not remove A2");
                return false;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Bro4");
            return false;

        } catch (Exception e1) {
            System.out.println("Bro5");
            return false;
        }

        test.add(new Cart("D"));
        test.add(new Cart("V"));
        test.add(new Cart("O"));

        try { // removing cart from middle of list
            if (test.remove(2).compareTo(new Cart("O")) != 0) {
                System.out.println("Bro6");
                return false;
            }
            if (test.isEmpty() || test.size() != 3) {
                System.out.println("Bro7");
                return false;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Bro8");
            return false;

        } catch (Exception e1) {
            System.out.println("Bro9");
            return false;
        }

        test = new AlphabetList();
        test.add(new Cart("A"));
        test.add(new Cart("B"));
        test.add(new Cart("C"));

        try { // removing cart from end of list containing at least two carts
            if (test.remove(2).compareTo(new Cart("C")) != 0) {
                System.out.println("Bro10");
                return false;
            }
            if (test.isEmpty() || test.size() != 2) {
                System.out.println("Bro11");
                return false;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Bro12");
            return false;

        } catch (Exception e1) {
            System.out.println("Bro13");
            return false;
        }


        return true;
    }


    /**
     * This method calls all the test methods defined and implemented in your AlphabetListTester
     * class.
     * 
     * @return true if all the test methods defined in this class pass, and false otherwise.
     */
    public static boolean runAllTests() {
        if (!(testLinkedCart() && testAlphabetListAddBadInput() && testAlphabetListAdd()
            && testAlphabetListRemove() && testAlphabetListConstructorIsEmpty()
            && testAlphabetListConstructorBadInput())) {
            return false;
        }
        return true;
    }

    /**
     * Driver method defined in this AlphabetListTester class
     * 
     * @param args input arguments if any.
     */
    public static void main(String[] args) {
        System.out.println("Running Tests: " + runAllTests());
    }
}
