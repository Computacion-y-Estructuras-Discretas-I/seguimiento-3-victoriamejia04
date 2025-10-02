package ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        
        if (s == null || s.isEmpty()) {
        return true;
    }

     PilaGenerica<Character> pila = new PilaGenerica<>(s.length());

     for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);

     if (c == '(' || c == '[' || c == '{') {
            pila.Push(c);
        }
        
        else if (c == ')' || c == ']' || c == '}') {
            
            if (pila.getTop() == 0) {
                return false;
            }

            char tope = pila.Pop();

           
            if (!esPar(tope, c)) {
                return false;
            }
        }
       
    }

    
    return pila.getTop() == 0;
}


private boolean esPar(char apertura, char cierre) {
    return (apertura == '(' && cierre == ')') ||
           (apertura == '[' && cierre == ']') ||
           (apertura == '{' && cierre == '}');
}


    

      
    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) throws Exception {
    TablasHash tabla = new TablasHash(numeros.length * 2);
    Set<String> paresUnicos = new HashSet<>();

    for (int num : numeros) {
        int complem = objetivo - num;

        if (tabla.search(complem, complem)) {
            int a = Math.min(num, complem);
            int b = Math.max(num, complem);
            paresUnicos.add("(" + a + ", " + b + ")");
        }

        tabla.insert(num, num);
    }

    if (paresUnicos.isEmpty()) {
        System.out.println("Ningún par encontrado para suma " + objetivo);
    } else {
        System.out.println("Pares: " + paresUnicos);
    }
}

      
    

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
