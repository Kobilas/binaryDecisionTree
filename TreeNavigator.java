package mkobilas.homework.decisiontree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
/**
 * TreeNavigator class used to make objects of type TreeNavigator, which is used to manipulate the decision tree input
 *   by the user as a whole. Used to organize the TreeNode objects into a theoretical tree using various types of
 *   methods. Used mainly for determining what category an input entered by the user should be classified under.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class TreeNavigator {
    private static TreeNode root;
    private static TreeNode cursor;
    //Used to determine what section of the input file the method is searching.
    private static boolean locationSwitch = true, keywordSwitch = false, leafSwitch = false;
    //Used to determine whether the method is editing the yes or no node according to the input file's syntax.
    private static boolean editYes = false, editNo = false, hasRun = false;
    //The current depth of the cursor in the tree.
    private static int currentDepth = -1;
    //The total depth of the tree.
    public static int maxDepth = 1;
    //Stores integers -1 (for null), 0 (for a 'no' node), and 1 (for a 'yes' node).
    private static int[] path = null;
    /**
     * Empty constructor for objects of the type TreeNavigator. The reference to TreeNode root is initialized to null,
     *   as well as the reference cursor. The int[] path array is initialized to have a size of 2.
     * @postcondition
     *      Creates an object of type TreeNavigator with root and cursor references initialized to null as well as an
     *        int array initialized to a size of 2.
     */
    public TreeNavigator(){
        root = null;
        cursor = root;
        path = new int[2];
    }
    /**
     * Constructor for objects of the type TreeNavigator. This constructor asks for a parameter in the form of a 
     *   TreeNode object which represents the root of a tree that is already constructed or soon to be constructed.
     * @param initRoot
     *      TreeNode initRoot is the TreeNode object that will serve as the root TreeNode for the tree that is being
     *        constructed.
     * @postcondition
     *      Creates an object of type TreeNavigator with root initialized to a user input and int[] path initialized to
     *        a size of 2.
     */
    public TreeNavigator(TreeNode initRoot){
        root = initRoot;
        cursor = root;
        currentDepth = 0;
        path = new int[2];
    }
    /**
     * This method is used to build a tree from an input file that is given by the user. This method takes input in
     *   the form of a file path or the file name of a .txt file. This file is then read using a FileReader and a
     *   BufferedReader object. If the TreeNavigator was null, this method can be called in a static context in order
     *   to initialize the TreeNavigator object since this method returns a TreeNavigator object of its own.
     * @param treeFile
     *      String treeFile is the file path or file name of the .txt file that will be read. This file must have a
     *        proper syntax or the method will not properly read the file.
     * @return
     *      Returns a TreeNavigator object with a tree constructed to the specifications of the input .txt file.
     * @throws IOException
     *      Throws an exception if the BufferedReader fails to read the next character in the file that was input.
     * @throws FileNotFoundException
     *      Throws an exception if the file could not be found according to the file path or name that was input by the
     *        user for a parameter.
     */
    public static TreeNavigator buildTree(String treeFile) throws IOException, FileNotFoundException{
        FileReader fileReader = new FileReader(treeFile);
        //int depth determines the depth of the TreeNode being created and helps to organize the tree during
          //the creation loop.
        int depth = -1;
        int numberWords = 0;
        StringBuilder currentKeywords = null;
        //String[] keywords holds the keywords that the TreeNode currently being created will have.
        String[] keywords;
        if((treeFile ==  null) ||  (treeFile ==  ""))
            throw new IllegalArgumentException("Argument String treeFile cannot be null and cannot be empty.");
        BufferedReader reader = new BufferedReader(fileReader);
        int temp;
        //Reads characters from the file as long as there is a file to be read.
        while((temp = reader.read()) != -1){
            //Switch booleans listed above help to figure out what part of the file is currently being read
            if(locationSwitch){
                //If the next character is a semicolon, the program switches to reading the next part of the syntax of
                  //the file
                if(temp ==  ';'){
                    if(depth ==  0){
                        root = new TreeNode();
                        cursor = root;
                        currentDepth = 0;
                    }
                    if(editNo){
                        cursor.setLeft(new TreeNode());
                        cursor = cursor.getLeft();
                        currentDepth++ ;
                        editNo = false;
                    }
                    if(editYes){
                        cursor.setRight(new TreeNode());
                        cursor = cursor.getRight();
                        currentDepth++ ;
                        editYes = false;
                    }
                    locationSwitch = false;
                    keywordSwitch = true;
                    depth = -1;
                    currentKeywords = new StringBuilder();
                    continue;
                }
                //If the character read is a 0, the cursor moves to the left, or creates the node if there is not one
                  //to the left already.
                if(temp ==  '0'){
                    depth++ ;
                    if(depth > 0){
                        if(cursor.getLeft() ==  null)
                            editNo = true;
                        else{
                            cursor = cursor.getLeft();
                            currentDepth++ ;
                        }
                    }
                }
                //If the character read is a 1, the cursor moves to the right, or creates the node if there is not one
                  //to the right already.
                if(temp ==  '1'){
                    depth++ ;
                    if(depth > 0){
                        if(cursor.getRight() ==  null)
                            editYes = true;
                        else{
                            cursor = cursor.getRight();
                            currentDepth++ ;
                        }
                    }
                }
            }
            //This block of code reads the keywords input by the file and reads them into the array. Everytime the
              //reader encounters a semicolon, the file resets the array to prepare for the next list of keywords.
            if(keywordSwitch){
                if(temp ==  ';'){
                    keywordSwitch = false;
                    leafSwitch = true;
                    keywords = new String[numberWords];
                    keywords = currentKeywords.toString().split(",");
                    cursor.setKeywords(keywords);
                    numberWords = 0;
                    continue;
                }
                else
                    if(temp ==  ','){
                        numberWords++ ;
                        currentKeywords.append((char)temp);
                    }
                    else
                        currentKeywords.append((char)temp);
            }
            //Block of code determines when the next line is and when the syntax resets.
            if(leafSwitch){
                maxDepth++ ;
                if(temp ==  '\n'){
                    leafSwitch = false;
                    locationSwitch = true;
                    cursor = root;
                    currentDepth = 0;
                }
            }
        }
        //While loop breaks once the file is done so the last iteration of the loop is put before the TreeNavigator
          //is actually returned
        leafSwitch = false;
        locationSwitch = true;
        cursor = root;
        currentDepth = 0;
        maxDepth++ ;
        fileReader.close();
        reader.close();
        return new TreeNavigator(cursor);
    }
    /**
     * This method takes input from the user in the form of a String and uses the tree and determines whether or not
     *   the text contains one of the keywords found in the TreeNode the cursor is currently on. If the input text
     *   contains at least one of the words found in the keywords, the cursor moves to the right, or the yes node.
     *   Otherwise, the cursor moves to the left, or the no node. This continues until the loop hits a leaf in the tree
     *   and cannot continue. The method then returns the decision, or the last node hit by the loop.
     * @param text
     * @return
     */
    public String classify(String text){
        cursor = root;
        currentDepth = 0;
        //Updates the size of the path array in order to keep track of the course that the cursor took while choosing a
          //decision.
        updatePathArraySize();
        //Empties the array and sets any values in it to -1 since the program will now be reclassifying text and
          //following a new path.
        emptyArray();
        boolean matchedWord = false;
        StringBuilder completeKeywords = new StringBuilder();
        //Loops while the program has not found a leaf, or a decision.
        while(!(cursor.isLeaf())){
            //Loops through the keywords of the cursor's TreeNode to find out whether or not the input text contains
              //any of the keywords of the TreeNode. If a keyword matches, the cursor moves to the right.
            keywordSearchLoop: for(int i = 0; i < cursor.getKeywords().length; i++ ){
                if(text.toLowerCase().contains(cursor.getKeywords()[i].toLowerCase())){
                    cursor = cursor.getRight();
                    path[currentDepth] = 1;
                    currentDepth++ ;
                    matchedWord = true;
                    break keywordSearchLoop;
                }
            }
            //If the program passes through the loop without finding any matches, the cursor moves to the left.
            if(!matchedWord){
                cursor = cursor.getLeft();
                path[currentDepth] = 0;
                currentDepth++ ;
            }
            matchedWord = false;
        }
        //Returns the keywords of the decision node to show the user what the text entered was classified as.
        if(cursor.getKeywords().length > 1){
            for(int j = 0; j < cursor.getKeywords().length; j++ ){
                completeKeywords.append(cursor.getKeywords()[j]);
                completeKeywords.append(",");
            }
            return completeKeywords.toString();
        }
        else
            return cursor.getKeywords()[0];
    }
    /**
     * This method is used in order to return a String that contains the path that the program used in order to arrive
     *   at a decision for the text that the user inputs.
     * @return
     *      Returns a String that contains the list of keywords and the decisions the program made at each node to
     *        arrive at the conclusion for the text that the user entered.
     */
    public String getPath(){
        int i = 0;
        StringBuilder result = new StringBuilder();
        TreeNode printCursor = root;
        if(cursor ==  root){
            return "DECISION: " + printCursor.getKeywordsString();
        }
        while(path[i] != -1){
            if(path[i] ==  0){
                result.append("NOT " + printCursor.getKeywordsString() + ", ");
                printCursor = printCursor.getLeft();
            }
            if(path[i] ==  1){
                result.append("IS " + printCursor.getKeywordsString() + ", ");
                printCursor = printCursor.getRight();
            }
            if((path[i+1] ==  -1) ||  (path[i] ==  -1)){
                result.append("DECISION: " + printCursor.getKeywordsString() + "\n");
                break;
            }
            i++ ;
        }
        return result.toString();
    }
    /**
     * This method is used to move the cursor back to the root. It also empties the array since the cursor is moved
     *   back to the root. Also prints that the cursor was moved to the root and the keywords at that node.
     * @precondition
     *      This TreeNavigator must have root initialized to something that is not null.
     * @postcondition
     *      Cursor is now at the root TreeNode.
     * @throws NullPointerException
     *      Throws an exception if root is null.
     */
    public void resetCursor(){
        cursor = root;
        updatePathArraySize();
        emptyArray();
        currentDepth = 0;
        System.out.println("Cursor moved to root. Cursor is at root. Message is: '"
          + cursor.getKeywordsString() + "'");
        hasRun = true;
    }
    /**
     * This method is used to move the cursor to the left child of the TreeNode. It also prints whether or not the node
     *   that the cursor is now at is a leaf or not. It also prints the keywords of the TreeNode.
     * @precondition
     *      The TreeNode that the cursor was at before the method was called must have a left TreeNode child.
     * @postcondition
     *      Cursor is now at the left child of the TreeNode it was at before.
     * @throws NullPointerException
     *      Throws an exception if the left child of the TreeNode is null or does not exist.
     */
    public void cursorLeft(){
        if(!hasRun){
            updatePathArraySize();
            emptyArray();
        }
        cursor = cursor.getLeft();
        path[currentDepth] = 0;
        currentDepth++ ;
        if(cursor.isLeaf())
            System.out.println("Cursor moved to the left. Cursor is at leaf. Message is: '"
              + cursor.getKeywordsString() + "'");
        else
            System.out.println("Cursor moved to the left. Cursor is not at leaf. Message is: '"
              + cursor.getKeywordsString() + "'");
    }
    /**
     * This method is used to move the cursor to the right child of the TreeNode. It also prints whether or not the
     *   node that the cursor is now at is a leaf or not. It also prints the keywords of the TreeNode.
     * @precondition
     *      The TreeNode that the cursor was at before the method was called must have a right TreeNode child.
     * @postcondition
     *      Cursor is now at the right child of the TreeNode it was at before.
     * @throws NullPointerException
     *      Throws an exception if the right child of the TreeNode is null or does not exist.
     */
    public void cursorRight(){
        if(!hasRun){
            updatePathArraySize();
            emptyArray();
        }
        cursor = cursor.getRight();
        path[currentDepth] = 1;
        currentDepth++ ;
        if(cursor.isLeaf())
            System.out.println("Cursor moved to the right. Cursor is at leaf. Message is: '"
              + cursor.getKeywordsString() + "'");
        else
            System.out.println("Cursor moved to the right. Cursor is not at leaf. Message is: '"
              + cursor.getKeywordsString() + "'");
    }
  /*
    public void cursorToParent(){
        if(isRoot()){
            cursor = root;
            emptyArray();
            currentDepth = 0;
            System.out.println("Cursor moved to parent. Cursor is at root. Message is: '"
              + cursor.getKeywordsString() + "'");
            return;
        }
        int i;
        cursor = root;
        currentDepth = 0;
        for(i = 0; i < path.length - 2; i++ ){
            if(path[i] ==  0)
                cursor = cursor.getLeft();
            if(path[i] ==  1)
                cursor = cursor.getRight();
            currentDepth++ ;
        }
        path[i] = -1;
        System.out.println("Cursor moved to parent. Cursor is not at leaf. Message is: '"
          + cursor.getKeywordsString() + "'");
    }
    */
    /**
     * Used to return a reference to the cursor of this TreeNavigator object that may be used to edit the TreeNode
     *   object that is being currently referenced by the TreeNode. Returns null if cursor is initialized to null.
     * @return
     *      Returns the TreeNode that the cursor of this TreeNavigator object is referencing or null if cursor is
     *        initialized to null.
     */
    public TreeNode getCursor(){
        if(cursor ==  null)
            return null;
        return cursor;
    }
    /**
     * Method used to edit the keywords of the TreeNode that the cursor is currently referencing. The input text is
     *   delimited by commas in order to separate multiple keywords if the user wants to have more than one keyword.
     * @param text
     *      String text is the input by the user that the keywords of the TreeNode being referenced will be changed to.
     * @postcondition
     *      String[] keywords of the TreeNode being referenced by the cursor is set to String text that is delimited by
     *        commas.
     * @throws IOException
     *      Throws an exception if the BufferedReader object cannot read the next character in the string input by the
     *        user.
     */
    public void editCursor(String text) throws IOException{
        int length = text.length();
        int numKeywords = 1;
        int arrayCursor = 0;
        for(int i = 0; i < length; i++ ){
            if(text.charAt(i) ==  ',')
                numKeywords++ ;
        }
        String[] keywords = new String[numKeywords];
        int temp;
        StringBuilder currentWord = new StringBuilder();
        StringReader input = new StringReader(text);
        BufferedReader reader = new BufferedReader(input);
        while(((temp = reader.read()) != -1)){
            if((char)temp ==  ','){
                keywords[arrayCursor] = currentWord.toString();
                currentWord.setLength(0);
                arrayCursor++ ;
            }
            else
                currentWord.append((char)temp);
        }
        keywords[arrayCursor] = currentWord.toString();
        cursor.setKeywords(keywords);
    }
    /**
     * Determines whether or not there are any TreeNode objects in this TreeNavigator object.
     * @return
     *      Returns true if null is initialized to null, or false if it is anything else.
     */
    public boolean isEmpty(){
        if(root ==  null)
            return true;
        return false;
    }
    /**
     * Sets the maxDepth of the tree held within this TreeNavigator object to the user input.
     * @param newMaxDepth
     *      int newMaxDepth is what the maxDepth of this TreeNavigator object will be set to.
     * @postcondition
     *      int maxDepth is set to int newMaxDepth that was input by the user.
     */
    public void setMaxDepth(int newMaxDepth){
        maxDepth = newMaxDepth;
    }
    /**
     * Returns the value of int maxDepth of this TreeNavigator object.
     * @return
     *      Returns the int maxDepth of this TreeNavigator object.
     */
    public int getMaxDepth(){
        return maxDepth;
    }
    /**
     * Updates the total length of the int[] path array in this TreeNavigator object in order for the program to use as
     *   little memory as possible. If the int[] path array is not yet initialized to something besides null, the
     *   method initializes the array to a size of int maxDepth. If the maxDepth is larger, the values of int[] path
     *   are stored in a temporary array and the size of int[] path is initialized to something larger. The values in
     *   the temp array are then put back into the int[] path array along with -1 filling the remainder of the indexes.
     *   If maxDepth is smaller than the int[] path array, then the size of the array is initialized to the lower
     *   maxDepth after all the values are stored in the int[] temp array.
     *   @postcondition
     *      Changes the size of the int[] path array to int maxDepth, with -1 values filling any indexes that are now
     *        empty.
     */
    public void updatePathArraySize(){
        if(path ==  null){
            path = new int[maxDepth + 1];
            for(int i = 0; i < path.length; i++ )
                path[i] = -1;
        }
        if(maxDepth > path.length){
            int[] temp = new int[path.length];
            for(int j = 0; j < temp.length; j++ ){
                temp[j] = path[j];
            }
            path = new int[maxDepth + 1];
            for(int k = 0; k < path.length; k++ ){
                if(k >= temp.length)
                    path[k] = -1;
                else
                    path[k] = temp[k];
            }
        }
        if(maxDepth < path.length){
            int[] temp = new int[path.length];
            for(int l = 0; l < maxDepth; l++ )
                temp[l] = path[l];
            path = new int[maxDepth + 1];
            for(int m = 0; m < path.length-1; m++ )
                path[m] = temp[m];
            path[maxDepth] = -1;
        }
    }
    /**
     * Sets all the values in the int[] path array to -1, which serves as an empty index.
     * @postcondition
     *      int[] path array now has -1 values in every index.
     */
    public void emptyArray(){
        for(int i = 0; i < path.length; i++ )
            path[i] = -1;
    }
    /**
     * Returns a boolean that tells the user whether or not the cursor is currently referencing the root TreeNode of
     *   this TreeNavigator object.
     * @return
     *      Returns true if cursor is referencing root, and false otherwise.
     */
    public boolean isRoot(){
        if(cursor ==  root)
            return true;
        return false;
    }
}
