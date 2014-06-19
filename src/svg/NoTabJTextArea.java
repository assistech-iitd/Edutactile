/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package svg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;

/**
 *
 * @author Mrinal
 */
public class NoTabJTextArea extends JTextArea implements KeyListener {

   public NoTabJTextArea ( String text ) {
      super ( text ) ;
      initialize () ;
   }

   public NoTabJTextArea ( ) {
      super() ;
      initialize() ;
   }

   private void initialize () {
      addKeyListener ( this ) ;
   }

   @Override
   public void keyPressed ( KeyEvent e ) {

      switch ( e.getKeyCode() ) {
         case KeyEvent.VK_TAB :
            e.consume() ;
            transferFocus() ;
            break ;
      }
   }

   @Override
   public void keyReleased ( KeyEvent e ) {
      switch ( e.getKeyCode() ) {
         case KeyEvent.VK_TAB :
            break ;
      }
   }

   @Override
   public void keyTyped ( KeyEvent e ) {
      switch ( e.getKeyCode() ) {
         case KeyEvent.VK_TAB :
            break ;
      }

   }

   
} /* NoTabJTextArea */
