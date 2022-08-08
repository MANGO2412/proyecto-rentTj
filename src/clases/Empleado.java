package src.clases;
import java.util.Scanner;
import java.io.Console;
import java.sql.*;



public class Empleado extends User {
    //attributes
      console con = new console();
   

      DataBase rentaEquipos;
      Console console = System.console();
      Scanner in;
      int id;
      String nombre;
      String apellid_pat;
      String num_tel;
   
      
      //constructor
      public Empleado(int id,String nombre,String apellid_pat,String num_tel, String userEmployer, String passwordEmployer,String tipoEmployer){
          super(userEmployer,passwordEmployer,tipoEmployer);
          this.id=id;
          this.nombre = nombre;
          this.apellid_pat = apellid_pat;
          this.num_tel = num_tel;
          in = new Scanner(System.in);
          rentaEquipos = new DataBase();
        
      }


    //methods get
    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public String getApellid_pat(){
        return apellid_pat;
    }

    public String getNumTel(){
        return num_tel;
        
    }

    
    void  clear(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
      }

      

    //method to create equipment with a inventory
    int getInvetory(){
      int mayorId = 0;  
    try {
       ResultSet resultSet = rentaEquipos.getData("select id_inv from inventarios");
       while (resultSet.next()) {
        if(resultSet.getInt("id_inv")>mayorId)
             mayorId = resultSet.getInt("id_inv");
       }
    } catch (Exception e) {
        System.out.println("hay un error en  la linea 65 en la clase empleado "+e);
    }
      mayorId = mayorId +1;
      return mayorId++;  
    }


    int getIdEqu(){
        int mayorId = 0;  
      try {
         ResultSet resultSet = rentaEquipos.getData("select id_equipo from equipos");
         while (resultSet.next()) {
          if(resultSet.getInt("id_equipo")>mayorId)
               mayorId = resultSet.getInt("id_equipo");
         }
      } catch (Exception e) {
          System.out.println("hay un error en  la linea 81 en la clase empleado "+e);
      }
        mayorId = mayorId +1;
        return mayorId++;  
      }


    /*modificaciones a las tablas */
    public int validateID(String campo,String tabla){
      boolean found;
       int number;
       int lastNumberTable;

        do {
        lastNumberTable = 0;
        found = true;
        number = con.validarOptionNumber(console.readLine(), "por favor inserta numeros");
      
        
        try {
          ResultSet rs = rentaEquipos.getData("select "+campo+"  from "+tabla);
          while (rs.next()) {
            int otherNumber =rs.getInt(campo);

            if(otherNumber > lastNumberTable){
               lastNumberTable = otherNumber;
            }
          }
        } catch (Exception e) {System.out.println(e);}

        if(number > lastNumberTable && number != lastNumberTable){
           found = false;          
        }else{
          System.out.println("lo sentimos no puedes usar este id "+number );
          System.out.println("vulve a intentarlo");
        }
      } while (found);
        
        return number;
    }

    //method to add equipment and inventory
    public void addInventory(){
        //formato para registrar el inventario del equipo
        System.out.println("inserta el id de inventario del equipo");
        int id_inv = validateID("id_inv", "inventarios");

        System.out.println("Inserta el total de equipos:");
        int total = con.validarOptionNumber(console.readLine(), "inserta numeros por favor");
        
        //formato para registrar el equipo
        System.out.println("Inserta el id del equipo");
        int id_equipo = validateID("id_equipo", "equipos");

        System.out.println("Inserta el nombre del equipo");
        String name = con.validateStrinUnNull(console.readLine().trim(), "no dejes vacios los campos");

        System.out.println("inserta una descripcion del equipo:");
        String desc = con.validateStrinUnNull(console.readLine(), "no dejes vacios los campos");

        System.out.println("inserta un precio base del equipo:");
        double priceBase = in.nextDouble();

        
       
        rentaEquipos.setData("insert into inventarios(id_inv,cant_disp,total,last_update) values("+id_inv+","+total+","+total+",now());");
          
         String cmm = "insert into equipos values("+id_equipo+","+id_inv+",'"+name+"','"+desc+"',"+priceBase+")";
         rentaEquipos.setData(cmm);       

           
     }
     
