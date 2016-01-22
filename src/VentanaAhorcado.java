
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jotivirix
 */
public class VentanaAhorcado extends javax.swing.JFrame {

//TAREAS A REALIZAR
/*
    1- Elegir la palabra oculta de un array de Strings aleatoriamente
    2- Que acabe el juego. HAS GANADO o HAS PERDIDO
    3- Que se pinte de forma automática los guiones en función del nº de letras
        que tenga la palabra oculta seleccionada.
     */
    String[] palabrasOcultas = new String[10];

    String palabraOculta = "CETYS";

    //Contador numero de fallos
    int numeroFallos = 0;

    //Contador maximo de fallos
    int maximosFallos = 6;

    //Partida terminada
    boolean partidaTerminada = false;

    /**
     * Creates new form VentanaAhorcado
     */
    public VentanaAhorcado() {
        initComponents();
        //Aquí va el codigo que metíamos en el run de ACM
        //Inicializamos las palabras del Array
        palabrasOcultas[0] = "CETYS";
        palabrasOcultas[1] = "DAM";
        palabrasOcultas[2] = "UFV";
        palabrasOcultas[3] = "JAVA";
        palabrasOcultas[4] = "MYSQL";
        palabrasOcultas[5] = "TECLA";
        palabrasOcultas[6] = "GOOGLE";
        palabrasOcultas[7] = "ECLIPSE";
        palabrasOcultas[8] = "RUN";
        palabrasOcultas[9] = "PC";
        //Hacemos un random para que elija la palabra al azar
        Random palabra = new Random();
        //Establecemos esa palabra como palabra oculta
        palabraOculta = palabrasOcultas[palabra.nextInt(10)];
        //Adaptamos la palabra con sus respectivos guiones
        adaptaPalabra();
    }

