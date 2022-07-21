package src.clases;
import java.sql.*;

public class DataBase{
     public String url;
     public String userName;
     public String password;

     //constructor
     public DataBase(){
       url = "jdbc:mysql://localhost:3306/rentaEquipos";
       userName = "root";
       password = "";  
     }
   
     
     //method getConnection
    public Connection getConnection(){
        Connection con = null;
         
        try {
            con = DriverManager.getConnection(url,userName,password);
                       
        } catch (SQLException e) {
           e.printStackTrace();
        }
       
        return con;
    }

      //methood getTable
     public ResultSet getTableResults(String command){
        ResultSet resultSet = null;

        try {
            Connection con=getConnection();
            Statement st = con.createStatement();
             resultSet = st.executeQuery(command);

        } catch (SQLException e) {
            System.out.println(e);
        }
       
        return resultSet;
       
    }
 
}
