/*
 * 
 */
package Paquete_para_paneles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.border.EmptyBorder;

public class Clase_para_graficar_barras extends JPanel implements MouseListener,MouseMotionListener
{
    public Line2D l1;
    public Rectangle2D r1;//rectangulo superior-izquierdo
    public Path2D figura;
    public int posicionMouseX=0;
    public int posicionMouseY=0;
    public int ancho;
    public int alto;
    public int ubicacionX;//variable "X" para ubicar la clase JPANEL sobre fondo
    public int ubicacionY;//variable "Y" para ubicar la clase JPANEL sobre fondo
    public int division;
    public Color cambio;
    public Color cambio2=new Color(249,48,5);
    public Color cambio3= Color.BLUE;
    public double[] arregloEjeX;//arreglo que contiene valores para el EJE "X"
    public double[] arregloEjeY;//arreglo que contiene valores para el EJE "Y"
    public double maximoValorY;
    public float[] posicionValorX;
    
    private int deltaEjeX;
    private int deltaEjeY;
    private int desfaseX;
    private int desfaseY;
    private float desfaseValorY;
    private float deltaValorY;
    private float posicionY;
    
    private float porcentajeCajaIndicador=(float)0.2;
    private float porcentajeAlturaCajaIndicador=(float)0.4;
    private float porcentajeAnchoPuntaIndicador=(float)0.2;
    private float porcentajeUbicacionDisplayIndicador=(float)0.75;
    
    private BigDecimal redondeo;
    
    Font dimensionEscala;
    
    public Clase_para_graficar_barras(int ancho,int alto,int ubicacionX,int ubicacionY,
            double[] arregloEjeX,double[] arregloEjeY,
            double maximoValorY)
    {
        this.ancho=ancho;
        this.alto=alto;
        this.ubicacionX=ubicacionX;
        this.ubicacionY=ubicacionY;
        this.arregloEjeX=arregloEjeX;
        this.arregloEjeY=arregloEjeY;
        this.maximoValorY=maximoValorY;
             
        division= arregloEjeX.length;
        deltaValorY=(float)(maximoValorY/division);
        deltaEjeX= (int)((ancho-(int)(ancho*1/10)-(int)(ancho*8/100))/division);
        deltaEjeY= (int)((alto-(int)(alto*15/100)-(int)(alto*5/100))/division);
        
        metodo_para_determinar_ubicacion_valoresX_sobre_panel();
        
        setSize(ancho,alto);
        setLayout(null);
        setBackground(new Color(236,238,238));
        setLocation(ubicacionX,ubicacionY);
        addMouseListener(this);
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
        
        //Secuencia para crear las barras que son desplegadas sobre el panel de la
        //grafica.
        for(int j=0;j<division;j++)
        {
            posicionY=(float)(alto-(int)(alto*14/100)-(arregloEjeY[j]*deltaEjeY/deltaValorY));
            float alturaBarraY=(float)((arregloEjeY[j]*deltaEjeY/deltaValorY)-1);
            r1 = new Rectangle2D.Float(posicionValorX[j],posicionY,(float)(deltaEjeX*8/10),alturaBarraY);
            g2.setColor(cambio2);
            g2.fill(r1);
            
             if(posicionMouseX>=(int)(posicionValorX[j]-(deltaEjeX*48/100)) && posicionMouseX<=(int)(posicionValorX[j]+(deltaEjeX*48/100))
                    && posicionMouseY>= (int)posicionY)
            {
                g2.setColor(cambio3);
                g2.fill(r1);
                
                String valorMaximo= Double.toString(arregloEjeY[j]);
                g2.drawString(valorMaximo,posicionValorX[j],posicionY-5);//valores para las divisiones del EJE "Y"
            
                figura=metodo_para_crear_figura();
                g2.setColor(new Color(1,1,1,90));
                g2.fill(figura);
                
                dimensionEscala= new Font("Cochin",Font.CENTER_BASELINE,(int)(ancho*porcentajeCajaIndicador/3));
                g2.setFont(dimensionEscala);
                g2.setColor(Color.YELLOW);
                g2.drawString(valorMaximo,(int)(posicionMouseX-ancho*porcentajeCajaIndicador*porcentajeUbicacionDisplayIndicador),
                        (int)(posicionMouseY+ancho*porcentajeCajaIndicador/7));
            
            }
            
        }
        desfaseX=0;
        desfaseY=0;
        
    }
    
///////////////////////////////////////////////////////////////////////////////    
    private void metodo_para_determinar_ubicacion_valoresX_sobre_panel()
    {
        posicionValorX= new float[division];
        for(int i=0;i<division;i++)
        {
            desfaseX+=deltaEjeX;
            posicionValorX[i]= (float)((ancho*10/100)+desfaseX-(deltaEjeX*4/10));
        }
    }
    
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
        posicionMouseX=e.getX();
        posicionMouseY=e.getY();
        cambio2=new Color(249,48,5,80);
       
        repaint();
    }
    public void mouseDragged(MouseEvent e) {
    }
  
    public void mouseReleased(MouseEvent e) {
        
        cambio2=new Color(249,48,5);
        repaint();
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
    
    
    public void metodo_para_pasar_arreglo(double[] arregloEjeY)
    {
        this.arregloEjeY=arregloEjeY;
        repaint();
    }
    
    
}//fin de clase
