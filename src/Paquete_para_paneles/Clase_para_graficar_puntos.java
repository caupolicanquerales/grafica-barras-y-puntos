/*
    Clase encargada de graficar puntos sobre el panel y de unir los puntos por
    rectas.
 */
package Paquete_para_paneles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JPanel;


public class Clase_para_graficar_puntos extends JPanel implements MouseMotionListener
{
    public Line2D l1;
    public Ellipse2D e1;
    public Path2D figura;
    public int ancho;
    public int alto;
    public int ubicacionX;//variable "X" para ubicar la clase JPANEL sobre fondo
    public int ubicacionY;//variable "Y" para ubicar la clase JPANEL sobre fondo
    public double[] arregloEjeX;//arreglo que contiene valores para el EJE "X"
    public double[] arregloEjeY;//arreglo que contiene valores para el EJE "Y"
    public double maximoValorY;
    public double maximoValorX;
    public int division;
    public int posicionMouseX=0;
    public int posicionMouseY=0;
    
    private int deltaEjeX;
    private int deltaEjeY;
    private int desfaseX;
    private int desfaseY;
    private float desfaseValorY;
    private float deltaValorY;
    private float deltaValorX;
    private float[] posicionX;
    private float[] posicionY;
    
    private float porcentajeCajaIndicador=(float)0.2;
    private float porcentajeAlturaCajaIndicador=(float)0.55;
    private float porcentajeAnchoPuntaIndicador=(float)0.2;
    private float porcentajeUbicacionDisplayIndicador=(float)0.95;
    
    private BigDecimal redondeo;
    Font dimensionEscala;
    
    public Clase_para_graficar_puntos(int ancho,int alto,int ubicacionX,int ubicacionY,
            double[] arregloEjeX,double[] arregloEjeY,
            double maximoValorY,double maximoValorX)
    {
        this.ancho=ancho;
        this.alto=alto;
        this.ubicacionX=ubicacionX;
        this.ubicacionY=ubicacionY;
        this.arregloEjeX=arregloEjeX;
        this.arregloEjeY=arregloEjeY;
        this.maximoValorY=maximoValorY;
        this.maximoValorX=maximoValorX;
        
        division= arregloEjeX.length;
        posicionX= new float[division];
        posicionY= new float[division];
        deltaValorY=(float)(maximoValorY/division);
        deltaValorX=(float)(maximoValorX/division);
        deltaEjeX= (int)((ancho-(int)(ancho*1/10)-(int)(ancho*8/100))/division);
        deltaEjeY= (int)((alto-(int)(alto*15/100)-(int)(alto*5/100))/division);
        
        setSize(ancho,alto);
        setLayout(null);
        setBackground(new Color(236,238,238));
        setLocation(ubicacionX,ubicacionY);
        addMouseMotionListener(this);
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D) g;
        
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      
         //linea del EJE de las "Y"
        l1= new Line2D.Double((int)(ancho*10/100),(int)(alto*5/100),(int)(ancho*10/100),alto-(int)(alto*15/100)); //eje de las "Y"
        g2.setStroke(new BasicStroke(2,BasicStroke.JOIN_BEVEL,BasicStroke.JOIN_BEVEL));
        g2.setColor(Color.BLACK);
        g2.draw(l1);
        
        //linea del EJE de las "X"
        l1= new Line2D.Double((int)(ancho*10/100),alto-(int)(alto*14/100),ancho-(int)(ancho*8/100),alto-(int)(alto*14/100));//eje de las "X"
        g2.setStroke(new BasicStroke(2,BasicStroke.JOIN_BEVEL,BasicStroke.JOIN_BEVEL));
        g2.setColor(Color.BLACK);
        g2.draw(l1);
        
        desfaseY=0;
        desfaseX=0;
        
