import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadText {
  static void createDB(String filename, DB b) throws IOException{
    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      String str;
      String curRef = "";
      String curName = "", newName = "";
      int itemIndex = 0, comIndex = 0;
      
      // Skip the first couple of lines with no bib. bibitems
      while (true) {
        str = in.readLine();
        if (str==null || str.indexOf("\\bibitem")>-1) break;
      }
      curRef = str+"\n";
      itemIndex = str.indexOf("\\bibitem");
      curName = str.substring(itemIndex+9, str.indexOf("}", itemIndex+9));
      while (true)
      {
        str = in.readLine();
//         System.out.println("Cur str = "+str);
        if (str == null || (str.indexOf("\\end{thebibliography}") > -1) ) {
          // If end of file or the end of bibliography reached - exit
          b.names[b.count] = curName;
          // System.out.println(b.count);
          b.refs[b.count++] = curRef;
          break;
        }
        // Find the index of \bibitem keyword
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

        // Extract the name of the rerefence from the string:
        newName = str.substring(itemIndex+9, str.indexOf("}", itemIndex+9));
        // System.out.println("New name: "+newName+"\n");
        b.names[b.count] = curName;
        b.refs[b.count++] = curRef;
        // System.out.println("Current ref. "+curName+" = "+curRef);
        curName = newName;
        // Clear the current reference
        curRef = str+"\n";
      }
      in.close();
      } catch (IOException e) {
    }
  }

  // list all the references in the .ps file
  static void listReferences(String filename, RefList l) throws IOException{
    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      String str;
      String curName = "";
      int citeIndex = 0;
      
      // Skip the first couple of lines with no bib. bibitems
      while ( (str = in.readLine()) != null) {
        citeIndex = str.indexOf("(cite.");
        if (citeIndex>-1) {
          // curName = str.substring(citeIndex+17, str.indexOf(")", citeIndex+17));
          curName = str.substring(citeIndex+6, str.indexOf(")", citeIndex+6));
          System.out.println("Current cite name: "+curName+"\n");
          if (!l.isInList(curName))
            l.refnames[l.count++] = curName;
        }
      }
      in.close();
      } catch (IOException e) {
    }
  }

  public static void main(String[] args) throws IOException {
    DB b = new DB();
    createDB("biblio.tex", b);
    b.printDB();
    RefList l = new RefList();
    listReferences("thesis.ps", l);
    l.print();
    l.printRefs(b);
  }
}
