package src.clases;
import java.util.Scanner;
import java.io.Console;
import java.sql.*;



public class Empleado extends User {
    //attributes
      DataBase rentaEquipos;
      Console console;
      Scanner in;
      int id;
      String nombre;
      String apellid_pat;
      String num_tel;

      
      
      public Empleado(int id,String nombre,String apellid_pat,String num_tel, String userEmployer, String passwordEmployer,String tipoEmployer){
          super(userEmployer,passwordEmployer,tipoEmployer);
          this.id=id;
          this.nombre = nombre;
          this.apellid_pat = apellid_pat;
          this.num_tel = num_tel;
          in = new Scanner(System.in);
          rentaEquipos = new DataBase();
          console = System.console();
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
        System.out.println("hay un erro en  la linea 65 en la clase empleado "+e);
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

    public void addInventory(){
        System.out.println("Inserta el total de equipos:");
        int total = in.nextInt();

        System.out.println("Inserta el nombre del equipo");
        String name = in.next();

        System.out.println("inserta una descripcion del equipo:");
        String desc = console.readLine();

        System.out.println("inserta un precio base del equipo:");
        double priceBase = in.nextDouble();

        int idInventario = getInvetory();
       
         rentaEquipos.setData("insert into inventarios(id_inv,total,last_update) values("+idInventario+","+total+",now());");
          
         String cmm = "insert into equipos values("+getIdEqu()+","+idInventario+",'"+name+"','"+desc+"',"+priceBase+")";
         rentaEquipos.setData(cmm);       

           
     }


     void changeInventory(){
       
    }



    public  void changeCatolgy(){
        clear();
        //print catology
        Catalogo catalogo = new Catalogo();
        //catalogo.printEquipments();

        //
        do {
         System.out.println("escribe el id del equipo para cambiar los datos");
         int idEquipo = in.nextInt();
    
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
                  System.out.println("inserta el nuevo nombre"); String name = console.readLine();
                  equipment.setName(name);
                 break;
                 
                 case 2:
                 System.out.println("inserta la nueva descripcion"); String description=console.readLine();
                 equipment.setDescr(description); 
                 break;

                 case 3:
                 System.out.println("inserta el nuevo precio"); double priceNew = in.nextDouble();
                 equipment.setPrecioBase(priceNew);
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


    void deletInventory(){

    }

}
