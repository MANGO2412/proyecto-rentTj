package src.clases;
import java.io.Console;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import  java.util.*;

public class Renta {
    //ojects
    DataBase rentaEquipos = new DataBase();
    Console console = System.console();
    Calendar calendar = Calendar.getInstance();
    Scanner in = new Scanner(System.in);
    console con = new console();
    LocalDateTime myobj = LocalDateTime.now();


    int id , id_client,dias_rentar,estado_renta,count,amount_equip;
    String fecha_entrega, fecha_dev,direccion_rent,register_rental;
    double amount_pay_customer, Total_pago,total_pago10;
    ArrayList<Renta> rentas;
    boolean register; 



    //attribust to third constructor
     String nameCustomer;
     String numeroTelefono;

    
    //constructor to register a rental
    public Renta(int id_client){
        this.id_client = id_client;
        this.count = 0;
        rentas = null;
        
    }
  
    //constructar para las rentas del cliente
    public Renta(int id,int id_client,String fecha_dev,String fecha_entr,String direcc,int estado,int days,double amount_pay_customer,double total_pago, double total_pago10,String register_rental,int amount_equip){
       this.id = id;
       this.id_client = id_client;
       this.fecha_dev = fecha_dev;
       this.direccion_rent = direcc;
       this.estado_renta = estado;
       this.dias_rentar = days ;
       this.amount_pay_customer = amount_pay_customer;
       this.fecha_entrega = fecha_entr;
       this.Total_pago = total_pago;
       this.total_pago10 = total_pago10;
       this.register_rental = register_rental;
       this.amount_equip = amount_equip;
    }

  
    //constructor para las rentas de los clientes
    public Renta(int id,int id_client,String fecha_dev,String fecha_entr,String direcc,int estado,int days,double amount_pay_customer,double total_pago, double total_pago10,String register_rental,int amount_equip,String nameCustomer,String numTel){
      this.id = id;
      this.id_client = id_client;
      this.fecha_dev = fecha_dev;
      this.direccion_rent = direcc;
      this.estado_renta = estado;
      this.dias_rentar = days ;
      this.amount_pay_customer = amount_pay_customer;
      this.fecha_entrega = fecha_entr;
      this.Total_pago = total_pago;
      this.register_rental = register_rental;
      this.amount_equip = amount_equip;
      this.nameCustomer = nameCustomer;
      this.numeroTelefono = numTel;
   }


    int lastId(){
        int id_renta = 2040;
      try {
       String  com = "SELECT id_renta from rentas ORDER BY ID_Cliente ASC";
       ResultSet resultSet = rentaEquipos.getData(com);
       
       while (resultSet.next()) {
          if(resultSet.getInt("id_renta") >  id_renta){
            id_renta = resultSet.getInt("id_renta");
          }     
        }
      } catch (Exception e) {System.out.println(e);}
     
      id_renta = id_renta+1;
      return id_renta;
    }

    public int getId(){
      return id;
    }


    //method set
    void setDireccion(String direccion_rent){
     this.direccion_rent = direccion_rent;
    }

    void setEstado(int num){
       this.estado_renta = num;
       rentaEquipos.setData("update rentas set estado="+this.estado_renta+" where id_renta="+id);
    }

