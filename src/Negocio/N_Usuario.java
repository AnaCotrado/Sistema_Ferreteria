package Negocio;
import Conexion.Conexion;
import Datos.D_Usuario;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class N_Usuario {
    private Conexion mysql = new Conexion();
    private Connection conect = mysql.Conectar();
    private String Sql="";
    
    public DefaultTableModel Lista(String busqueda){
       DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("IdUsuario");
        modelo.addColumn("Dni");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Login");
        modelo.addColumn("Password");
        modelo.addColumn("Nivel");
        modelo.addColumn("Estado");
        
        if(busqueda.equals("")){
            Sql="SELECT * FROM tb_usuario";
        }else{
            Sql="SELECT * FROM tb_usuario WHERE DNIUsuario LIKE '" + busqueda + "%'";
        }
        
        try{
            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(Sql);
            
            while(rs.next()){
                Object registro[]={rs.getString(1),rs.getString(2),
                           rs.getString(3),rs.getString(4),
                           rs.getString(5),rs.getString(6),
                           rs.getString(7), rs.getString(8)};
            modelo.addRow(registro);
            }
            return modelo;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }             
    }
    
    public boolean agregar(D_Usuario datos){
        Sql = "INSERT INTO tb_usuario (DNIUsuario,NombreUsuario,ApellidosUsuario,LoginUsuario,ContraseñaUsuario,NivelUsuario,EstadoUsuario) "
                + "VALUES(?,?,?,?,?,?,true)";
        
        try{
            PreparedStatement pst = conect.prepareStatement(Sql);
            
            pst.setString(1,datos.getDni());
            pst.setString(2,datos.getNombre());
            pst.setString(3,datos.getApellido());
            pst.setString(4,datos.getLogin());
            pst.setString(5,datos.getPassword());
            pst.setInt(6,datos.getNivelUsuario());
            int n = pst.executeUpdate();
            return n!=0;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al agregar datos " + e);
            return false;
        } 
    }
    
    public boolean editar(D_Usuario datos){
        Sql = "UPDATE tb_usuario SET DNIUsuario=?, NombreUsuario=?, ApellidosUsuario=?, LoginUsuario=?, ContraseñaUsuario=?, NivelUsuario=?, EstadoUsuario=? "
                + " WHERE IdUsuario=?";
        
        try{
            PreparedStatement pst = conect.prepareStatement(Sql);
            
            pst.setString(1,datos.getDni());
            pst.setString(2,datos.getNombre());
            pst.setString(3,datos.getApellido());
            pst.setString(4,datos.getLogin());
            pst.setString(5,datos.getPassword());
            pst.setInt(6,datos.getNivelUsuario());
            pst.setBoolean(7,datos.isEstado());
            
            pst.setInt(8,datos.getIdUsuario());
            int n = pst.executeUpdate();
            return n!=0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al editar datos " + e);
            return false;
        } 
    }
    
    public boolean eliminar(D_Usuario datos){
        Sql = "DELETE FROM tb_usuario WHERE IdUsuario=?";
        
        try{
            PreparedStatement pst = conect.prepareStatement(Sql);
            
            pst.setInt(1,datos.getIdUsuario());

            int n = pst.executeUpdate();
            return n!=0;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al eliminar datos " + e);
            return false;
        } 
    }
    
    public boolean UserLista(String user, String password){
        Sql = "SELECT IdUsuario,NombreUsuario,ApellidosUsuario,NivelUsuario FROM tb_usuario WHERE LoginUsuario='"+ user + "' AND ContraseñaUsuario='"+ password + "' AND EstadoUsuario=true";
             
        try{

            Statement st = conect.createStatement();
            ResultSet rs = st.executeQuery(Sql);
                        
            while(rs.next()){               
                D_Usuario.LIdUsuario = rs.getInt(1);
                D_Usuario.Lnombre = rs.getString(2);
                D_Usuario.Lapellido = rs.getString(3); 
                D_Usuario.LnivelUsuario = Integer.parseInt(rs.getString(4));
                return true;
            }            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        return false;
    }
}
