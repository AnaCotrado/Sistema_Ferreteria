/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Conexion.Conexion;
import Datos.D_Kardex;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author analu
 */
public class N_Kardex {
    static Conexion mysql = new Conexion();
    static Connection conect = mysql.Conectar();
    private String Sql="";
    
    public DefaultTableModel Lista(String busqueda){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("IdKardex");
        modelo.addColumn("IdProducto");
        modelo.addColumn("IdEmpleado");
        modelo.addColumn("Fecha y Hora");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Tipo");
        
        if(busqueda.equals("Seleccionar")){
            Sql="SELECT * FROM tb_kardexproducto";
        }else{
            Sql="SELECT k.IdKardexProducto, k.IdProducto, k.IdEmpleado, k.FechaHoraKardexProducto, k.CantidadKardexProducto, k.TipoKardexProducto "
                + " FROM tb_kardexproducto as k "
                + " JOIN tb_producto as p ON p.IdProducto = k.IdProducto "
                + " WHERE p.NombreProducto = '" + busqueda + "'";
        }
        
        try{
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(Sql);
            
            while(rs.next()){
                Object registro[]={rs.getString(1),rs.getString(2),
                            rs.getString(3),rs.getString(4),rs.getString(5),
                            rs.getString(6)};
            modelo.addRow(registro);
            }
            return modelo;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }  
    }
    
    public boolean agregar(D_Kardex datos){
        Sql = "INSERT INTO tb_kardexproducto(IdProducto,IdEmpleado,FechaHoraKardexProducto,CantidadKardexProducto,TipoKardexProducto) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement pst = conect.prepareStatement(Sql);
            
            pst.setInt(1,datos.getIdProducto());
            pst.setInt(2, datos.getIdEmpleado());
            pst.setDate(3, datos.getFechaHora());
            pst.setInt(4, datos.getCantidad());
            pst.setBoolean(5, datos.isTipo());
            
            int n = pst.executeUpdate();
            return n!=0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al agregar datos " + e);
            return false;
        }
    }
    
    public boolean editar(D_Kardex datos){
        Sql = "UPDATE tb_kardexproducto SET IdProducto=?, IdEmpleado=?, FechaHoraKardexProducto=?, CantidadKardexProducto=?, TipoKardexProducto=? WHERE IdKardexProducto=?";
        
        try{
            PreparedStatement pst = conect.prepareStatement(Sql);
            
            pst.setInt(1,datos.getIdProducto());
            pst.setInt(2, datos.getIdEmpleado());
            pst.setDate(3, datos.getFechaHora());
            pst.setInt(4, datos.getCantidad());
            pst.setBoolean(5, datos.isTipo());
            pst.setInt(6,datos.getIdKardex());
            
            int n = pst.executeUpdate();
            return n!=0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al editar datos " + e);
            return false;
        } 
    }
    
    public boolean eliminar(D_Kardex datos){
        Sql = "DELETE FROM tb_kardexproducto WHERE IdKardexProducto=?";
        
        try{
            PreparedStatement pst = conect.prepareStatement(Sql);
            pst.setInt(1,datos.getIdKardex());

            int n = pst.executeUpdate();
            return n!=0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al eliminar datos " + e);
            return false;
        } 
    }
    
    public  ArrayList llenarCombo1(){
        ArrayList lista = new  ArrayList();
        Sql ="SELECT * FROM tb_producto";
        try{
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(Sql);
            
            while(rs.next()){
               lista.add(rs.getString("NombreProducto"));
            }       
        }catch(SQLException e){          
            JOptionPane.showMessageDialog(null,e);
        }
        return lista;
    }
}
