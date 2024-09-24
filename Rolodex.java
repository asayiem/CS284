//Abu Sayiem
//I pledge that I abide by the Stevens Honor System.

package rolodex;

import rolodex.Entry;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Rolodex {
	private Entry cursor;
	private final Entry[] index;
	char[] letters = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
	'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	// Constructor
	/**
 	 * Represents a Rolodex for storing contacts.
	 */
	Rolodex() {
		index = new Entry[26];
		// set the Separator letters
		for (int i = 0; i < letters.length; i++) {
			index[i] = new Separator(null, null, letters[i]);
		}
		// link each separator
		for (int i = 0; i < index.length; i++) {
			if (i == 0) {
				index[i].next = index[1];
				index[i].prev = index[25];
			} else if (i == 25) {
				index[i].next = index[0];
				index[i].prev = index[24];
			} else {
				index[i].next = index[i + 1];
				index[i].prev = index[i - 1];
			}
		}
	}
	/**
     * Checks if the Rolodex contains a contact with the given name.
     * 
     * @param name The name of the contact to check.
     * @return True if the Rolodex contains the contact, otherwise false.
     */
    public Boolean contains(String name) {
        char firstLetter = Character.toUpperCase(name.charAt(0));
        int indexPosition = firstLetter - 'A';

        if(indexPosition < 0 || indexPosition >= 26){
            return false;
        }

        Entry current = index[indexPosition].next;

        while(current != index[indexPosition]){
            if(current.getName().equals(name)){
                return true;
            }
            current = current.next;
        }
        return false;
    }
    /**
     * Retrieves the number of contacts in the Rolodex.
     * 
     * @return The number of contacts in the Rolodex.
     */
	public int size() {
		int count = 0;
		Entry current = index[0];
		while (current.next != index[0]) {
			if (!current.isSeparator())
				count++;
			current = current.next;
		}
		return count;
	}
	/**
     * Looks up the cell numbers associated with a given contact name.
     * 
     * @param name The name of the contact to look up.
     * @return An ArrayList containing the cell numbers associated with the contact name.
     * @throws IllegalArgumentException if the name is null or if the name is not found.
     */
	public ArrayList<String> lookup(String name) {
		if (name == null) {
			throw new IllegalArgumentException("lookup: no name to lookup");
		}
		char first = name.charAt(0);
		int j = 0;
		for (int i = 0; i < letters.length; i++) {
			if (first == letters[i]) {
				j = i;
			}
		}
		cursor = index[j];
		Boolean check = false;
		Entry current = cursor;
		ArrayList<String> numbers = new ArrayList<String>();
		while (current != null) {
			if (name.equals((current).getName())) {
				numbers.add(((Card) (current)).getCell());
				current = current.next;
				check = true;
			} else if (check) {
				current = null;
			} else {
				current = current.next;
			}
		}

		if (numbers.size() == 0) {
			throw new IllegalArgumentException("lookup: name not found");
		}
		return numbers;

	}


	public String toString() {
		Entry current = index[0];

		StringBuilder b = new StringBuilder();
		while (current.next!=index[0]) {
			b.append(current.toString()+"\n");
			current=current.next;
		}
		b.append(current.toString()+"\n");		
		return b.toString();
	}



	private Entry nextSep(String name) {
		int c = name.charAt(0) -65;
		Entry nextSep;
		if (c == 25) {
			nextSep = index[0];
		} else {
			nextSep = index[c+1];
		}
		return nextSep;
	}

    /**
     * Adds a new contact with the given name and cell number to the Rolodex.
     * 
     * @param name The name of the contact to add.
     * @param cell The cell number of the contact to add.
     * @throws IllegalArgumentException if the name or cell is null, or if a duplicate entry is found.
     */
	public void addCard(String name, String cell) {
		if (name == null) {
			throw new IllegalArgumentException("addCard: name non existant(empty input)");
		} else if(cell == null){
			throw new IllegalArgumentException("addCard: cell non existant(empty input)");
		} else if (contains(name) && lookup(name).contains(cell)) {
			throw new IllegalArgumentException("addCard: duplicate entry");
		}
		int c = name.charAt(0) -65;
		if (c>25 || c<0) {
			throw new IllegalArgumentException("addCard: Invalid name entry");
		}
		Entry current = index[c];
		while (current != nextSep(name)) {
			if (current.next.isSeparator() && name.compareToIgnoreCase(current.getName()) >= 0) {
				Card newCard = new Card(current, current.next, name, cell);
				Entry newEntry = current.next;
				current.next = newCard;
				newEntry.prev = newCard;
				break;
			} else if (name.compareTo(current.getName()) >=0 && name.compareTo(current.next.getName()) <= 0) {
				Card newCard = new Card(current, current.next,name,cell);
				Entry newEntry = current.next;
				current.next = newCard;
				newEntry.prev = newCard;
				break;
			} else {
				current = current.next;
			}
		}

	}
    /**
     * Removes a contact with the given name and cell number from the Rolodex.
     * 
     * @param name The name of the contact to remove.
     * @param cell The cell number of the contact to remove.
     * @throws IllegalArgumentException if the name is null or if the contact is not found.
     */
	public void removeCard(String name, String cell) {
		if (name == null) {
			throw new IllegalArgumentException("removeCard: name non existant");
		}
		int c = name.charAt(0) -65;
		cursor = index[c];

		Entry current = cursor;
		while (name.compareToIgnoreCase(current.getName()) >= 0){
			if(name.equals((current).getName()) && cell.equals(((Card)(current)).getCell())){
				current.prev.next = current.next;
				current.next.prev = current.prev;
				return;
			} else if (name.compareToIgnoreCase((current).getName()) >= 0){
				current = current.next;
			}
		}


	}
    /**
     * Removes all contacts with the given name from the Rolodex.
     * 
     * @param name The name of the contacts to remove.
     * @throws IllegalArgumentException if the name is not found.
     */
	public void removeAllCards(String name) {
		if(!contains(name)) {
			throw new IllegalArgumentException("removeAllCards: name does not exist");
		}
		ArrayList<String> nameList = lookup(name);
		
		for(int i = 0; i<nameList.size();i++) {
			removeCard(name,nameList.get(i));
		}

	}

	// Cursor operations
    /**
     * Initializes the cursor to the beginning of the Rolodex.
     */
	public void initializeCursor() {
		    cursor = index[0];

	}
    /**
     * Moves the cursor to the next separator in the Rolodex.
     */
	public void nextSeparator() {
		    cursor = cursor.next;
			while(!cursor.isSeparator()){
				cursor = cursor.next;
			}


	}
    /**
     * Moves the cursor to the next entry in the Rolodex.
     */
	public void nextEntry() {
		    cursor = cursor.next;

	}
    /**
     * Retrieves the string representation of the current entry pointed to by the cursor.
     * 
     * @return The string representation of the current entry.
     */
	public String currentEntryToString() {
			return cursor.toString();

	}


	public static void main(String[] args) {

		Rolodex r = new Rolodex();


		System.out.println(r);

		r.addCard("Chloe", "123");
		r.addCard("Chad", "23");
		r.addCard("Cris", "3");
		r.addCard("Cris", "4");
		r.addCard("Cris", "5");
		//		r.addCard("Cris", "4");
		r.addCard("Maddie", "23");

		System.out.println(r);

		System.out.println(r.contains("Albert"));

		r.removeAllCards("Cris");

		System.out.println(r);

		r.removeAllCards("Chad");
		r.removeAllCards("Chloe");

		r.addCard("Chloe", "123");
		r.addCard("Chad", "23");
		r.addCard("Cris", "3");
		r.addCard("Cris", "4");

		System.out.println(r);




	}

}
