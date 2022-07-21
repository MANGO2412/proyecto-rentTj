package src.clases;
import java.sql.*;


//class database
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
   
     
     //method getConnection for the programer  can have to connection with database 
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


    public void updateData(String command){
       try {
          Connection con = getConnection();
          Statement statement = con.createStatement();
          statement.executeUpdate(command);
         
          con.close();
          statement.close();
       } catch (SQLException e) {
          System.out.println(e);
       }         
    }
 
}
