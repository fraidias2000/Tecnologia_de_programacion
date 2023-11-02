package javaapplication3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class CalcMVC extends JFrame {
    private static final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
    private JTextField display;
    private boolean   empezarNumero = true;
    private String    opPrevio  = "=";
    private CalcModel modelo = new CalcModel();
    
    public static void main(String[] args) {
        CalcMVC ventana = new CalcMVC();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
    public CalcMVC() {
        display = new JTextField("0", 12);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(BIGGER_FONT);
        
        JButton botonBorrar = new JButton("Clear");
        botonBorrar.setFont(BIGGER_FONT);
        botonBorrar.addActionListener(new ClearListener());

        ActionListener oyenteNumeros = new OyenteNumeros();
        String numeros = "789456123 0 ";
        JPanel panelNumeros = new JPanel();
        panelNumeros.setLayout(new GridLayout(5, 3, 2, 2));
        for (int i = 0; i < numeros.length(); i++) {
            String numero = numeros.substring(i, i+1);
            JButton b = new JButton(numero);
            if (numero.equals(" ")) {
                b.setEnabled(false);
            } else {
                b.addActionListener(oyenteNumeros);
                b.setFont(BIGGER_FONT);
            }
            panelNumeros.add(b);
        }
        ActionListener oyenteOper = new OyenteOper();       
        JPanel panelOper = new JPanel();
        panelOper.setLayout(new GridLayout(5, 1, 2, 2));
        String[] operadores = {"+", "-", "*", "/", "="};
        for (int i = 0; i < operadores.length; i++) {
            JButton b = new JButton(operadores[i]);
            b.addActionListener(oyenteOper);
            b.setFont(BIGGER_FONT);
            panelOper.add(b);
        }        
        JPanel panelBorrar = new JPanel();
        panelBorrar.setLayout(new FlowLayout());
        panelBorrar.add(botonBorrar);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(5, 5));
        content.add(display, BorderLayout.NORTH );
        content.add(panelNumeros, BorderLayout.CENTER);
        content.add(panelOper, BorderLayout.EAST  );
        content.add(panelBorrar, BorderLayout.SOUTH );
        
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        this.setContentPane(content);
        this.pack();
        this.setTitle("Calc MVC");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    private void borrar() {
        empezarNumero = true; 
        display.setText("0");
        opPrevio  = "=";
        modelo.setTotal("0");
    }
    class OyenteOper implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (empezarNumero) {
                borrar();
                display.setText("Error - No operador");
            } else {
                empezarNumero = true;
                try {
                  String displayTexto = display.getText();                    
                  if (opPrevio.equals("=")) {
                    modelo.setTotal(displayTexto);
                  } else if (opPrevio.equals("+")) {
                    modelo.sumar(displayTexto);
                  } else if (opPrevio.equals("-")) {
                    modelo.restar(displayTexto);
                  } else if (opPrevio.equals("*")) {
                    modelo.multiplicar(displayTexto);
                  } else if (opPrevio.equals("/")) {
                    modelo.dividir(displayTexto);
                  }
                  display.setText("" + modelo.getTotalString());
               } catch (NumberFormatException ex) {
                    borrar();
                    display.setText("Error desbordamiento");
               } catch (ArithmeticException ex) {
                    borrar();
                    display.setText("Error - División por 0");
               } 
               opPrevio = e.getActionCommand();
            }
        }
    }
    class OyenteNumeros implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String digito = e.getActionCommand();
            if (empezarNumero) {
                display.setText(digito);
                empezarNumero = false;
            } else {
                display.setText(display.getText() + digito);
            }
        }
    }
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { borrar(); }
    }
}
class CalcModel {
    private int total;
    public CalcModel() { total = 0; }    
    public String getTotalString() { return "" + total; }    
    public void setTotal(String n) { total = convertirANumero(n); }
    public void sumar(String n) { total += convertirANumero(n); }
    public void restar(String n) { total -= convertirANumero(n); }
    public void multiplicar(String n) { total *= convertirANumero(n); }    
    public void dividir(String n) { 
	  if (n.equals("0")) throw new ArithmeticException();
	  total /= convertirANumero(n);   
	}    
    private int convertirANumero(String n) { return Integer.parseInt(n);  }
}
