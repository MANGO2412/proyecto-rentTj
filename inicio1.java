import java.util.Scanner;
class inicio {
    String trabajador="Gael Breton";
    String contraseña;
    void changecontraseña(String contras){contraseña= contras;}
    void printsesion(){
    if(contraseña == "hola") System.out.println("Hola bienvenido que accion quieres hacer?");
    else System.out.print("Esa no es la contraseña vuelve a inciar sesion");}
}
class iniciodemo{
    public static void main(String [] args){
        inicio inicio1 = new inicio();
        Scanner in = new Scanner(System.in);
        String contra;
        System.out.println("Dame la contraseña:");
        contra = in.nextLine();
        inicio1.changecontraseña(contra);
        inicio1.printsesion();
    }
}