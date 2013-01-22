package com.alexsources.referencesorter;
/**
 * 
 * @author Oleksandr Volynets
 * Class for Database of References.
 * It holds all the entries found in the bibliography tex-file
 *
 */
public class DB {
	public DB () {}
	
	/// Maximal number of references in the file
	static final int MAX_ENTRIES = 200;  // FIXME - max. 200 entries allowed for now
	String[] refs = new String[MAX_ENTRIES];   /// Array of full references
	String[] names = new String[MAX_ENTRIES];  /// Array of reference names
	static int count = 0;                      /// Number of entries in the database

	/**
	 *  Print the entire database
	 */
	public void printDB() {
		System.out.println("Total "+count+" items found");
		for (int i=0; i<count; i++) {
			System.out.println("Item with index "+i+" and name "+names[i]+" is as follows: ");
			System.out.println(refs[i]);
		}
	}

	/**
	 * Check if a reference with name refname is in the database
	 * 
	 * @param refname Name of the reference to search for
	 * @return <code>true</code> if the reference was found in the database
	 */
	public boolean isInDB(String refname) {
		for (int i=0; i<count; i++) {
			System.out.println("Bib. item with name "+refname+" already exists in the database");
			if (names[i].equals(refname)) return true;
		}
		return false;
	}

	/**
	 * Get the full reference by name
	 * @param refname Name of the reference to search for
	 * @return The full reference; empty string if the reference does not exist in the database
	 */
	public String getRefByName(String refname) {
		for (int i=0; i<count; i++) {
			// System.out.println("Bib. item with name "+nn+" already exists in the database");
			if (names[i].equals(refname)) return refs[i];
		}
		System.out.println("The item with name "+refname+" does not exist in the database");
		return "";
	}
}