    /*
    Método que adapta la palabra con sus guiones
    en función del tamaño de la misma.
    Lo primero que hace es pasar el texto a una
    cadena vacía.
    Una vez vacía, recorre el array de palabras
    definidas y le añade un guión a cada letra
     */
    private void adaptaPalabra() {
        jLabel1.setText("");
        for (int i = 0; i < palabraOculta.length(); i++) {
            jLabel1.setText(jLabel1.getText() + "_ ");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //Indicamos que vamos a dibujar en el panel
        g = jPanel1.getGraphics();
        Image imagen = null;
        try {
            //Cargamos las imágenes
            /*
            En función del momento del juego en el que estemos
            se nos cargará una imagen u otra en el panel central
            En caso de perder saldrá el muñeco ahorcado, y en
            caso de ganar saldrá la imagen de la victoria.
            */
            switch (numeroFallos) {
                case 0:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_0.png"));
                    break;
                case 1:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_1.png"));
                    break;
                case 2:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_2.png"));
                    break;
                case 3:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_3.png"));
                    break;
                case 4:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_4.png"));
                    break;
                case 5:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_5.png"));
                    break;
                case -100:
                    imagen = ImageIO.read(getClass().getResource("/acertasteTodo.png"));
                    break;
                default:
                    imagen = ImageIO.read(getClass().getResource("/ahorcado_fin.png"));
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(VentanaAhorcado.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Dibujamos la imagen añadida en el jPanel
        g.drawImage(imagen, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), null);
    }

    private void chequeaLetra(JButton boton) {
        //Mientras no haya acabado la partida ejecutamos todo el if()
        if (partidaTerminada == false) {
            //Si el botón está activo, al pulsarlo identificará la letra
            if (boton.isEnabled()) {
                //Declaramos que la letra sea la del botón pulsado
                String letra = boton.getText();
                //Desactivamos el botón pulsado
                boton.setEnabled(false);
                //Le decimos que la palabra secreta sea almacenada
                String palabraSecreta = jLabel1.getText();
                /*
                
            Si la palabra oculta contiene alguna letra elegida,
            comprobamos la posición de dicha letra, y una vez
            sabida la posición de la letra la colocamos en la
            parte superior asignada a tal efecto.
            Si no, está esa letra, las iremos añadiendo abajo
            en la sección habilitada al efecto de recordarnos
            las letras ya utilizadas hasta el momento actual
                 */
                if (palabraOculta.contains(letra)) {
                    for (int i = 0; i < palabraOculta.length(); i++) {
                        if (palabraOculta.charAt(i) == letra.charAt(0)) {
                            palabraSecreta = palabraSecreta.substring(0, 2 * i)
                                    + letra
                                    + palabraSecreta.substring(2 * i + 1);
                        }
                    }
                    jLabel1.setText(palabraSecreta);

                    //Comprobamos si hay guiones o no en la palabra oculta
                    //En caso de haber guiones no hacemos nada. No habríamos
                    //ganado aún la partida. En caso de no haber guiones,
                    //habríamos ganado la partida y debemos indicarlo de 
                    //alguna manera
                    if (!palabraSecreta.contains("_")) {
                        numeroFallos = -100;      //Este es el caso que carga la imagen de ganador
                        resultadoJuego.setText("HA GANADO. ENHORABUENA");
                        if (numeroFallos == 0) {
                            resultadoFallos.setText("NINGÚN FALLO");
                        }
                        if (numeroFallos == 1) {
                            resultadoFallos.setText("Ha acumulado: " + numeroFallos + " fallo");
                        } else if (numeroFallos >= 2 && numeroFallos < 6) {
                            resultadoFallos.setText("Ha acumulado: " + numeroFallos + " fallos");
                        }

                        partidaTerminada = true;  //La partida habría terminado
                    }
                    //Si no, la partida seguiría en activo
                    /* En caso de seguir la partida en activo,
                    establecemos que letras hemos fallado en
                    la parte establecida para ello e incrementamos
                    en uno el número de fallos. Si la letra está en
                    la palabra la pondremos en su sitio.
                    Conforme vayamos fallando nos irá diciendo
                    cuantos fallos llevamos.
                     */
                } else {
                    letrasPulsadas.setText(letrasPulsadas.getText() + letra + " ");
                    numeroFallos++;
                    if (numeroFallos == 1) {
                        resultadoFallos.setText(String.valueOf("Lleva: "
                                + numeroFallos + " fallo"));
                    } else {
                        resultadoFallos.setText(String.valueOf("Lleva: "
                                + numeroFallos + " fallos"));
                    }
                }
                //Iremos cambiando la imagen en caso de ir fallando
                repaint();

                //Si alcanzamos el final establecemos las letras usadas
                //Indicamos abajo que ha terminado la partida
                //Establecemos la partida como terminada
                if (numeroFallos == maximosFallos) {
                    resultadoFallos.setText(numeroFallos + " fallos");
                    resultadoJuego.setText("AHORCADO! HA PERDIDO");
                    partidaTerminada = true;
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        letrasUtilizadas = new javax.swing.JLabel();
        resultadoJuego = new javax.swing.JLabel();
        resultadoFallos = new javax.swing.JLabel();
        letrasPulsadas = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(365, 680));
        setPreferredSize(new java.awt.Dimension(365, 680));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Monaco", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("_ _ _ _ _");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 351, 45));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 69, -1, -1));

        jButton1.setText("A");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 285, 45, 45));

        jButton2.setText("B");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 285, 45, 45));

        jButton3.setText("C");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 285, 45, 45));

