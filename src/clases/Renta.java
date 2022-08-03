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


    int id , id_client,dias_rentar,estado_renta,count;
    String fecha_entrega, fecha_dev,direccion_rent;
    double amount_pay_customer, Total_pago,total_pago10;
    ArrayList<Renta> rentas;
    boolean register; 
    
    //constructor to register a rental
    public Renta(int id_client){
        this.id_client = id_client;
        this.count = 0;
        rentas = new ArrayList<Renta>();
        
    }
    
    public Renta(int id,int id_client,String fecha_dev,String fecha_entr,String direcc,int estado,int days,double amount_pay_customer,double total_pago, double total_pago10){
       this.id = id;
       this.id_client = id_client;
       this.fecha_dev = fecha_dev;
       this.direccion_rent = direcc;
       this.estado_renta = estado;
       this.dias_rentar = days ;
       this.amount_pay_customer = amount_pay_customer;
       this.fecha_entrega = fecha_entr;
       this.Total_pago = total_pago;     
    }


    //constructor to get list of rental
   


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

    

 
     //method validate
    String  validateDate(){
      DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      String formattedDate = myobj.format(myFormatObj);


      String date;
     
      String []fechasUser = formattedDate.split("-");
      String []fechas = null;
      boolean estado  = true; 
      do {
        try {
           date = console.readLine().trim();
           fechas= date.split("-");
           

          if(Integer.valueOf(fechas[0]) > Integer.valueOf(fechasUser[0]) || Integer.valueOf(fechas[1]) > Integer.valueOf((fechasUser[1]))){
            estado = false;
          }else{
            System.out.println("la fecha que introduciste "+date+"-"+calendar.get(Calendar.YEAR)+" no es una fecha actualizada con la fecha actual: "+formattedDate);  ;
            System.out.println("vuelve a intentarlo por favor");
          }
         
        } catch (NumberFormatException e) {
          System.out.println("tu no escribiste el format solicitado, vulve a intentarlo por favor >:( ");
        }
        
      } while (estado);
      return  ""+calendar.get(Calendar.YEAR)+"-"+fechas[1]+"-"+fechas[0];
     }


     double validateTotalPay(int amountDays,double TotalPago){
       if(amountDays > 1){
         double totalPorcent = 0.1*amountDays;
         TotalPago = TotalPago*(1+totalPorcent);

       }else{
        TotalPago = 0.0;
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
      System.out.println("quieres rentar el dia por mas dias(si/no)");
      String answer = in.next();

      if(answer.equals("si")){
         System.out.println("por cuantos dias mas lo quieres rentar");
         amountDays=in.nextInt(); 
      }

      return amountDays;
     }

    
    void registerRental(double importe){
       this.id = lastId();

       System.out.println("Escribe la direccion de entrega de los equipos");
       this.direccion_rent = con.validateStrinUnNull(console.readLine(), "por favor no dejes campos de input vacios >:(");

       System.out.println("Escribe la fecha de entrega en este formato(dd-mm) 22-03");
       this.fecha_entrega = validateDate();

       this.dias_rentar = getAmountDay();
       String []fechas = this.fecha_entrega.split("-");

       this.fecha_dev = getLastDate(dias_rentar, Integer.valueOf(fechas[2]), Integer.valueOf(fechas[1]));
       
       this.total_pago10 = validateTotalPay(dias_rentar, importe);
       this.Total_pago = importe;
       
       String values = id+","+id_client+",'"+fecha_entrega+"','"+fecha_dev+"',"+importe+",'"+direccion_rent+"',"+estado_renta+","+dias_rentar+",now()";
       String cm = "insert into rentas(id_renta,id_cliente,fecha_entr,fecha_dev,importe,direccion,estado,dias_renta,Fecha_Hora_reg) values("+values+")";
       rentaEquipos.setData(cm);
    }

   public void printLIstRenta(){
       try {
        ResultSet rs =  rentaEquipos.getData("select *from rentas where id_cliente="+id_client);

        while (rs.next()) {
          if(rs.getInt("dias_renat")>1){
              Double importeOrin = rs.getDouble("importe")*(100/((rs.getInt("dias_renta")*10)+100));
             rentas.add(new Renta(rs.getInt("id_renta"), this.id_client, rs.getString("fecha_dev"),rs.getString("fecha_entr"), rs.getString("direccion"), rs.getInt("estado"), rs.getInt("dias_renta"), rs.getDouble("cantidad_pago"),importeOrin, rs.getDouble("importe")));
          }else{
            rentas.add(new Renta(rs.getInt("id_renta"), this.id_client, rs.getString("fecha_dev"),rs.getString("fecha_entr"), rs.getString("direccion"), rs.getInt("estado"), rs.getInt("dias_renta"), rs.getDouble("cantidad_pago"),rs.getDouble("importe"), 0.0));
          }
          
          this.count = this.count +1;
        }
       } catch (Exception e) {}

       for (int i = 0; i < this.count; i++) {
        
       }
   }
  

}
