package src.clases;
import java.util.*;
import java.io.Console;
import java.sql.*;


import javax.swing.JFrame;
//import com.mysql.cj.callback.UsernameCallback;
import javax.swing.JOptionPane;

public class Cliente extends User{
    //conecction wuth database
   
    JFrame jFrame =new JFrame();
    Calendar calendar = Calendar.getInstance();
    Scanner in = new Scanner(System.in);
    DataBase rentaEquipos = new DataBase();
    Console console = System.console();
    Catalogo catalogo;
    console con = new console();

    int id;
    String nombre;
    String apell_paterno;
    String numTel;
    String date;
    
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

   public int getId(){
    return id;
   }

   
    

    // String  getEntrDate(String date){
  
    //   return date;
    // }

    String validatOptionSINO(){
      boolean confirm;
      String answer;

      do {
         confirm = false;
         answer = console.readLine();
         if(answer.equals("si")  || answer.equals("no"))
          confirm = true;
         else
           System.out.println("Escribe unicamente  si o no por favor");
         
      } while (confirm == false);
       
      return answer;
    }

    public void createRenta(){
      //objects
       catalogo = new Catalogo();
       catalogo.storeEquipments();
       ArrayList<Equipment> equipos = catalogo.getEq();
       Renta renta = new Renta(getId());

      if(renta.verifyRentalPayed() == false){
        boolean confirm = false;
        int amount_equip_renta = 0;
        double importeTotal = 0.0;
        String messConfim = "Confirma la renta de estos equipos: \n\n"; 
         
 
        try {
         for (int i = 0; i < catalogo.getCount(); i++) {
       
           catalogo.printEquipments(i);    
           System.out.println("¿Quieres rentar este equipo? (si/no)");
           String answer = validatOptionSINO();
           
           if(answer.equals("si")){
              boolean flow = true;
              do {
               System.out.println("¿Cuanto vas rentar del equipo "+equipos.get(i).getNombre());
               int amoutEquip = con.validarOptionNumber(console.readLine(), "por favor ingresa un numero entero >:(");
             
               
               if(amoutEquip<=equipos.get(i).getDisp() && amoutEquip > 0){
                   equipos.get(i).setCantPedida(amoutEquip);
                   messConfim +=">"+equipos.get(i).getNombre()+",  cantidad pedida:"+equipos.get(i).getCant_pedida()+",  Precio: "+equipos.get(i).getPreciBas()+", importe del equipo: "+ equipos.get(i).getCant_pedida() * equipos.get(i).getPreciBas()+"\n";
                   importeTotal+=equipos.get(i).getCant_pedida() * equipos.get(i).getPreciBas();
                   amount_equip_renta++;
                   flow = false;                
               }else{
                 System.out.println("la cantidad que pides no es adecuada \n porque es una cantidad mayor a equipos disponible "+equipos.get(i).getDisp()+" o es menor igual a cero ");
                 System.out.println("¿Quieres intentar?Escribe(si) o ¿Quieres agregar otro equipo?Escribe(no)");
                 String resp = validatOptionSINO();
 
                 if(resp.equals("no"))
                    flow = false;
 
               }
              }while (flow);
 
           }
           con.clear();
        }
       
        
        messConfim +=  "total a pagara"+importeTotal;
      
        if(importeTotal != 0){
          int input= JOptionPane.showConfirmDialog(jFrame, messConfim);
 
          if(input == 0){
           confirm = true;
            renta.registerRental(importeTotal,amount_equip_renta);
            //renta.prinRental(importeTotal);
            for (int j = 0; j< catalogo.getCount(); j++) {
              if(equipos.get(j).getCant_pedida() != 0){                   
                 equipos.get(j).setCantRen(equipos.get(j).getCant_pedida());
                 equipos.get(j).setDis(equipos.get(j).getCant_pedida());
                  
                 //inserta datos a la tabla rent equip
                  double pago_equipo_precio = equipos.get(j).getCant_pedida() * equipos.get(j).getPreciBas() ;
                  String command ="insert into rent_equip values("+catalogo.createIdPed()+","+renta.getId()+","+equipos.get(j).getId()+","+equipos.get(j).getCant_pedida()+","+equipos.get(j).getPreciBas()+","+pago_equipo_precio+")";
                  rentaEquipos.setData(command);
               } 
             }                  
           }
        
         }
        } catch (Exception e) {
           confirm = false;
           JOptionPane.showConfirmDialog(jFrame, "la renta no se registro  o no supes registrar un  error de tu conexion de internet");
        }
 
       
         if(confirm == true){
           JOptionPane.showMessageDialog(jFrame, "tu renta se registro exitosamente \n Revisa en la opcion de Mostrar renta generadas");
         }
 
      }else{
         for (int i = 0; i < catalogo.getCount(); i++) {
             catalogo.printEquipments(i);
         }

        System.out.println("lo siento, al parecer ya tienes una renta que aun no esta pagada, hasta  que  lo pages, ya vas a poder genmerar otra renta :) \n\n");

        System.out.println("pulsa Enter para regresar al menu principal");
        console.readLine();

      }
       
      
          
    
    }




    
      public  void showRental(){
       Renta renta = new Renta(getId());
       renta.printLIstRenta();
    }

