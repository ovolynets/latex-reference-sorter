package com.alexsources.referencesorter;
import java.io.*;

/**
 * 
 * @author Oleksandr Volynets
 * Class for list of references found in the.
 * It holds all the entries found in the bibliography tex-file
 *
 */
public class RefList {

	public RefList () {}
	String[] refnames = new String[DB.MAX_ENTRIES];
	static int count = 0;

	/**
	 * Check if the reference already exists in the list 
	 * @param refname Name of the reference to search for
	 * @return <code>true</code> if the entry was found
	 */
	public boolean isInList(String refname) {
		for (int i=0; i<count; i++) {
			if (refnames[i].equals(refname)) {
				System.out.println("Bib. item with name "+refname+" already exists in the list");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Print the ordered list of reference names in the .ps file
	 */
	public void print() {
		for (int i=0; i<count; i++) {
			System.out.println("Item "+i+" has name "+refnames[i]);
		}
	}

	/**
	 * Print the sorted bibliography given the list of references
	 * found in the .ps file
	 * @param b Database from which to take the reference entries
	 */
	public void printRefs(DB b) {
		String curRef = "";
		try {
			FileWriter outFile = new FileWriter("bib_sorted.tex");
			PrintWriter out = new PrintWriter(outFile);
			out.println("% \\begin{tiny}");
			out.println("\\begin{thebibliography}{00}\n");
			System.out.println("N of references = "+count);

			for (int i=0; i<count; i++) {
				curRef = b.getRefByName(refnames[i]);
				System.out.println("Ref: \n "+curRef);
				out.println(curRef);
			}
			out.println("\\end{thebibliography}");
			out.println("% \\end{tiny}\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

