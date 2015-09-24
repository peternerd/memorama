/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peternerd;

/**
 *
 * @author Pedro
 */
public class Memorama {
    private Image images[];
    
    public Memorama(String paths[]){
        //Obtiene el numero de imagenes
        int numberImages = paths.length;
        //crea un array de doble tamano de imagenes
        images = new Image[numberImages*2];
        //variable para guardar un numero aleatorio
        int aleatorio = 0;
        //se crea un objeto r de la Clase Random para generar numeros aleatorios
        java.util.Random r = new java.util.Random();
        //se itera sobre el numero de imagenes
        for(int j=0;j<numberImages;j++){
            //i es un contador
            int i=0;
            //el ciclo while es para insertar una imagen en dos posiciones, de ahi que sea mientras i sea menor a 2
            while(i<2){
                //se genera el numero pseudoaleatorio, desde 0 hasta el numero de imagenes totales(incluyendo los pares)
                aleatorio = r.nextInt(numberImages*2);
                //si el array en la posicion aleatoria no esta ocupada entonces...
                if(images[aleatorio] == null){
                    //se crea un objeto Image en esa posicion 
                    setImagePosition(aleatorio,new Image(paths[j]));
                    //se aumenta el contador
                    i++;
                }
            }
        }
    }
    
    /**
     * Inserta una imagen en la posicion i del memorama
     * @param i la posicion en la cual se inserta la imagen
     * @param image el objeto imagen a insertar
     */
    private void setImagePosition(int i,Image image){
        this.images[i] =image;
    }
    
    /**
     * Obtiene una imagen
     * @param position indica cual imagen obtener segun su posicion
     * @return un objeto Image
     */
    public Image getImage(int position){
        return this.images[position];
    }
    
    /**
     * Obtiene todas las imagenes del memorama
     * @return un array de objetos Image
     */
    public Image[] getAllImages(){
        return this.images;
    }
    
}