    void deleteRental(){
       
    }


      //later
    public void printInformation(){
        System.out.println("+---------informacion de la cuenta----------+");
        System.out.println("|>Id de usuario: "+getId());
        System.out.println("|>Nombre de usuario:"+super.getuserName().trim()+". ");
        System.out.println("|>Nombre:"+getNombre().trim()+",                    ");
        System.out.println("|>Apellido:"+getApell().trim()+",                   ");
        System.out.println("|>Numero de telefono:"+ getNumTel().trim()+".");
        System.out.println("+--------------------------------------------+\n\n");



        System.out.println("presione Enter para volver al menu principal");
        console.readLine();       
    }

    void changeInformation(){

    }

  //methods to register new customer
    public int id_cliente() {

      int id_cliente = 1800;
      try {
       String  com = "SELECT ID_Cliente from clientes ORDER BY ID_Cliente ASC";
       ResultSet resultSet = rentaEquipos.getData(com);
       
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
      System.out.println("Escribe tu nombre");String  nombreNew = con.validateStrinUnNull(console.readLine(), "no dejes vacio este input");

      System.out.println("Escribe tu apellido paterno");String apell_paternoNew = con.validateStrinUnNull(console.readLine(), "no dejes vacio este input");

      System.out.println("Escribe tu telefono");
      String numTelNew = validateNumTel();
      
      System.out.println("Escribe un nombre de usuario");
      String usernameNew = validateUserName();
      
      System.out.println("Escribe una contraseña de  8 caracteres");
      String contrasenaNew = validatePassword();

      int idCustomerNew = id_cliente();
      int idUserNew = super.getLastIdUser();
      //command mysql
      String comand1 = "insert into clientes values("+idCustomerNew+",'"+nombreNew+"','"+apell_paternoNew+"',"+numTelNew+")";
      super.register(comand1);

      String command2 = "insert into users(id_user,tipo,username,password,cliente) values("+idUserNew+",'"+getTipo()+"','"+usernameNew+"','"+contrasenaNew+"','"+idCustomerNew+"')";
      super.register(command2);


      JOptionPane.showMessageDialog(jFrame, "tu registro fue exitoso, inicia sesion para usra nuestro servicio,recuerda: \n\n >nombre de usuario: "+usernameNew+"\n\n >contraseña: "+contrasenaNew);
  }
  
  //  public static void main(String[] args) {
  //     Cliente cliente = new Cliente(1801,"Alonso","Reyes","6641644270","Gamer12","lima123%"); 
  //     cliente.createRenta(); 
  //     System.exit(0);
  // }
     
}
