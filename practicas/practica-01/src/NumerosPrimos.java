/*
 * Autor: Maria Fernanda Miranda Sanchez
 * ICC Laboratorio
 */

import java.util.Scanner;

public class NumerosPrimos {
    public static void main(String[]args){

        /*Declaración de objetos y varaibles
         * Scanner para leer input
         * Primo para verificar si un numero es primo
         * SumaN para usar la fórmula lineal
         */
        Scanner sc = new Scanner(System.in);
        Primo nPrimo = new Primo();
        SumaN formula = new SumaN(2, 1);

        int valorAbsoluto;

        // Bucle de validación: pide un entero distinto de 0 y 1
        while(true){
            System.out.println("Ingresa un número");
            if(!sc.hasNextInt()){
                sc.next();
                System.out.println("Entrada inválida. Ingresa un número entero");
                continue;
            }
            
            // Lee el entero y toma su valor absoluto (Math.abs)
            int variableX = sc.nextInt();
            valorAbsoluto = Math.abs(variableX);

            if(valorAbsoluto==0 || valorAbsoluto==1){
                System.out.println("Tu número no puede ser 0 ni 1. Ingresa otro número");
                continue;
            }
            break;
        }

        System.out.println("-- Resultado --");

        //Evalua usando la formula 2n+1 desde i hasta |x|
        for(int i=1; i <= valorAbsoluto; i++){
            int result = formula.evalua(i);
            
            //Si el resultado es primo, imprime "es primo" al final
            if(nPrimo.esPrimo(result)){
                System.out.println(result +" es primo");
            }
            else{
                System.out.println(result);
            }
        }

    }

}
