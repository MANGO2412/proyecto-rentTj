package src.clases;
import java.sql.*;

import com.mysql.cj.callback.UsernameCallback;

import src.clases.*;

public class Cliente extends User{
    //conecction wuth database
    DataBase rentaEquipos = new DataBase();
    
    int id;
    String nombre;
    String apell_paterno;
    String numTel;
    //attributes
    public Cliente(int id,String nombre,String apell_paterno,String numTel,String username,String contrasena){
      super(username,contrasena);
      this.id = id;
      this.nombre = nombre;
      this.apell_paterno = apell_paterno;
      this.numTel = numTel;
    }
  
    //method gets
    public String getNombre(){
      return nombre;
    }

    public String getApell(){
      return apell_paterno;
    }
     

    public  void printState(){
        System.out.println(nombre);
    }  

    void createRenta(){

    }

      void showRental(){

      }

      void deleteRental(){

      }
   
}