     public void changeInventory(){
        System.out.println("-----Sistema para modificar inventario----------");
        System.out.println("|1)Registrar los equipo devueltos               |");
        System.out.println("|2)Aumentar la cantidad de equipos disponibles  |");
        System.out.println("|3)Disminuir la cantidad de equipos disponibles |");
        System.out.println("|4)volver al menu principal                     |");
        System.out.println("-------------------------------------------------");
     }
    
    public void registerPay(){
        String answer;
        Renta dataUser = null;
        boolean validate2;
        boolean validate;
        do {
          validate2 = true;
          do {
            validate = true;
            System.out.println("ingresa el id del usuario");
            int idUser = con.validarOptionNumber(console.readLine(), "escribe  el id en entero");
  
            //attributes
            int id,id_client;
            String fecha_dev,fecha_entr,direcc;
            int estado,days;
            double pay_customer, total_pago,total_pago10;
            String register_rental;
            int amount_equip;
            String nameCustomer,num_tel;
  
  
            try {
              ResultSet rs = rentaEquipos.getData("select * from rentas r, clientes c where c.ID_cliente="+idUser+" and r.id_cliente="+idUser);
              while (rs.next()) {
                if(rs.getInt("r.estado")==0){
                   id = rs.getInt("r.id_renta");
                   id_client = rs.getInt("id_cliente");
                   fecha_dev = rs.getString("r.fecha_entr");
                   fecha_entr = rs.getString("r.fecha_dev");
                   direcc = rs.getString("r.direccion").trim();
                   estado = rs.getInt("r.estado");
                   days = rs.getInt("r.dias_renta");
                   pay_customer = rs.getDouble("r.cantid_pago");
                   total_pago = rs.getDouble("r.importe");
                   register_rental = rs.getString("r.Fecha_Hora_reg");
                   amount_equip = rs.getInt("r.cantidad_equipos");
                   nameCustomer = rs.getString("c.nombre").trim()+" "+rs.getString("c.apell_paterno").trim();
                   num_tel = rs.getString("c.num_tel");
                   validate = false;
                   dataUser = new Renta(id,id_client,fecha_entr,fecha_dev,direcc,estado,days,pay_customer,total_pago,0.0,register_rental,amount_equip,nameCustomer,num_tel);
                }
              } 
  
            } catch (Exception e) {
              System.out.println(e);
            }
  
            if(validate){
              System.out.println("al parecer no una renta no pagada con este  id usario: "+idUser);
  
              System.out.println("Quieres volver a intentar escribe si o no");
              String answer2 = con.validatOptionSINO();
              if (answer2.equals("no")) 
                break; 
            }
  
          } while (validate);
  
          if(dataUser != null){
            dataUser.printAllCustomers(1);
            System.out.println("¿quieres registrar el pago? si/no");
            String option = con.validatOptionSINO();
            
            if(option.equals("si")){
              con.clear();
              double pago_client;
              boolean confirm;
             
              do {
                confirm = false;
                System.out.println("Escribe el pago igual o mayor al importe: "+dataUser.Total_pago);
                pago_client = in.nextDouble();
                 
                if(pago_client >=dataUser.Total_pago){
                  confirm = true;
                }else{
                  System.out.println("no insertates la cantidad acorde a la deuda, vuelve a intarlo");
                }
              } while (confirm = false);
              
                dataUser.setAmount_pay_Customer(pago_client);
                dataUser.setEstado(1);
            }
          }

          System.out.println("¿Quieres seguir registrando pagos de usuarios? si/no");
          answer = con.validatOptionSINO();
          if(answer.equals("no"))
            validate2 = false;
    
        } while (validate2);

        
      
    }