        //Secuencia para desplegar las divisiones de los EJES "Y" y "X", asi como
        //los valores de las escalas sobre los ejes.
        for(int i=0;i<division;i++)//Rutina para colocar division "Y" y escala de eje "X"
        {
            //Rutina para colocar rectas de division en el EJE "Y"
            l1= new Line2D.Double((int)(ancho*10/100),alto-(int)(alto*14/100)-desfaseY,ancho-(int)(ancho*8/100),alto-(int)(alto*14/100)-desfaseY);//eje de las "X"
            g2.setStroke(new BasicStroke(1,BasicStroke.JOIN_BEVEL,BasicStroke.JOIN_BEVEL));
            g2.setColor(Color.LIGHT_GRAY);
            g2.draw(l1);
            
            desfaseY+=deltaEjeY;//incrementa los valores de Y a colocar sobre el EJE
            desfaseX+=deltaEjeX;//incrementa los valores de X a colocar sobre el EJE
            desfaseValorY+=deltaValorY;
           
            //secuencia para colocar valores de "Y" en el EJE.
            dimensionEscala= new Font("Cochin",Font.CENTER_BASELINE,(int)(alto*7/200*9/10));
            g2.setFont(dimensionEscala);
            g2.setColor(Color.BLACK);
            redondeo= new BigDecimal(desfaseValorY);//linea para redondear numero Double o Float
            String valorY= redondeo.setScale(1, RoundingMode.HALF_UP).toString();//linea para pasar numero Redondeo a STRING
            g2.drawString(valorY,(int)(ancho*2/100),alto-(int)(alto*14/100)-desfaseY);//valores para las divisiones del EJE "Y" 
        
            //Rutina para colocar rectas de division sobre el EJE de las "X"
            l1= new Line2D.Double((ancho*10/100)+desfaseX,alto-(alto*12/100),(ancho*10/100)+desfaseX,alto-(int)(alto*14/100));//eje de las "X"
            g2.setStroke(new BasicStroke(1,BasicStroke.JOIN_BEVEL,BasicStroke.JOIN_BEVEL));
            g2.setColor(Color.LIGHT_GRAY);
            g2.draw(l1);
            
            //secuencia para colocar valores de X debajo de cada barra.
            dimensionEscala= new Font("Cochin",Font.CENTER_BASELINE,(int)(deltaEjeX*4/10));
            g2.setFont(dimensionEscala);
            g2.setColor(Color.BLACK);
            String valorX= Double.toString(arregloEjeX[i]);
            g2.drawString(valorX,(int)(ancho*10/100)+desfaseX-(int)(deltaEjeX*4/10),alto-(int)(alto*9/100));//valores para las divisiones del EJE "X" 
       
        }
        desfaseY=0;
        desfaseX=0;
        desfaseValorY=0;
        
