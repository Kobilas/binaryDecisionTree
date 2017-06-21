package mkobilas.homework.decisiontree;
/**
 * The TreeNode class is used to make objects of the type TreeNode that are then used to organize a binary tree used
 *   later in the program for making decisions based on the input from the user. TreeNode objects are used to wrap
 *   keywords that are entered by the user which are then used to determine the decision made by the program to 
 *   classify what the user later inputs.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      Stony Brook ID: 111152838
 *      CSE214-R02
 */
public class TreeNode {
    private String[] keywords;
    private TreeNode left;
    private TreeNode right;
    /**
     * Empty constructor for objects of type TreeNode. The left and right references to subtrees are initialized to
     *   null since the references do not have anything to reference yet. String[] keywords is also initialized to null
     *   to allow the user to later enter the keywords using the mutator method for keywords.
     * @postcondition
     *      Creates an object of type TreeNode with String[] keywords, TreeNode left, and TreeNode right all
     *        initialized to null.
     */
    public TreeNode(){
        left = null;
        right = null;
        keywords = null;
    }
    /**
     * Constructor asking for an array of Strings as a parameter in order to initialize keywords with the text within
     *   the input array.
     * @param text
     *      String[] text is the array of text that the String[] keywords array in this TreeNode object will be
     *        initialized to.
     * @postcondition
     *      Creates an object of type TreeNode with String[] keywords initialized to the user's input for String[]
     *        text. TreeNode left and TreeNode right references are initialized to null.
     */
    public TreeNode(String[] text){
        left = null;
        right = null;
        keywords = new String[text.length];
        for(int i = 0; i < text.length; i++ )
            keywords[i] = text[i];
    }
    /**
     * Constructor asking for an array of Strings, as well as the TreeNode objects that stand for the left and right
     *   subtrees of this TreeNode.
     * @param text
     *      String[] text is the array of text that the String[] keywords array in this TreeNode object will be
     *        initialized to.
     * @param leftChild
     *      TreeNode leftChild is the reference to the left subtree of this TreeNode object as well as what the
     *        TreeNode left object will be initialized to.
     * @param rightChild
     *      TreeNode rightChild is the reference to the right subtree of this TreeNode object as well as what the
     *        TreeNode right object will be initialized to.
     */
    public TreeNode(String[] text, TreeNode leftChild, TreeNode rightChild){
        keywords = new String[text.length];
        for(int i = 0; i < text.length; i++ )
            keywords[i] = text[i];
        left = leftChild;
        right = rightChild;
    }
    /**
     * Mutator method for this TreeNode object's String[] keywords array. If the length of the parameter String[]
     *   newText array is longer than the current String[] keywords array, then the method will instantiate a new
     *   array for this TreeNode object to use that is of an appropriate size.
     * @param newText
     *      String[] newText is the parameter that the String[] keywords array in this TreeNode object will be
     *        set to.
     * @postcondition
     *      String[] keywords will have the same size as well as all the same text as String[] newText.
     */
    public void setKeywords(String[] newText){
        if(keywords ==  null){
            keywords = new String[newText.length];
            for(int i = 0; i < newText.length; i++ )
                keywords[i] = newText[i];
        }
        else{
            if(keywords.length != newText.length){
                keywords = new String[newText.length];
                for(int i = 0; i < newText.length; i++ )
                    keywords[i] = newText[i];
            }
            else
                for(int i = 0; i < newText.length; i++ )
                    keywords[i] = newText[i];
        }
    }
    /**
     * Accessor method for this TreeNode object's String[] keywords array. 
     * @return
     *      Returns a reference to this TreeNode object's String array which contains the keywords of this TreeNode
     *        object.
     */
    public String[] getKeywords(){
        return keywords;
    }
    /**
     * Alternate accessor method for this TreeNode object's String[] keywords array which returns a String of the
     *   text in the array separated by commas.
     * @return
     *      Returns a String of the text in this TreeNode object's String[] keywords array separated by commas.
     */
    public String getKeywordsString(){
        StringBuilder result = new StringBuilder();
        if(keywords.length <= 1)
            return keywords[0];
        for(int i = 0; i < keywords.length; i++ ){
            result.append(keywords[i]);
            if(!(i+1 ==  keywords.length))
                result.append(",");
        }
        return result.toString();
    }
    /**
     * Mutator method for this TreeNode object's TreeNode left, which references the left subtree of this object.
     * @param newLeftChild
     *      TreeNode newLeftChild is the reference to the left subtree of this TreeNode object.
     * @postcondition
     *      TreeNode left is set to TreeNode newLeftChild.
     */
    public void setLeft(TreeNode newLeftChild){
        left = newLeftChild;
    }
    /**
     * Accessor method for this TreeNode object's TreeNode left, which references the left subtree of this object.
     * @return
     *      Returns TreeNode left of this TreeNode object, which is the left child TreeNode of this object.
     */
    public TreeNode getLeft(){
        return left;
    }
    /**
     * Mutator method for this TreeNode object's TreeNode right, which references the right subtree of this object.
     * @param newRightChild
     *      TreeNode newRightChild is the reference to the right subtree of this TreeNode object.
     * @postcondition
     *      TreeNode right is set to TreeNode newRightChild.
     */
    public void setRight(TreeNode newRightChild){
        right = newRightChild;
    }
    /**
     * Accessor method for this TreeNode object's TreeNode right, which references the right subtree of this object.
     * @return
     *      Returns TreeNode right of this TreeNode object, which is the right child TreeNode of this object.
     */
    public TreeNode getRight(){
        return right;
    }
    /**
     * Method used to deteremine whether or not this TreeNode object has right and left subtrees or TreeNode children.
     * @return
     *      Returns true if this TreeNode object does not have right and left children (right and left references are
     *        equal to null), or false if it has either a right or left child.
     */
    public boolean isLeaf(){
        if((left ==  null) && (right ==  null))
            return true;
        return false;
    }
    /**
     * Method used mainly for debugging that returns a String of the keywords in this TreeNode object as well as what
     *   index it is in the array.
     * @return
     *      Returns a String of the keywords and indexes of the String[] keywords array in this TreeNode object.
     */
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < keywords.length; i++ ){
            if(i+1 ==  keywords.length)
                result.append(i);
            else
                result.append(i + " ");
        }
        return result.toString();
    }
}