    public  void changeCatolgy(){
       
        //print catology
        Catalogo catalogo = new Catalogo();
        //catalogo.printEquipments();

        //
        do {
         System.out.println("escribe el id del equipo para cambiar los datos");
         int idEquipo = con.validarOptionNumber(console.readLine(),"Escribe numero enteros por favor");
    
         Equipment equipment = catalogo.getEquipment(idEquipo,null,0);
         if(equipment != null){
             int optionN;
             do {
               System.out.println("̣ ___________________________");
               System.out.println("|1)cambiar el nombre.       |");
               System.out.println("|2)cambiar la descripcion.  |");
               System.out.println("|3)cambiar el precio.       |"); 
               System.out.println("|4)salir.                   |");
               System.out.println("|___________________________|");
                optionN = in.nextInt();

               switch (optionN) {
                case 1:
                  System.out.println("Inserta el nuevo nombre"); String name = con.validateStrinUnNull(console.readLine(), "No dejes vacio este camppo del nombre del equipo");
                  equipment.setName(name);
                 break;
                 
                 case 2:
                 System.out.println("Inserta la nueva descripcion"); String description=con.validateStrinUnNull(console.readLine(), "no dejes el campo vacio de la decripcion equipo");
                 equipment.setDescr(description); 
                 break;

                 case 3:
                 System.out.println("Inserta el nuevo precio"); double priceNew = in.nextDouble();
                 equipment.setPrecioBase(priceNew);
                 break;
                  
                case 4:
                 System.out.println("presiona Enter para confirmar");
                 console.readLine();
                break; 
                default:
                  System.out.println("la opcion es incorrecta");
                    break;
               }
             } while (optionN != 4);
             break;
         }else{
             System.out.println("el id del equipo que insertates es incorrecto :( \n vuelve a escribir");
            }
        } while (true);

       

    }


    void deletEquipInventory(){

    }

   
  //consultas de unas tablas
  public void showRental(){
      Renta rentasCustomer = new Renta(0);
      int option;
      do {
        con.clear();
        System.out.println("+--------------------------------------------+");
        System.out.println("|1)mostrar las rentas pendientes             |");
        System.out.println("|2)mostar las rentas para recoger los equipos|");
        // System.out.println("|3)buscar rentas                             |");
        System.out.println("|3)volver al menu principal                  |");
        System.out.println("+--------------------------------------------+");
        option = con.validarOptionNumber(console.readLine(), "Escribe la opcion en numero por favor >:(");


        switch (option) {
          case 1: 
            con.clear();
            rentasCustomer.printListRentaCustomers(0, "fecha_entr");
            con.clear();
          break;
          case 2: 
            con.clear();
            rentasCustomer.printListRentaCustomers(1, "fecha_dev");
            con.clear();
          break;
          case 3: System.out.println("Pulsa Enter para confirma"); console.readLine();break;
      
        
          default:
             System.out.println("la opcion selecciona es incorrecta");
             console.readLine();
             con.clear();
            break;
        }
          
      } while (option != 3);

      
   }

  public void showInventory(){
    try {
      ResultSet rs = rentaEquipos.getData("select *from inventarios, equipos where inventarios.id_inv = equipos.inventario");
      while (rs.next()) {
         int idEqui = rs.getInt("equipos.id_equipo");
         String name = rs.getString("equipos.nombre").trim();
         Double price =  rs.getDouble("precio_base");
         int idInv = rs.getInt("id_inv");
         int disp = rs.getInt("cant_disp");
         int cant_rent = rs.getInt("cant_rent");
         int total = rs.getInt("total");
         String lastUpdate = rs.getString("last_update");

        System.out.println("|id Equipo: "+idEqui+"\n|Nombre del equipo: "+name+"\n|Precio:"+price+"\n|ID del inventario:"+idInv+"\n| disponibles: "+disp+"\n| rentados: "+cant_rent+"\n|  total: "+total+"\n| ultimas modificaciones: "+lastUpdate+"\n\n");
        
      }   

    } catch (Exception e) {}

    System.out.println("presione Enter para voler al menu principal");
    console.readLine();
  }

}
