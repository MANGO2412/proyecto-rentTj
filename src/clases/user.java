package src.clases;
import src.clases.DataBase;


public class user {
   DataBase rentaEquipos = new DataBase();

   //attributes
   int id;
   String userName;
   String password;
   String tipo;
   
   user(String userName,String password){
        this.userName = userName;
        this.password = password;
   }


   void log(){
      
   }




}
