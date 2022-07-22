package src;
import java.sql.ResultSet;
import java.util.*;
import src.clases.*;




public class application{
   //metodo 
   
    public static void  main(String[] args){
          DataBase bar2c = new DataBase();
          
          try{
            ResultSet resultSet = bar2c.getTableResults("select *from meseros;");
            int id_mesero;
            String nombre_mesero;

            while (resultSet.next()) {
                id_mesero = resultSet.getInt("id_mesero");
                nombre_mesero = resultSet.getString("nombre_mesero");

                System.out.println(id_mesero + ""+nombre_mesero);
            }
          }catch(Exception e){

          }
    }
}

