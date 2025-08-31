import java.util.*; // Importa la clase util

public class JuegoPapel{
    public static void main(String[] args){
        
        //Declaración de variables y objetos
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int choice;         // elección del jugador (1..3)
        int randomNumber;   // elección de la PC (1..3)
        int scoreJugador=0; // marcador del jugador
        int scorePC=0;      // marcador de la PC

        System.out.println("Bienvenido al Juego de Piedra, Papel o Tijeras!\nJugaremos hasta que alguien gane 2 de 3 rondas");

        //Bucle principal: Evalua si algún jugador tiene suficientes puntos para ganar 
        while(scoreJugador<2 && scorePC<2){
            
            //Bucle que evalua que no haya strings o numeros fuera de rango, se rompe el bucle al recibir un entero entre el 1 y el 3
            while (true) {
                System.out.println("Escoge un valor, solo escribe el número:\n1. Piedra | 2. Papel | 3. Tijeras");
                
                // Si lo siguiente NO es un entero (letras, símbolos, etc.), descarta y vuelve a pedir
                if (!sc.hasNextInt()) {              
                    sc.nextLine();                       // consumir el token inválido
                     System.out.println("Entrada inválida. Escribe 1, 2 o 3.");
                    continue;
                    }
                // Aquí sí hay entero; lo leemos
                choice = sc.nextInt();   
                //Validación de rango permitido          
                if (choice < 1 || choice > 3) {      // ¿fuera de rango?
                    System.out.println("Número fuera de rango. Escribe 1, 2 o 3.");
                    continue;
                    }
            break;                               // válido → salimos del bucle
            }          
            
            // Genera un numero random entre el 1 y el 3
            randomNumber = rand.nextInt(3-1 + 1) +1;

            // Determina el resultado de la ronda: 0=empate, 1=gana jugador, 2=gana PC
            int resultado = compararValores(choice, randomNumber);

            // Actualiza el marcador según el resultado
            if (resultado == 1) scoreJugador++;
            else if (resultado == 2) scorePC++;
            
            // Muestra resultado de la ronda y marcadores actuales
            imprimirGanador(resultado, choice, randomNumber);
            System.out.println("Tu score actual es " + scoreJugador + ", y el score de tu PC es: " + scorePC);
        }     

        // Mensaje final cuando alguien alcanza 2 puntos
        if(scoreJugador>=2){
            System.out.println("Has ganado! Felicidades");
            System.out.println("Tu score final es " + scoreJugador + ", y el score final de tu PC es: " + scorePC);
        } else if(scorePC>=2){
            System.out.println("Has perdido!");
            System.out.println("Tu score final es " + scoreJugador + ", y el score final de tu PC es: " + scorePC);
        }
    }

    /* Método para comparar los el valores ingresados
     * Este juego está hecho en ciclos. 1 ← 2 ← 3 ← 1 (la flecha llega al que gana)
     * Regresa 0 si num1 == num2 (empate)
     * Regresa 1 si num1 gana a num2 en alguno de los tres casos base
     * Regresa 2 en cualquier otro caso (gana num2)
    */

    private static int compararValores(int num1, int num2) {
        if (num1 == num2){
            return 0; }                 // Empate
        if ((num1 == 1 && num2 == 3) ||             // Piedra vence Tijeras
            (num1 == 3 && num2 == 2) ||             // Tijeras vence Papel
            (num1 == 2 && num2 == 1) )               // Papel vence Piedra
            {                
            return 1; // Gana num1
            
             }
        return 2; // En los demás casos, gana num2
    }   

    //Este método regresa el ganador de la ronda con nombres legibles
    private static void imprimirGanador(int resultado, int choice, int randomNumber){
        
        String nameChoice = (choice==1 ? "Piedra" : choice==2 ? "Papel" : "Tijeras");
        String namePC = (randomNumber==1 ? "Piedra" : randomNumber==2 ? "Papel" : "Tijeras");

        switch(resultado){
            case 0:
                System.out.println("Es un empate " + nameChoice + " vs " + namePC);
            break;
            case 1:
                System.out.println("Ganaste la ronda! " + nameChoice + " vence a " + namePC);
            break;
            case 2:
                System.out.println("Perdiste la ronda! " + nameChoice + " vence a " + namePC);
            break;
        }
    }

}
