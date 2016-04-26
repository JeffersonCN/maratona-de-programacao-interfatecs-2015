import java.util.Scanner;

class Niver {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int dia, mes, colegas, diaColega, mesColega;
        boolean encontrado = false;
        
        while ((dia = scan.nextInt()) != 0 && (mes = scan.nextInt()) != 0) {
            encontrado = false;
            colegas = scan.nextInt();
            
            for (int i = 0; i < colegas; i++) {
                diaColega = scan.nextInt();
                mesColega = scan.nextInt();
                
                if (dia == diaColega && mes == mesColega) {
                    encontrado = true;
                }
            }
            System.out.println(encontrado ? "S" : "N");
        }
    }
}