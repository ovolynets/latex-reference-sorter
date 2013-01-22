package com.alexsources.referencesorter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Oleksandr Volynets
 * 
 * The main class for the reference sorter program. It reads bibliography tex-file
 * (for now hard-coded as "biblio.tex") and a .ps file (for now hard-coded "thesis.ps")
 * and produces an output file "biblio_sorted.tex" with a sorted bibliography items
 * ordered as used in the .ps file 
 *
 */
public class ReferenceSorter {

	public static void main(String[] args) throws IOException {
		// Create the reference database
		DB b = new DB();
		createDB("biblio.tex", b);
		
		// Print the full database
		b.printDB();
		
		// Read the .ps file and create a list of reference names
		// according to their first usage in the file, avoiding
		// the repetition of the references once the one has been used before
		RefList l = new RefList();
		listReferences("thesis.ps", l);
		
		// Print the reference names to the screen
		l.print();
		// Print the full references to the output sorted bibliography names
		l.printRefs(b);
	}


	/**
	 * Create the reference database from the bibliography tex-file.
	 * 
	 * @param filename Bibliography tex-file
	 * @param b Database
	 * @throws IOException
	 */
	static void createDB(String filename, DB b) throws IOException {
		try {
			// Initialize some needed variables
			BufferedReader in = new BufferedReader(new FileReader(filename)); // Read buffer
			String str;          // Current string read from the file
			String curRef = "";  // Current reference being compiled
			String curName = ""; // The name of the reference being processed
			String newName = ""; // The name of the following reference
			int itemIndex = 0, comIndex = 0;

			// Skip the first couple of dummy lines with no bib. bibitems
			while (true) {
				str = in.readLine();
				if (str==null || str.indexOf("\\bibitem")>-1) break;
			}
			// Add end-of-line to the first reference
			curRef = str+"\n";
			itemIndex = str.indexOf("\\bibitem");
			// Get the name of the first reference
			curName = str.substring(itemIndex+9, str.indexOf("}", itemIndex+9));
			// Read all the references one by one until we reach the
			// \end{bibliography} tag
			while (true)
			{
				str = in.readLine();
				if (str == null || (str.indexOf("\\end{thebibliography}") > -1) ) {
					// If end of file or the end of bibliography reached - exit
					b.names[DB.count] = curName;
					// System.out.println(b.count);
					b.refs[DB.count++] = curRef;
					break;
				}
				// Find the index of the \bibitem tag
				itemIndex = str.indexOf("\\bibitem");
				// Also find the index for a comment sign, in case a line is like % \bibitem
				// In this case add the line to the current ref, do not break the loop
				comIndex = str.indexOf("%");
				// System.out.println("In current line \""+str+"\" bibitem was found at "+itemIndex+" and the comment at "+comIndex);
				//         if (itemIndex > -1 && (comIndex > itemIndex || comIndex < 0) ) {
				if (itemIndex < 0 || (comIndex < itemIndex && comIndex > -1) ) {
					curRef += str+"\n";
					continue;
				}

				// Extract the name of the reference from the string:
				newName = str.substring(itemIndex+9, str.indexOf("}", itemIndex+9));
				// System.out.println("New name: "+newName+"\n");
				b.names[DB.count] = curName;
				b.refs[DB.count++] = curRef;
				// System.out.println("Current ref. "+curName+" = "+curRef);
				curName = newName;
				// Clear the current reference
				curRef = str+"\n";
			}
			// Close the file
			in.close();
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage());
		}
	}

	/**
	 * List all the references found in the .ps file
	 *  
	 * @param filename Name of the .ps file
	 * @param l List of references
	 * @throws IOException
	 */
	static void listReferences(String filename, RefList l) throws IOException{
		try {
			// Initialize some needed variables
			BufferedReader in = new BufferedReader(new FileReader(filename)); // Buffer for reading
			String str;          // Current string of the .ps file 
			String curName = ""; // The name of the current reference
			int citeIndex = 0;

			// Read the .ps file line by line and try to find the references
			// It is possible since the .ps file is simply a text file!
			while ( (str = in.readLine()) != null) {
				citeIndex = str.indexOf("(cite.");
				if (citeIndex>-1) {
					// +6 here appears to shift the line by the content of the string "(cite."
					curName = str.substring(citeIndex+6, str.indexOf(")", citeIndex+6));

					// older style with shifting by 17 characters for older latex-dvips programs
					// which produce a different output for the reference name "(cite. SOMETHINGELSE"
					// curName = str.substring(citeIndex+17, str.indexOf(")", citeIndex+17));

					// Verbose output
					System.out.println("Current cite name: "+curName+"\n");
					if (!l.isInList(curName))
						l.refnames[RefList.count++] = curName;
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Error: "+e.getMessage());
		}
	}

}
