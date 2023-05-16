package com.mycompany.programaciondb;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Metodos {

    UI ui;

    public Metodos(UI ui ) {
        this.ui=ui;
    }

    String url="jdbc:sqlite:src/main/java/BD/PruebaPROG.db";
    Connection connect;


    public void conectarBD(){
        try {
            connect = DriverManager.getConnection(url);
            if(connect!=null){
                JOptionPane.showMessageDialog(null, "Conexion exitosa");
            }
            actualizarTabla();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public void insertarElemento(){
        int codigo;
        String nombre;
        int nota;

        codigo = Integer.parseInt(JOptionPane.showInputDialog("Introduzca un nuevo codigo:"));
        nombre = JOptionPane.showInputDialog("Introduzca el nombre del alumno:");
        nota = Integer.parseInt(JOptionPane.showInputDialog("Introduzca la nota media del alumno:"));
        try{
            PreparedStatement st = connect.prepareStatement("insert into alumnos values('"+codigo+"','"+nombre+"','"+nota+"')");
            st.execute();
            JOptionPane.showMessageDialog(null, "Valores insertados!");
            actualizarTabla();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public void buscarElemento(){
        int codigo;
        ResultSet resultado = null;

        codigo = Integer.parseInt(JOptionPane.showInputDialog("Introduzca el código del item que quieras ver:"));
        try{
            PreparedStatement st = connect.prepareStatement("select * from alumnos where codigo = '"+codigo+"'");
            resultado = st.executeQuery();
            JOptionPane.showMessageDialog(null, "Codigo:" + resultado.getInt("codigo") + " Nombre:" + resultado.getString("nombre") + " Nota media:" + resultado.getInt("nota") );
            actualizarTabla();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public void borrarElemento(){
        int codigo;

        codigo = Integer.parseInt(JOptionPane.showInputDialog("Introduzca el número de lo que quiera eliminar"));
        try{
            PreparedStatement st = connect.prepareStatement("delete from alumnos where codigo = '"+codigo+"'");
            st.execute();
            JOptionPane.showMessageDialog(null, "Valores borrados");
            actualizarTabla();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public void cambiarDatos(){
        int codigo;
        String nombre;
        int nota;

        codigo = Integer.parseInt(JOptionPane.showInputDialog("Introduzca el número de lo que quiera modificar"));
        nombre = JOptionPane.showInputDialog("Introduzca el nuevo valor de texto");
        nota = Integer.parseInt(JOptionPane.showInputDialog("Introduzca la nota media del alumno (Solo números enteros)"));

        try{
            PreparedStatement st = connect.prepareStatement("update alumnos set nombre = '"+nombre+"', nota = '"+nota+"' where codigo = '"+codigo+"'");
            st.execute();
            JOptionPane.showMessageDialog(null, "Valores modificados");
            actualizarTabla();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public void actualizarTabla(){
        ui.model.setRowCount(0);
        ResultSet resultado = null;

        try{
            PreparedStatement st = connect.prepareStatement("select codigo,nombre,nota from alumnos");
            resultado = st.executeQuery();

            while(resultado.next()){
                ui.model.addRow(new Object[]{resultado.getInt("codigo"),resultado.getString("nombre"),resultado.getInt("nota")});
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public void cerrarBD(){
        try{
            connect.close();
            ui.dispose();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

}