    void setAmount_pay_Customer(double value){
      this.amount_pay_customer = value;
      rentaEquipos.setData("update rentas set cantid_pago="+this.amount_pay_customer+" where id_renta="+id);
    }

     
     //
     public boolean verifyRentalPayed(){
      boolean estado = false;

      try {
        ResultSet rs = rentaEquipos.getData("SELECT  r.estado from clientes c,rentas r where c.ID_cliente ='"+this.id_client+"' and r.id_cliente = c.ID_cliente; ");
        while (rs.next()) {
          if(rs.getInt("r.estado") == 0){
           estado = true;          
          }
       } 
      } catch (Exception e){} 
       return  estado;
     }


 
     //methods validate
    String  validateDate(){
      DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      String formattedDate = myobj.format(myFormatObj);


      String date;
     
      String []fechasUser = formattedDate.split("-");
      String []fechas = null;
      boolean estado  = true; 
      do {
        try {
          //getdate last renatl
          String[] arrayDateLast = null;

           date = console.readLine().trim();
           fechas= date.split("-");
    

          if(Integer.valueOf(fechas[0]) > Integer.valueOf(fechasUser[0]) || Integer.valueOf(fechas[1]) > Integer.valueOf(fechasUser[1])){
            estado = false;
          }else{
            System.out.println("la fecha que introduciste "+date+"-"+calendar.get(Calendar.YEAR)+" no es una fecha actualizada con la fecha actual: "+formattedDate);  ;
            System.out.println("vuelve a intentarlo por favor");
          }
         
        } catch (Exception   e) {
          System.out.println("tu no escribiste el formato solicitado, vulve a intentarlo por favor >:( ");
        }
        
      } while (estado);
      return  ""+calendar.get(Calendar.YEAR)+"-"+fechas[1]+"-"+fechas[0];
     }


    double validateTotalPay(int amountDays,double TotalPago){
       if(amountDays > 1){
         double totalPorcent = 0.1*amountDays;
         TotalPago = TotalPago*(1+totalPorcent);

       }

      return TotalPago;
     }

    String getLastDate(int amountdays,int day, int moth){
        int totalHour = amountdays*24;
        
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH,moth);
        calendar.set(Calendar.HOUR, totalHour);

