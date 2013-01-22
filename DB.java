class DB {
  public void DB () {}
  String[] refs = new String[200];
  String[] names = new String[200];
  int[] index = new int[200];
  static int count = 0;
  
  public void printDB() {
    // for (int i=0; i<count; i++) {
      System.out.println("Total "+count+" items found");
  }
    
  public boolean isInDB(String nn) {
    for (int i=0; i<count; i++) {
      System.out.println("Bib. item with name "+nn+" already exists in the database");
      if (names[i].equals(nn)) return true;
    }
    return false;
  }
  
  public String getRefByName(String nn) {
    for (int i=0; i<count; i++) {
      // System.out.println("Bib. item with name "+nn+" already exists in the database");
      if (names[i].equals(nn)) return refs[i];
    }
    System.out.println("The item with name "+nn+" does not exist in the database");
    return "";
  }
}
