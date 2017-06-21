package mkobilas.homework.decisiontree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/**
 * This class is used to receive input from the user in order to manipulate the TreeNavigator decisionMaker object that
 *   is contained within this class. This class serves as a driver for the entirety of this program. The program can
 *   take input from the user in order to classify some of the input entered by the user that may allow the
 *   decisionMaker tree to determine where the text may end up according to the keywords held within the TreeNode
 *   objects that the decisionMaker tree contains. This class also allows the user to import a tree using a .txt file.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class DecisionTreeClassifier {
    //TreeNavigator decisionMaker is the tree that the user will be manipulating with use of two menus that give the
      //user options for input.
    private static TreeNavigator decisionMaker = null;
    //Used for determining whether input by the user was valid and which menu is currently being displayed.
    private static boolean isMain = true, invalidResponse = false, invalidFile = false;
    //Receives input from the user.
    private static Scanner keyboard = new Scanner(System.in);
    private static String answer;
    private static String file;
    /**
     * This method is set to main, though the parameter String[] args is not necessarily used. This method contains
     *   the main loop which will loop continuously until the program receives input from the user to quit the program.
     * @param args
     *      String[] args is used to allow the main method to be considered a main method.
     */
    public static void main(String[] args) {
        String temp1 = "";
        String temp2 = "";
        file = "";
        System.out.println("Welcome to the DescisionTree Classifier\n");
        //Loop which continues until the program receives the quit signal from the user.
        mainLoop: while(true){
            //Prints the menu that should currently be gettting displayed.
            if(isMain)
                printMainMenu();
            else
                printEditMenu();
            //Loop which determines whether or not input was valid.
            answerLoop: while(true){
                if(invalidResponse){
                    System.out.print("\nPlease select a valid option: ");
                    invalidResponse = false;
                }
                else
                    System.out.print("Please select an option: ");
                answer = keyboard.nextLine();
                if(answer.length() ==  1)
                    break answerLoop;
                else
                    invalidResponse = true;
            }
            System.out.println("");
            //This set of switch/case statements is only used if the main menu is currently being displayed.
            if(isMain)
                switch(answer.toLowerCase()){
                    //Prompts the user for a file name or file path to a .txt file that will be used to create a tree
                      //out of TreeNode objects in the TreeNavigator decisionMaker
                    case "i":
                        fileLoop: while(true){
                            if(invalidFile){
                                System.out.print("File not found.\n");
                                invalidFile = false;
                                continue mainLoop;
                            }
                            else
                                System.out.print("Please enter a file name/path: ");
                            try{
                                temp1 = keyboard.nextLine();
                                System.out.println("");
                                file = temp1;
                                decisionMaker = TreeNavigator.buildTree(file);
                                break fileLoop;
                            }
                            catch(FileNotFoundException err){
                                invalidFile = true;
                                continue fileLoop;
                            }
                            catch(IOException err){
                                System.out.print("FATAL ERROR: Error occured while reading next character from " + 
                                  "file.");
                                break mainLoop;
                            }
                            catch(NullPointerException err){
                                System.out.print("Incorrect syntax in target .txt file.\n");
                                continue mainLoop;
                            }
                        }
                        System.out.println("Tree loaded.");
                        continue mainLoop;
                    //Takes the user to the editing menu if TreeNavigator object has already been initialized to
                      //something that is not null. If it has not yet been initialized, then pressing e will initialize
                      //the TreeNavigator object, then take the user to the editing menu.
                    case "e":
                        if(decisionMaker ==  null){
                            decisionMaker = new TreeNavigator(new TreeNode("tree is empty".split("\0")));
                            
                        }
                        isMain = false;
                        decisionMaker.resetCursor();
                        continue mainLoop;
                    //Takes input from the user and then attempts to classify the input according to the tree currently
                      //being used in the TreeNavigator object. Prints the decision made by the decisionMaker.
                    case "c":
                        try{
                            if(decisionMaker.isEmpty()){
                                System.out.println("The decision-making tree is currently empty.");
                                continue mainLoop;
                            }
                        }
                        catch(NullPointerException err){
                            System.out.println("The decision-making tree is currently empty.");
                            continue mainLoop;
                        }
                        System.out.print("Please enter some text: ");
                        temp1 = keyboard.nextLine();
                        System.out.println("");
                        System.out.println("Your request is classified as: " +
                          decisionMaker.classify(temp1));
                        continue mainLoop;
                    //Takes input from the user and then attempts to classify the input according to the tree currently
                      //being used in the TreeNavigator object. Prints the path used by the decisionMaker to make the
                      //the classification of the input.
                    case "p":
                        try{
                            if(decisionMaker.isEmpty()){
                                System.out.println("The decision-making tree is currently empty.");
                                continue mainLoop;
                            }
                        }
                        catch(NullPointerException err){
                            System.out.println("The decision-making tree is currently empty.");
                            continue mainLoop;
                        }
                        System.out.print("Please enter some text: ");
                        temp1 = keyboard.nextLine();
                        System.out.println("");
                        decisionMaker.classify(temp1);
                        try{
                            System.out.print("Decision path: " + decisionMaker.getPath());
                        }
                        catch(NullPointerException err){
                            System.out.println("The decision-making tree is currently empty.");
                        }
                        continue mainLoop;
                    //Quits the program
                    case "q":
                        break mainLoop;
                    //Only used if the user enters a response that does not fit any of the options given in the menu
                    default:
                        invalidResponse = true;
                        continue mainLoop;
                }
            //Second set of options used if the user needs to edit the tree currently in the TreeNavigator
            else
                switch(answer.toLowerCase()){
                    //Edits the keywords in the cursor's TreeNode, and changes them to the user's input. Multiple
                      //keywords may be entered delimited by commas.
                    case "e":
                        System.out.print("Please enter keywords for this node, separated by commas: ");
                        temp1 = keyboard.nextLine();
                        System.out.println("");
                        decisionMaker.getCursor().setKeywords(temp1.split(","));
                        System.out.println("Keywords updated to: " + temp1);
                        continue mainLoop;
                    //Creates two child TreeNodes beneath the cursor, and prompts the user for the keywords for the two
                      //children. Multiple keywords may be entered by using commas as delimiters.
                    case "c":
                        if(decisionMaker.getCursor().isLeaf()){
                            System.out.print("Please enter terminal text for the no leaf: ");
                            temp1 = keyboard.nextLine();
                            System.out.print("\nPlease enter terminal text for the yes leaf: ");
                            temp2 = keyboard.nextLine();
                            System.out.println("");
                            decisionMaker.getCursor().setLeft(new TreeNode(temp1.split(",")));
                            decisionMaker.getCursor().setRight(new TreeNode(temp2.split(",")));
                            System.out.println("Children are: no - '"
                              + decisionMaker.getCursor().getLeft().getKeywordsString()
                              + "' and yes - '" + decisionMaker.getCursor().getRight().getKeywordsString() + "'");
                            decisionMaker.setMaxDepth(decisionMaker.getMaxDepth() + 2);
                        }
                        else{
                            System.out.print("Please enter terminal text for the new no leaf: ");
                            temp1 = keyboard.nextLine();
                            System.out.print("\nPlease enter terminal text for the new yes leaf: ");
                            temp2 = keyboard.nextLine();
                            System.out.println("");
                            decisionMaker.getCursor().setLeft(new TreeNode(temp1.split(",")));
                            decisionMaker.getCursor().setRight(new TreeNode(temp2.split(",")));
                            System.out.println("New children are: no - '"
                              + decisionMaker.getCursor().getLeft().getKeywordsString()
                              + "' and yes - '" + decisionMaker.getCursor().getRight().getKeywordsString() + "'");
                        }
                        decisionMaker.updatePathArraySize();
                        continue mainLoop;
                    //Deletes the children of the cursor, and prompts the user for new keywords for the cursor's
                      //TreeNode.
                    case "d":
                        System.out.print("Please enter a terminal text for this node: ");
                        temp1 = keyboard.nextLine();
                        System.out.println("");
                        if((decisionMaker.getCursor().getLeft() != null)
                          && (decisionMaker.getCursor().getRight() != null))
                              decisionMaker.setMaxDepth(decisionMaker.getMaxDepth() - 1);
                        decisionMaker.getCursor().setLeft(null);
                        decisionMaker.getCursor().setRight(null);
                        decisionMaker.getCursor().setKeywords(temp1.split(","));
                        if(!(decisionMaker.isRoot())){
                            System.out.println("Current node is leaf. Text is: '" + 
                              decisionMaker.getCursor().getKeywordsString() + "'");
                        }
                        else{
                            System.out.println("Current node is root. Text is: '" +
                              decisionMaker.getCursor().getKeywordsString() + "'");
                            decisionMaker.setMaxDepth(0);
                        }
                        decisionMaker.updatePathArraySize();
                        continue mainLoop;
                    //Moves the cursor to its left TreeNode child if it exists and prints its keywords.
                    case "n":
                        if(decisionMaker.getCursor().getLeft() ==  null){
                            System.out.println("There is no left/no node to move to.");
                            continue mainLoop;
                        }
                        else{
                            decisionMaker.cursorLeft();
                            continue mainLoop;
                        }
                    //Moves the cursor to its right TreeNode child if it exists and prints its keywords.
                    case "y":
                        if(decisionMaker.getCursor().getRight() ==  null){
                            System.out.println("There is no right/yes node to move to.");
                            continue mainLoop;
                        }
                        else{
                            decisionMaker.cursorRight();
                            continue mainLoop;
                        }
                    //Moves the cursor back to the root TreeNode and prints its keywords.
                    case "r":
                        decisionMaker.resetCursor();
                        continue mainLoop;
                    //Extra credit that I didn't do.
                    /*
                    case "p":
                        decisionMaker.cursorToParent();
                        continue mainLoop;
                     */
                    //Takes the user back to the main menu.
                    case "m":
                        isMain = true;
                        continue mainLoop;
                    //Used to tell the user if they entered something that wasn't in the options menu.
                    default:
                        invalidResponse = true;
                        continue mainLoop;
                }
        }
        //Closes any controllers being used for input, and shuts the program down without error.
        keyboard.close();
        System.out.println("SHUTTING DOWN");
        System.exit(0);
    }
    /**
     * Method used to print the main menu in a neatly formatted fashion.
     * @postcondition
     *      Prints the main menu to standard out.
     */
    private static void printMainMenu(){
        System.out.println("\nMain Menu:");
        System.out.println("        I) Import a tree from a file");
        System.out.println("        E) Edit current tree");
        System.out.println("        C) Classify a description");
        System.out.println("        P) Show decisison path for a description");
        System.out.println("        Q) Quit");
    }
    /**
     * Method used to print the editing menu in a neatly formatted fashion.
     * @postcondition
     *      Prints the edit menu to standard out.
     */
    private static void printEditMenu(){
        System.out.println("\nEdit Menu:");
        System.out.println("        E) Edit keywords");
        System.out.println("        C) Add children");
        System.out.println("        D) Delete children and make this node a leaf");
        System.out.println("        N) Cursor to \"no\" child");
        System.out.println("        Y) Cursor to \"yes\" child");
        System.out.println("        R) Cursor to root");
        //More extra credit that I didn't do.
        //System.out.println("        P) Cursor to parent");
        System.out.println("        M) Go back to main menu");
    }
}