      return ""+calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
     }

    int getAmountDay(){
      int amountDays = 1;

      System.out.println("la renta dura un dia, pero si deseas rentar los equipos por mas dias, el total del pago aumenta un 10% por dia");
      System.out.println("Las entregas se realiza  de 10:00 de la mañana a la 1:00 de la tarde en la decha solicitadda, igualmente al recojer los equipos");
      System.out.println("quieres rentar el dia por mas dias(si/no)");
      String answer = in.next();

      if(answer.equals("si")){
         System.out.println("por cuantos dias mas lo quieres rentar");
         amountDays=in.nextInt(); 
      }

      return amountDays;
     }

    
     //method to register a rental
    public void registerRental(double importe, int cantEquip){
       this.id = lastId();

       System.out.println("Escribe la direccion de entrega de los equipos");
       this.direccion_rent = con.validateStrinUnNull(console.readLine(), "por favor no dejes campos de input vacios >:(");

       System.out.println("Escribe la fecha de entrega en este formato(dd-mm) 22-03");
       this.fecha_entrega = validateDate();

       this.dias_rentar = getAmountDay();
       String []fechas = this.fecha_entrega.split("-");

       this.fecha_dev = getLastDate(dias_rentar, Integer.valueOf(fechas[2]), Integer.valueOf(fechas[1]));
       
       this.Total_pago = validateTotalPay(dias_rentar, importe);
       
       String values = id+","+id_client+",'"+fecha_entrega+"','"+fecha_dev+"',"+cantEquip+","+Total_pago+",'"+direccion_rent+"',"+estado_renta+","+dias_rentar+",now()";
       String cm = "insert into rentas(id_renta,id_cliente,fecha_entr,fecha_dev,cantidad_equipos,importe,direccion,estado,dias_renta,Fecha_Hora_reg) values("+values+")";
       rentaEquipos.setData(cm);
    }

     //method to print a  rental
     public void printStateCustomer(int amount){
      System.out.println("------------------------------------------------------------------");
      System.out.println("renta  numero: "+amount);
      System.out.println("fecha hora  de registro de la renta:"+this.register_rental);
      System.out.println("------------------------------------------------------------------");
      System.out.println(">Fecha de entrega: "+this.fecha_entrega);
      System.out.println(">Fecha de devolucion: "+this.fecha_dev);
      System.out.println(">Cantidad de pago: "+this.amount_pay_customer+" pesos");

      if(estado_renta == 1){
        System.out.println(">Estado de la renta:pagada ");
        if(dias_rentar > 1){
         System.out.println("cambio: "+(amount_pay_customer-total_pago10)+" pesos");
        }else{
         System.out.println("cambio: "+(amount_pay_customer-Total_pago)+" pesos");
        }
       }else{
        System.out.println(">Estado de la renta:No pagada");
       }

     
      System.out.println(">Dias de la renta: "+this.dias_rentar);
      System.out.println(">direccion de entrega: "+this.direccion_rent+"\n");
      System.out.println("lista de equipos rentados");
      System.out.println("<------------------------------------------------------------------>");
      System.out.println(getListeEquipmen());
     
      if(dias_rentar > 1){
        System.out.println("tu debias: "+this.Total_pago+" pesos");
        System.out.println("tu debes:  "+this.total_pago10+" pesos  por"+dias_rentar+" dias de renta extra de los equipos");

      }else{
        System.out.println("total de pago: "+this.Total_pago+" pesos");
      }
      System.out.println("------------------------------------------------------------------\n\n");
    }


    void printAllCustomers(int amount){
      System.out.println("------------------------------------------------------------------");
      System.out.println("renta  numero: "+amount);
      System.out.println("nombre del cliente: "+nameCustomer+"   numero de telefono: "+numeroTelefono);
      System.out.println("fecha hora  de registro de la renta:"+this.register_rental);
      System.out.println("------------------------------------------------------------------");
      System.out.println(">Fecha de entrega: "+this.fecha_entrega);
      System.out.println(">Fecha de devolucion: "+this.fecha_dev);

      System.out.println(">Cantidad de pago: "+this.amount_pay_customer);

      if(estado_renta == 1){
       System.out.println(">Estado de la renta:pagada ");
       if(dias_rentar > 1){
        System.out.println("cambio: "+(amount_pay_customer-total_pago10));
       }else{
        System.out.println("cambio: "+(amount_pay_customer-Total_pago));
       }
      }else{
       System.out.println(">Estado de la renta:No pagada");
      }

      
      
      System.out.println(">Dias de la renta: "+this.dias_rentar);
      System.out.println(">direccion de entrega: "+this.direccion_rent+"\n");
      System.out.println("lista de equipos rentados");
      System.out.println("<------------------------------------------------------------------>");
      System.out.println(getListeEquipmen());
     
      if(dias_rentar > 1){
        System.out.println("tu debias: "+this.Total_pago);
        System.out.println("tu debes:  "+this.total_pago10+" por "+dias_rentar+" dias de renta extra de los equipos");

      }else{
        System.out.println("total de pago: "+this.Total_pago);
      }
      
      System.out.println("------------------------------------------------------------------\n\n");
    }
     
     //methods to get a list of equipments
     String getListeEquipmen(){
        

        String equipos = "";
      
      try {
        ResultSet rs = rentaEquipos.getData("select *from rent_equip re, equipos e ");
        while (rs.next()) {
          if (rs.getInt("re.id_renta")== id && rs.getInt("re.id_equip") == rs.getInt("e.id_equipo") ) {
            String nameEquip = rs.getString("e.nombre").trim();
            int amountBor = rs.getInt("re.cant_ped");
            double priceBase = rs.getDouble("re.precio_base");
            double import_per=rs.getDouble("re.importe_per_amount");
 
            
            equipos += ">EQUIPO:"+nameEquip+"  CANTIDAD:"+amountBor+"   PRECIO:"+priceBase+"  TOTAL:"+import_per+"\n\n"; 
          }
           
        }
     } catch (Exception e) {System.out.println(e);}

     return equipos;
   }

     ArrayList<Renta> getRentasCUstomer(){
       ArrayList<Renta>  rentas2 = new ArrayList<Renta>();
       try {
        ResultSet rs =  rentaEquipos.getData("select *from rentas where id_cliente="+this.id_client);

        while (rs.next()){
          if(rs.getInt("dias_renta")>1){
              double suma = (rs.getInt("dias_renta")*10)+100;
              double importeOrin = (rs.getDouble("importe")*100)/suma;
          
             rentas2.add(new Renta(rs.getInt("id_renta"), this.id_client, rs.getString("fecha_dev"),rs.getString("fecha_entr"), rs.getString("direccion"), rs.getInt("estado"), rs.getInt("dias_renta"), rs.getDouble("cantid_pago"),importeOrin, rs.getDouble("importe"),rs.getString("Fecha_Hora_reg"),rs.getInt("cantidad_equipos")));
          }else{
            rentas2.add(new Renta(rs.getInt("id_renta"), this.id_client, rs.getString("fecha_dev"),rs.getString("fecha_entr"), rs.getString("direccion"), rs.getInt("estado"), rs.getInt("dias_renta"), rs.getDouble("cantid_pago"),rs.getDouble("importe"), 0.0,rs.getString("Fecha_Hora_reg"),rs.getInt("cantidad_equipos")));
          }
          
          //this.count = this.count +1;
        }

       } catch (Exception e) {System.out.println(e);}

       return rentas2;
   }
   
    //method to get liste of all customer
     public ArrayList<Renta> getAllCustmers(int estadoOrder){
      ArrayList<Renta>  rentas2 = new ArrayList<Renta>();
      try {
        ResultSet rs =  rentaEquipos.getData("select *from rentas ,clientes  where estado="+estadoOrder);
        

        while (rs.next()){
          String names =rs.getString("nombre") +""+rs.getString("apell_paterno");
          if(rs.getInt("dias_renta")>1){
              double suma = (rs.getInt("dias_renta")*10)+100;
              double importeOrin = (rs.getDouble("importe")*100)/suma;
        
            rentas2.add(new Renta(rs.getInt("id_renta"), rs.getInt("id_cliente"), rs.getString("fecha_dev"),rs.getString("fecha_entr"), rs.getString("direccion"), rs.getInt("estado"), rs.getInt("dias_renta"), rs.getDouble("cantid_pago"),importeOrin, rs.getDouble("importe"),rs.getString("Fecha_Hora_reg"),rs.getInt("cantidad_equipos"),names,rs.getString("num_tel")));
          }else{
            rentas2.add(new Renta(rs.getInt("id_renta"), rs.getInt("id_cliente"), rs.getString("fecha_dev"),rs.getString("fecha_entr"), rs.getString("direccion"), rs.getInt("estado"), rs.getInt("dias_renta"), rs.getDouble("cantid_pago"),rs.getDouble("importe"), 0.0,rs.getString("Fecha_Hora_reg"),rs.getInt("cantidad_equipos"),names,rs.getString("num_tel")));
          }
          
          //this.count = this.count +1;
        }

       } catch (Exception e) {System.out.println(e);}

       return rentas2;     
     }

     //methods to print a list of rentals
     public void printLIstRenta(){
      rentas = getRentasCUstomer();   
       if(rentas.size() > 0){
          for (int i = 0; i < rentas.size(); i++) {
               rentas.get(i).printStateCustomer(i+1);
          }
       }else{
        System.out.println("No tienes ninguna renta genereada\n\n");
       }


       System.out.println("Presiona un Enter para volver al menu principal");
       console.readLine();     
      }  
      
      public void printListRentaCustomers(int numOrden, String orderCamp){
        
        
        if(numOrden == 0){
          rentas =  getAllCustmers(numOrden);
          if(rentas.size() >= 0){
            for (int i = 0; i < rentas.size(); i++) {
                 rentas.get(i).printAllCustomers(i+1);
            }
         }else{
          System.out.println("Los clientes todavia no genere rentas\n\n");
         }
        }else if(numOrden == 1){
          LocalDateTime myob = LocalDateTime.now();
          DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           String formattedDate = myob.format(myFormatObj);
           System.out.println(formattedDate);

          rentas =  getAllCustmers(numOrden);
          if(rentas.size() >= 0){
            for (int i = 0; i < rentas.size(); i++) {
              if(formattedDate.equals(rentas.get(i).fecha_dev))
                 rentas.get(i).printAllCustomers(i+1);
            }
         }else{
          System.out.println("no hay rentas finalizada\n\n");
         }
        }


        
 
 
        System.out.println("Presiona un Enter para volver al menu principal");
        console.readLine();  
      }
  

} 
