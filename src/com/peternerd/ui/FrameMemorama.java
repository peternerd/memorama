/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peternerd.ui;

import com.peternerd.Image;
import com.peternerd.Memorama;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author pedro
 */
public class FrameMemorama extends JFrame implements ActionListener{
    
    JButton btnAgregar;
    JPanel pnlButtons;
    int clicks = 0;
    Stack<Image> pares = new Stack();
    Stack<JButton> btnPressed = new Stack();
    Memorama memorama;
    
    FrameMemorama(){
        super("Memorama");
        pnlButtons = new JPanel();
        btnAgregar = new JButton("Agregar");
        //agrega un evento al boton agregar xD el cual lanza una ventana para seleccionar las imagenes
        btnAgregar.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //crea el memorama con las imagenes seleccionadas
                memorama = new Memorama(selectImages());
                //muestra el tablero
                displayTablero();
            }
        
        });
        this.setContentPane(pnlButtons);
        pnlButtons.add(btnAgregar);
        setSize(800,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * Crea un selector de archivos para escoger las imagenes del memorama
     * @return array que contiene las rutas de las imagenes seleccionadas
     */
    public String[] selectImages(){
        JFileChooser selector = new JFileChooser();
        selector.setMultiSelectionEnabled(true);
        selector.showOpenDialog(null);
        //obtiene todos los archivos seleccionados y los almacena en un array de File
        File imagenes[] = selector.getSelectedFiles();
        String rutas[] = new String[imagenes.length];
        for(int i=0;i<imagenes.length;i++){
            rutas[i]=imagenes[i].getAbsolutePath();
        }  
        return rutas;
    }
    
    /**
     * Muestra el tablero con con una imagen de fondo por cada imagen seleccionada
     */
    public void displayTablero(){
        //quita el boton agregar del panel
        pnlButtons.remove(btnAgregar);
        //Itera sobre el total de imagenes y agrega un boton por cada una
        for(Image img:memorama.getAllImages()){
            //agrega un boton con una imagen de fondo 
            //pnlButtons.add(new JButton(new ImageIcon(img.getPath())));
            pnlButtons.add(new JButton(new ImageIcon(this.getClass().getResource("/com/peternerd/ui/images/poke.png"))));
        }
        addEvents(pnlButtons.getComponents());
        this.validate();
        this.repaint();
        System.out.println("Hello");
    }
    
    public void addEvents(Component components[]){
        for(Component c:components){
            ((JButton)c).addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component[] c = pnlButtons.getComponents();
        JButton btn = (JButton)e.getSource();
      for(int i=0; i<c.length; i++){
        if( btn.equals((JButton)c[i])){
            Image img = memorama.getImage(i);
            btn.setIcon(new ImageIcon(img.getPath()));
            pares.push(img);
            btnPressed.push(btn);
            clicks++;
            if(clicks==2){
                JButton btn1 = btnPressed.pop();
                JButton btn2 = btnPressed.pop();
                if(!isPair()){
                    JOptionPane.showMessageDialog(this, "Continua");
                    btn1.setIcon(new ImageIcon(this.getClass().getResource("/com/peternerd/ui/images/poke.png")));
                    btn2.setIcon(new ImageIcon(this.getClass().getResource("/com/peternerd/ui/images/poke.png")));
                }
                else{
                    btn1.setEnabled(false);
                    btn2.setEnabled(false); 
                }
                clicks=0;
            }
            break;
        }
      }
    }
    
    public boolean isPair(){
        Image img1 = pares.pop();
        Image img2 = pares.pop();
        if(img1.getPath().equals(img2.getPath())){
            return true;
        }
        else{
            return false;
        }
    }
}
