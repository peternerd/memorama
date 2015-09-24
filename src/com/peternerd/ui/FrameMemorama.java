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
    
    /**
     * Agrega el evento de click a cada uno de los botones generados
     * @param components la lista de botones para agregar evento click
     */
    public void addEvents(Component components[]){
        for(Component c:components){
            ((JButton)c).addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtiene todos los botones en el panel de botones
        Component[] c = pnlButtons.getComponents();
        //obtiene el boton que lanzo el evento
        JButton btn = (JButton)e.getSource();
        //se itera sobre el total de botones
        for(int i=0; i<c.length; i++){
        //si el boton presionado es igual a el boton que esta en la posicion i de el panel
        if( btn.equals((JButton)c[i])){
            //obtienen el objeto Image del memorama en la posicion i
            Image img = memorama.getImage(i);
            //se obtiene la ruta de la imagen y se pone en el boton
            btn.setIcon(new ImageIcon(img.getPath()));
            //inserta en la pila de pares la imagen
            pares.push(img);
            //inserta en la pila de botones presionados el boton
            btnPressed.push(btn);
            //aumenta el numero de clicks
            clicks++;
            //si ya son dos clicks
            if(clicks==2){
                //se obtienen los dos botones presionados
                JButton btn1 = btnPressed.pop();
                JButton btn2 = btnPressed.pop();
                //si no son pares entonces
                if(!isPair()){
                    JOptionPane.showMessageDialog(this, "Continua");
                    //regresa el icono por default a los dos botones
                    btn1.setIcon(new ImageIcon(this.getClass().getResource("/com/peternerd/ui/images/poke.png")));
                    btn2.setIcon(new ImageIcon(this.getClass().getResource("/com/peternerd/ui/images/poke.png")));
                }
                else{
                    //si son pares los botones se deshabilitan ambos botones
                    btn1.setEnabled(false);
                    btn2.setEnabled(false); 
                }
                //reinicia el numero de clicks a 0
                clicks=0;
            }
            break;
        }
      }
    }
    
    /**
     * Comprueba si las dos imagenes son iguales
     * @return true si son pares, false si son diferentes
     */
    public boolean isPair(){
        //saca las dos imagenes de la pila de imagenes
        Image img1 = pares.pop();
        Image img2 = pares.pop();
        //si la ruta de la imagen1 es igual a la ruta de la segunda imagen entonces regresa true
        if(img1.getPath().equals(img2.getPath())){
            return true;
        }
        else{
            return false;
        }
    }
}
