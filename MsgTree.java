package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
 * @author Lachlan Kreunen
 *
 */


/**
 * The MsgTree class represents a binary tree used for encoding and decoding messages.
 */
public class MsgTree {
    
    public char payloadChar;
    public MsgTree left;
    public MsgTree right;
    private static String binaryCode;

    /**
     * Constructs a MsgTree based on the given encodingString.
     *
     * @param encodingString The string containing the encoding information for the tree.
     */
    public MsgTree(String encodingString) {
        int currentIndex = 0;
        this.payloadChar = encodingString.charAt(currentIndex++);

        if (this.payloadChar != '^') {
            return;
        }

        Stack<MsgTree> parentStack = new Stack<>();
        parentStack.push(this);

        while (currentIndex < encodingString.length()) {
            MsgTree parentNode = parentStack.peek();
            MsgTree newNode = new MsgTree(encodingString.charAt(currentIndex++));

            if (parentNode.left == null) {
                parentNode.left = newNode; 
            } else if (parentNode.right == null) {
                parentNode.right = newNode; 
                parentStack.pop(); 
                	
        
            } if (newNode.payloadChar == '^') {
                parentStack.push(newNode);
            }
            

        }
    }


    /**
     * Constructs a leaf node with the given payloadChar and null children.
     *
     * @param payloadChar The character payload of the leaf node.
     */
    public MsgTree(char payloadChar) {
        this.payloadChar = payloadChar;
        this.left = null;
        this.right = null;
    }

    /**
     * Prints the binary codes for each character based on the given tree and code.
     *
     * @param tree The MsgTree used for encoding.
     * @param code The binary code for characters.
     */
    public static void printCodes(MsgTree root, String code) {
        System.out.println("character code");
        System.out.println("-------------------------\n");
        for (char currCharacter : code.toCharArray()) {
            alphaSet(root, currCharacter, binaryCode = "");
            System.out.println("    " + (currCharacter == '\n' ? "\\n" : currCharacter + " ") + "    " + binaryCode);
        }
    }

    /**
     * Decodes the given message using the provided codes and prints the result.
     *
     * @param codes The MsgTree containing the encoding information.
     * @param msg   The binary message to decode.
     */
    public void decode(MsgTree codes, String msg) {
        StringBuilder decodedMessage = new StringBuilder(); // To build the decoded message
        MsgTree currentNode = codes; // Start at the root of the tree

        for (char bit : msg.toCharArray()) {
            currentNode = (bit == '0') ? currentNode.left : currentNode.right;

            if (currentNode.left == null && currentNode.right == null) {
                decodedMessage.append(currentNode.payloadChar);
                currentNode = codes;
            }
        }

        System.out.println("MESSAGE:");
        System.out.println(decodedMessage.toString());
        stats(msg, decodedMessage.toString());
    }


    /**
     * Recursively searches for the binary code of a character in the tree.
     *
     * @param currNode      The current node in the tree.
     * @param currCharacter The character to search for.
     * @param path          The current path in the binary tree.
     * @return True if the character is found, false otherwise.
     */
    private static boolean alphaSet(MsgTree currNode, char currCharacter, String path) {
        if (currNode != null) {
            
            if (currNode.payloadChar == currCharacter) {
                binaryCode = path;
                return true;
            }

            return alphaSet(currNode.left, currCharacter, path + "0") || 
                   alphaSet(currNode.right, currCharacter, path + "1");
        }

        
        return false;
    }


    /**
     * Prints statistics about the decoding process.
     *
     * @param encStr The original encoded message.
     * @param decStr The decoded message.
     */
    private void stats(String encStr, String decStr) {
        System.out.println("STATISTICS:");
        System.out.println(String.format("Avg bits/char:\t%.1f", encStr.length() / (double)decStr.length()));
        System.out.println("Total Characters:\t" + decStr.length());
        System.out.println(String.format("Space Saving:\t%.1f%%", (1d - decStr.length() / (double)encStr.length()) * 100));
    }
}