        //secuencia para determinar posicion de los puntos en X y Y sobre el panel
        //a graficar
        //NOTA: valor del radio del punto "(ancho*18/1000*1/2), valor del diametro del punto (ancho*18/1000)"
        for(int h=0;h<division;h++)
        {
            posicionX[h]=(float)((ancho*10/100)+(arregloEjeX[h]*deltaEjeX/deltaValorX)-(ancho*18/1000*1/2));
            posicionY[h]=(float)(alto-(int)(alto*14/100)-(arregloEjeY[h]*deltaEjeY/deltaValorY)-(ancho*18/1000*1/2));
        }
        //Secuencia para crear las RECTAS que son desplegadas sobre el panel de la
        //grafica.
        for(int j=0;j<division-1;j++)
        {
            l1= new Line2D.Double(posicionX[j]+(ancho*18/1000*1/2),posicionY[j]+(ancho*18/1000*1/2),
                    posicionX[j+1]+(ancho*18/1000*1/2),posicionY[j+1]+(ancho*18/1000*1/2));//eje de las "X"
            g2.setStroke(new BasicStroke(1,BasicStroke.JOIN_BEVEL,BasicStroke.JOIN_BEVEL));
            g2.setColor(Color.BLACK);
            g2.draw(l1);
        }
        //Secuencia para crear los puntos que son desplegadas sobre el panel de la
        //grafica.
        for(int j=0;j<division;j++)
        {
            e1 = new Ellipse2D.Float(posicionX[j],posicionY[j],(int)(ancho*18/1000),(int)(ancho*18/1000));//circulo externo mayor
            g2.setColor(Color.BLUE);
            g2.fill(e1);
            
             if(posicionMouseX>=(int)(posicionX[j]) && posicionMouseX<=(int)(posicionX[j]+(ancho*18/1000))
                    && posicionMouseY>=(int)posicionY[j] && posicionMouseY<=(int)(posicionY[j]+(ancho*18/1000)))
            {
                g2.setColor(Color.RED);
                g2.fill(e1);
               
             
                figura=metodo_para_crear_figura();
                g2.setColor(new Color(1,1,1,90));
                g2.fill(figura);
                
                dimensionEscala= new Font("Cochin",Font.CENTER_BASELINE,(int)(ancho*porcentajeCajaIndicador/6));
                g2.setFont(dimensionEscala);
                g2.setColor(Color.YELLOW);
                redondeo= new BigDecimal(arregloEjeX[j]);//linea para redondear numero Double o Float
                String valor= redondeo.setScale(1, RoundingMode.HALF_UP).toString();//linea para pasar numero Redondeo a STRING
                g2.drawString("X= "+valor,(int)(posicionMouseX-ancho*porcentajeCajaIndicador*porcentajeUbicacionDisplayIndicador),
                        (int)(posicionMouseY-ancho*porcentajeCajaIndicador/20));
                
                redondeo= new BigDecimal(arregloEjeY[j]);//linea para redondear numero Double o Float
                valor= redondeo.setScale(1, RoundingMode.HALF_UP).toString();//linea para pasar numero Redondeo a STRING
                g2.drawString("Y= "+valor,(int)(posicionMouseX-ancho*porcentajeCajaIndicador*porcentajeUbicacionDisplayIndicador),
                        (int)(posicionMouseY+ancho*porcentajeCajaIndicador/5)); 
            }
        }
        
        
    }//fin de paintComponent
    
    private Path2D metodo_para_crear_figura()
    {
        Path2D path= new Path2D.Double();
        path.reset();
        path.moveTo(posicionMouseX,posicionMouseY);
        path.lineTo((posicionMouseX-(ancho*porcentajeCajaIndicador*porcentajeAnchoPuntaIndicador)),
                posicionMouseY-(ancho*porcentajeCajaIndicador*porcentajeAlturaCajaIndicador/2));
        path.lineTo(posicionMouseX-(ancho*porcentajeCajaIndicador),
                posicionMouseY-(ancho*porcentajeCajaIndicador*porcentajeAlturaCajaIndicador/2));
        path.lineTo(posicionMouseX-(ancho*porcentajeCajaIndicador),
                posicionMouseY+(ancho*porcentajeCajaIndicador*porcentajeAlturaCajaIndicador/2));
        path.lineTo(posicionMouseX-(ancho*porcentajeCajaIndicador*porcentajeAnchoPuntaIndicador),
                posicionMouseY+(ancho*porcentajeCajaIndicador*porcentajeAlturaCajaIndicador/2));
        path.lineTo(posicionMouseX,posicionMouseY);
        path.closePath();
        
        return path;
    } 
///////////////////////////////////////////////////////////////////////////////    
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseDragged(MouseEvent e) {
    }
  
    public void mouseReleased(MouseEvent e) {
       
    }
    
    public void mouseMoved(MouseEvent e) 
    {
        posicionMouseX=e.getX();
        posicionMouseY=e.getY();
        repaint();
    }
    
    public void mouseClicked(MouseEvent e) 
    {
    }
    
    public void mouseExited(MouseEvent e) 
    {
       
    }

    public void mouseEntered(MouseEvent e) 
    {
        
    }
    
}//fin de la clase
