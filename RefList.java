import java.io.*;

class RefList{
  public void DB () {}
  String[] refnames = new String[200];
  static int count = 0;

  public boolean isInList(String nn) {
    for (int i=0; i<count; i++) {
      if (refnames[i].equals(nn)) {
        System.out.println("Bib. item with name "+nn+" already exists in the list");
        return true;
      }
    }
    return false;
  }
  public void print() {
    for (int i=0; i<count; i++) {
      System.out.println("Item "+i+" has name "+refnames[i]);
    }
  }
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

