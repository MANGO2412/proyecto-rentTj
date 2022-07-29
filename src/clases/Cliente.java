package src.clases;
import java.util.*;
import java.io.Console;
import java.sql.*;
//import com.mysql.cj.callback.UsernameCallback;
import javax.swing.JOptionPane;

public class Cliente extends User{
    //conecction wuth database
    Calendar calendar = Calendar.getInstance();
    Scanner in = new Scanner(System.in);
    DataBase rentaEquipos = new DataBase();
    Console console = System.console();
    Catalogo catalogo = new Catalogo();
    console con = new console();

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

    public Cliente(String tipoCustomer){
      super(tipoCustomer);
    }
  
  
    //method gets
    public String getNombre(){
      return nombre;
    }

    public String getApell(){
      return apell_paterno;
    }
     
   public String getNumTel(){
    return numTel;
   }

   //other methods 
   void clear(){
     System.out.print("\033[H\033[2J");  
     System.out.flush(); 
   }
    

    // String  getEntrDate(String date){
  
    //   return date;
    // }

    public void createRenta(){
      catalogo.printEquipments();

      System.out.println("¿Quieres realizar una renta?(si/no)");String answer = console.readLine();
      do {
       if(answer.equals("si")){
          System.out.println("¿por cuantos dias vas a rentar los equipos?");
          int dayAmount = in.nextInt();

          String fecha = console.readLine("Dime la fecha de entrage en este formato(dd:mm:yy)");
          
         
                 

         
       }else if(answer.equals("no")){
         break;
       }else{
          System.out.println("tu respuesta es confusa >:(, vuelve escribir (si/no)");
          answer = console.readLine(); 
       }
     } while (true);
   
    }




    
    void showRental(){
    
    }

    void deleteRental(){
       
    }


      //later
    public void printInformation(){
        System.out.println("+---------informacion de la cuenta----------+");
        System.out.println("|>Nombre de usuario:"+super.getuserName().trim()+". ");
        System.out.println("|>Nombre:"+getNombre().trim()+",                    ");
        System.out.println("|>Apellido:"+getApell().trim()+",                   ");
        System.out.println("|>Numero de telefono:"+ getNumTel().trim()+".");
        System.out.println("+--------------------------------------------+");       
    }

    void changeInformation(){

    }

  //methods to register new customer
    public int id_cliente() {

      int id_cliente = 0;
      try {
       String  com = "SELECT ID_Cliente from clientes ORDER BY ID_Cliente ASC";
       ResultSet resultSet = rentaEquipos.getTableResults(com);
       
       while (resultSet.next()) {
          if(resultSet.getInt("ID_Cliente") > id_cliente){
            id_cliente = resultSet.getInt("ID_Cliente");
          }     
        }
      } catch (Exception e) {
        // TODO: handle exception
        System.out.println(e);
      }
     
      id_cliente = id_cliente+1;
      return id_cliente;
    }

  public void register(){
      System.out.println("Escribe tu nombre");String  nombreNew = in.next();

      System.out.println("Escribe tu apellido paterno");String apell_paternoNew = in.next();

      System.out.println("Escribe tu telefono");
      String numTelNew = validateNumTel();
      
      System.out.println("Escribe un nombre de usuario");
      String usernameNew = validateUserName();
      
      System.out.println("Escribe una contrasena de 12 caracteres");
      String contrasenaNew = validatePassword();

      int idCustomerNew = id_cliente();
      int idUserNew = super.getLastIdUser();
      //command mysql
      String comand1 = "insert into clientes values("+idCustomerNew+",'"+nombreNew+"','"+apell_paternoNew+"',"+numTelNew+")";
      super.register(comand1);

      String command2 = "insert into users(id_user,tipo,username,password,cliente) values("+idUserNew+",'"+getTipo()+"','"+usernameNew+"','"+contrasenaNew+"','"+idCustomerNew+"')";
      super.register(command2);


      JOptionPane.showMessageDialog(null, "tu registro fue exitoso, inicia sesion para usra nuestro servicio,recuerda: \n\n >nombre de usuario: "+usernameNew+"\n\n >contraseña: "+contrasenaNew);
  }     
     
}