        jButton4.setText("D");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 285, 45, 45));

        jButton5.setText("E");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton5MousePressed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 285, 45, 45));

        jButton6.setText("F");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 285, 45, 45));

        jButton7.setText("G");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton7MousePressed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 285, 45, 45));

        jButton8.setText("H");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton8MousePressed(evt);
            }
        });
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 336, 45, 45));

        jButton9.setText("I");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton9MousePressed(evt);
            }
        });
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 336, 45, 45));

        jButton10.setText("J");
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton10MousePressed(evt);
            }
        });
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 336, 45, 45));

        jButton11.setText("K");
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton11MousePressed(evt);
            }
        });
        getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 336, 45, 45));

        jButton12.setText("L");
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton12MousePressed(evt);
            }
        });
        getContentPane().add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 336, 45, 45));

        jButton13.setText("M");
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton13MousePressed(evt);
            }
        });
        getContentPane().add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 336, 45, 45));

        jButton14.setText("N");
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton14MousePressed(evt);
            }
        });
        getContentPane().add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 336, 45, 45));

        jButton15.setText("Ñ");
        jButton15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton15MousePressed(evt);
            }
        });
        getContentPane().add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 387, 45, 45));

        jButton16.setText("O");
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton16MousePressed(evt);
            }
        });
        getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 387, 45, 45));

        jButton17.setText("P");
        jButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton17MousePressed(evt);
            }
        });
        getContentPane().add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 387, 45, 45));

        jButton18.setText("Q");
        jButton18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton18MousePressed(evt);
            }
        });
        getContentPane().add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 387, 45, 45));

        jButton19.setText("R");
        jButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton19MousePressed(evt);
            }
        });
        getContentPane().add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 387, 45, 45));

        jButton20.setText("S");
        jButton20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton20MousePressed(evt);
            }
        });
        getContentPane().add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 387, 45, 45));

        jButton21.setText("T");
        jButton21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton21MousePressed(evt);
            }
        });
        getContentPane().add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 387, 45, 45));

        jButton22.setText("U");
        jButton22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton22MousePressed(evt);
            }
        });
        getContentPane().add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 438, 45, 45));

        jButton23.setText("V");
        jButton23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton23MousePressed(evt);
            }
        });
        getContentPane().add(jButton23, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 438, 45, 45));

        jButton24.setText("W");
        jButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton24MousePressed(evt);
            }
        });
        getContentPane().add(jButton24, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 438, 45, 45));

        jButton25.setText("X");
        jButton25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton25MousePressed(evt);
            }
        });
        getContentPane().add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 438, 45, 45));

        jButton26.setText("Y");
        jButton26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton26MousePressed(evt);
            }
        });
        getContentPane().add(jButton26, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 438, 45, 45));

        jButton27.setText("Z");
        jButton27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton27MousePressed(evt);
            }
        });
        getContentPane().add(jButton27, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 438, 45, 45));

        letrasUtilizadas.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        letrasUtilizadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        letrasUtilizadas.setText("LETRAS UTILIZADAS");
        getContentPane().add(letrasUtilizadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 495, 350, -1));

        resultadoJuego.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        resultadoJuego.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultadoJuego.setPreferredSize(new java.awt.Dimension(350, 30));
        getContentPane().add(resultadoJuego, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 360, 30));

        resultadoFallos.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        resultadoFallos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(resultadoFallos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 360, 30));

        letrasPulsadas.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        letrasPulsadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(letrasPulsadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 360, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton5MousePressed

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton6MousePressed

    private void jButton7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton7MousePressed

    private void jButton8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton8MousePressed

    private void jButton9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton9MousePressed

    private void jButton10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton10MousePressed

    private void jButton11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton11MousePressed

    private void jButton12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton12MousePressed

    private void jButton13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton13MousePressed

    private void jButton14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton14MousePressed

    private void jButton15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton15MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton15MousePressed

    private void jButton16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton16MousePressed

    private void jButton17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton17MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton17MousePressed

    private void jButton18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton18MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton18MousePressed

    private void jButton19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton19MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton19MousePressed

    private void jButton20MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton20MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton20MousePressed

    private void jButton21MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton21MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton21MousePressed

    private void jButton22MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton22MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton22MousePressed

    private void jButton23MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton23MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton23MousePressed

    private void jButton24MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton24MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton24MousePressed

    private void jButton25MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton25MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton25MousePressed

    private void jButton26MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton26MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton26MousePressed

    private void jButton27MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton27MousePressed
        chequeaLetra((JButton) evt.getSource());
        letrasPulsadas.setText(letrasPulsadas.getText());
    }//GEN-LAST:event_jButton27MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaAhorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaAhorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaAhorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaAhorcado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaAhorcado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel letrasPulsadas;
    private javax.swing.JLabel letrasUtilizadas;
    private javax.swing.JLabel resultadoFallos;
    private javax.swing.JLabel resultadoJuego;
    // End of variables declaration//GEN-END:variables
}
