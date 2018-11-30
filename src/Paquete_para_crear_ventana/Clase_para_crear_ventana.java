/*
 * Clase encargada de crear JFrame para colocar los paneles
 */
package Paquete_para_crear_ventana;

import Paquete_para_paneles.Clase_para_graficar_barras;
import Paquete_para_paneles.Clase_para_graficar_puntos;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clase_para_crear_ventana extends JFrame 
{
    public double[] arregloEjeY={1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,11.0,15.0,17.5,6.0,11.0};
    public double[] arregloEjeX={1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0,11.0,12.0,13.0,14.0};//arreglo que contiene valores para el EJE "X"
    public double maximoValorY=17.5;
    public double maximoValorX=13.0;
    public int ancho=400;
    public int alto=400;
    public int ubicacionX=30;
    public int ubicacionY=30;
    
    public JButton boton;
    
    Clase_para_graficar_barras grafica;
    Clase_para_graficar_puntos grafica_puntos;
    
    public Clase_para_crear_ventana()
    {
        super("VENTANA PRINCIPAL");
        setSize(500,500);
        setLayout(null);
        getContentPane().setBackground(new Color(7,246,239,90));
        
        
        grafica= new Clase_para_graficar_barras(ancho,alto,ubicacionX,ubicacionY,
                arregloEjeX,arregloEjeY,maximoValorY);
        getContentPane().add(grafica);
        
        ubicacionX=460;
        ubicacionY=30;
        grafica_puntos= new Clase_para_graficar_puntos(ancho,alto,ubicacionX,ubicacionY,
                arregloEjeX,arregloEjeY,maximoValorY,maximoValorX);
        getContentPane().add(grafica_puntos);
        
        metodo_para_colocar_boton();
        
        setVisible(true);
    }
    
    public void metodo_para_colocar_boton()
    {
        boton= new JButton("Enviar");
        boton.setBounds(20, 440, 80, 20);
        boton.setVisible(true);
        getContentPane().add(boton);
        
        boton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                double[] arreglo={1.0,2.0,5.0,11.0,5.0,6.0,2.0,8.0,9.0,11.0,15.0,17.5,6.0,15.0};
                grafica.metodo_para_pasar_arreglo(arreglo);
            }
        });
    }
    
}//fin de clase 
