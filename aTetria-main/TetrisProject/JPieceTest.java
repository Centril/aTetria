 JPieceTest.java 	a�� 8"	a�`J  J��Gt	K=X	KB�	I��	L�p   	a��	K�TEXTCWIE ����      �  ���ȷd�                       ��v  // JPieceTest.java

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;


/**
 Debugging client for the Piece class.
 The JPieceTest component draws all the rotations of a tetris piece.
 JPieceTest.main()  creates a frame  with one JPieceTest for each
 of the 7 standard tetris pieces.
 
 This is the starter file version -- 
 The outer shell is done. You need to complete paintComponent()
 and drawPiece().
*/
class JPieceTest extends JComponent {
	protected Piece root;	
	

	public JPieceTest(Piece piece, int width, int height) {
		super();
		
		setPreferredSize(new Dimension(width, height));

		root = piece;
	}

	/**
	 Draws the rotations from left to right.
	 Each piece goes in its own little box.
	*/
	public final int MAX_ROTATIONS = 4;
	public void paintComponent(Graphics g) {
	}
	
	/**
	 Draw the piece inside the given rectangle.
	*/
	private void drawPiece(Graphics g, Piece piece, Rectangle r) {
	}	


	/**
	 Draws all the pieces by creating a JPieceTest for
	 each piece, and putting them all in a frame.
	*/
	static public void main(String[] args)
	
	{
		JFrame frame = new JFrame("Piece Tester");
		JComponent container = (JComponent)frame.getContentPane();
		
		// Put in a BoxLayout to make a vertical list
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Piece[] pieces = Piece.getPieces();
		
		for (int i=0; i<pieces.length; i++) {
			JPieceTest test = new JPieceTest(pieces[i], 375, 75);
			container.add(test);
		}
		
		// Size the window and show it on screen
		frame.pack();
		frame.setVisible(true);
		
		// Quit on window close
		frame.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
	}
}

            T   T   F:�5~�;���@�ϥ���1��B9q1�����WGxe0���Pd��/Ga��e�ŗ��s��.xZ?�z�YT�K������GJ�*�Z�-��5b�O�۔��6z�?����1YK��C���U�s�P�Q�#���k��-�CۛIxQ�o��<G�>��x �C�w�R���?a�ȟ ���붅��{|�$�ϰdm�cQ�Uy(?���q��@�e+�;�)�a���L�����   H 	Monaco                             : 5)� : 5)��d�  �  �               T   T   FSORT �  F MPSR   MWBB   ���        ���   L                                                                                                          