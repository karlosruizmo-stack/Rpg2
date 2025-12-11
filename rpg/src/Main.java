import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean seguirJuego = true;

        while (seguirJuego) {
            // ===== CREACIÓN DE PERSONAJE =====
            System.out.println("=== Bienvenido al Mundo RPG ===");
            System.out.print("Introduce el nombre de tu personaje: ");
            String nombre = sc.nextLine();

            System.out.println("\nElige la clase de tu personaje:");
            System.out.println("1) Guerrero");
            System.out.println("2) Mago");
            System.out.println("3) Druida");
            System.out.println("4) Elfo");
            System.out.print("Opción: ");
            int opcionClase = sc.nextInt();
            sc.nextLine();

            String clase = "";
            int ps = 0, pm = 0, danoFisico = 0, danoMagico = 0;
            double multiplicador = 1.0;
            int oro = 0;

            // Estadísticas aleatorias según la clase pa mejor nota
            switch (opcionClase) {
                case 1: clase = "Guerrero"; ps = 80 + (int)(Math.random()*21); pm = 20 + (int)(Math.random()*11);
                    danoFisico = 15 + (int)(Math.random()*6); danoMagico = 5 + (int)(Math.random()*6); break;
                case 2: clase = "Mago"; ps = 50 + (int)(Math.random()*11); pm = 50 + (int)(Math.random()*26);
                    danoFisico = 5 + (int)(Math.random()*6); danoMagico = 20 + (int)(Math.random()*16); break;
                case 3: clase = "Druida"; ps = 70 + (int)(Math.random()*16); pm = 30 + (int)(Math.random()*21);
                    danoFisico = 10 + (int)(Math.random()*6); danoMagico = 15 + (int)(Math.random()*11); break;
                case 4: clase = "Elfo"; ps = 60 + (int)(Math.random()*21); pm = 40 + (int)(Math.random()*16);
                    danoFisico = 12 + (int)(Math.random()*6); danoMagico = 12 + (int)(Math.random()*6); break;
                default: clase = "Aventurero"; ps = 70; pm = 40; danoFisico = 10; danoMagico = 10; break;
            }

            // Arrays
            String[] enemigos = {"Ogro","Goblin","Mago Oscuro","Slime","Bandido","Lobo Salvaje"};
            String[] recompensas = {"Poción de vida","Poción Mágica","Poción de daño","Poción de daño extremo",
                    "Elixir de velocidad","Escudo mágico","Bomba de fuego"};
            String[] inventario = new String[10];
            String[] interacciones = {"normal","normal","normal","esquivo","crítico"};

            System.out.print("\n¿Quieres continuar? (s/n): ");
            String continuar = sc.nextLine();
            if (!continuar.equalsIgnoreCase("s")) continue;

            boolean jugadorVivo = true;
            int bonusVidaEnemigo = 0;

            while (jugadorVivo) {
                // ===== EVENTO ALEATORIO AL EXPLORAR =====
                double evento = Math.random();
                if (evento < 0.5) {
                    System.out.println("\nTe encuentras con un enemigo salvaje!");
                } else if (evento < 0.7) {
                    System.out.println("\nEncuentras un cofre con un objeto!");
                    int idxCofre = (int)(Math.random()*recompensas.length);
                    String objCofre = recompensas[idxCofre];
                    for (int i = 0; i < inventario.length; i++) {
                        if (inventario[i] == null) { inventario[i] = objCofre; break; }
                    }
                    System.out.println("Obtienes: " + objCofre);
                    continue; // siguiente exploración
                } else if (evento < 0.85) {
                    System.out.println("\nTe topas con un comerciante. No hay objetos interesantes por ahora.");
                    continue;
                } else {
                    System.out.println("\nNo pasa nada interesante en esta zona.");
                    continue;
                }

                // ===== COMBATE =====
                int idxEnemigo = (int)(Math.random()*enemigos.length);
                String enemigo = enemigos[idxEnemigo];
                int vidaEnemigo = 40 + (int)(Math.random()*61) + bonusVidaEnemigo;
                bonusVidaEnemigo += 5;

                System.out.println("\nMientras caminabas por las mazmorras un " + enemigo + " se cruzó ante ti!");

                while (vidaEnemigo > 0 && ps > 0) {
                    System.out.println("\n===== COMBATE =====");
                    System.out.println("Enemigo: " + enemigo + " | Vida: " + vidaEnemigo);
                    System.out.println(nombre + " | PS: " + ps + " | PM: " + pm);
                    System.out.println("\n1) Atacar\n2) Ataque Mágico (-25 PM)\n3) Objetos\n4) Rendirse");
                    System.out.print("Elige: ");
                    int accion = sc.nextInt(); sc.nextLine();

                    // ATAQUE NORMAL
                    if (accion == 1) {
                        int idxInter = (int)(Math.random()*interacciones.length);
                        String tipo = interacciones[idxInter];
                        System.out.println("Tu ataque es: " + tipo);
                        if (tipo.equals("normal")) { int dmg = (int)(danoFisico*multiplicador); vidaEnemigo -= dmg;
                            System.out.println("Haces " + dmg + " de daño."); }
                        else if (tipo.equals("esquivo")) System.out.println("¡El enemigo esquivó tu ataque!");
                        else if (tipo.equals("crítico")) { int dmg = (int)(danoFisico*multiplicador*1.5);
                            vidaEnemigo -= dmg; System.out.println("Golpe crítico! Haces " + dmg + " de daño."); }
                    }
                    // ATAQUE MÁGICO
                    else if (accion == 2) {
                        if (pm < 25) System.out.println("¡No tienes suficiente PM!");
                        else { pm -= 25; int dmg = (int)(danoMagico*multiplicador); vidaEnemigo -= dmg;
                            System.out.println("Lanzas un ataque mágico y haces " + dmg + " de daño."); }
                    }
                    // OBJETOS
                    else if (accion == 3) {
                        System.out.println("\n===== INVENTARIO =====");
                        for (int i = 0; i < inventario.length; i++) System.out.println((i+1) + ") " + inventario[i]);
                        System.out.print("Elige objeto o 0 para salir: ");
                        int obj = sc.nextInt(); sc.nextLine();
                        if (obj>0 && obj<=inventario.length && inventario[obj-1]!=null) {
                            String item = inventario[obj-1];
                            switch(item){
                                case "Poción de vida": ps+=25; System.out.println("Recuperas 25 PS."); break;
                                case "Poción Mágica": pm+=25; System.out.println("Recuperas 25 PM."); break;
                                case "Poción de daño": danoFisico+=5; System.out.println("Daño físico +5."); break;
                                case "Poción de daño extremo": multiplicador+=0.05; System.out.println("Multiplicador +0.05."); break;
                                case "Elixir de velocidad": multiplicador+=0.1; System.out.println("Multiplicador +0.1 temporal."); break;
                                case "Escudo mágico": ps+=10; System.out.println("PS +10 temporal."); break;
                                case "Bomba de fuego": vidaEnemigo -= 20; System.out.println("Haces 20 de daño directo."); break;
                            }
                            inventario[obj-1]=null;
                        }
                    }
                    // RENDIRSE
                    else if (accion == 4) {
                        oro -= 5; System.out.println("Te rendiste. Pierdes 5 de oro."); break;
                    }

                    // TURNO ENEMIGO
                    if (vidaEnemigo>0){
                        int dmgEn = 10 + (int)(Math.random()*11);
                        String tipoEnemigo = interacciones[(int)(Math.random()*interacciones.length)];
                        System.out.println("\nEl enemigo ataca: " + tipoEnemigo);
                        if (tipoEnemigo.equals("normal")) { ps -= dmgEn; System.out.println("Recibes "+dmgEn+" de daño."); }
                        else if (tipoEnemigo.equals("esquivo")) System.out.println("¡Has esquivado su ataque!");
                        else if (tipoEnemigo.equals("crítico")) { int dmg = dmgEn*2; ps-=dmg; System.out.println("Crítico enemigo! Recibes "+dmg+" de daño."); }

                        pm +=10; if(pm>100) pm=100;
                    }
                }

                // Esto son las recompensas si ganas
                if(ps>0 && vidaEnemigo<=0){
                    System.out.println("\n¡Has derrotado al enemigo!");
                    int idxRec = (int)(Math.random()*recompensas.length); String recompensa = recompensas[idxRec];
                    System.out.println("Obtienes: "+recompensa);
                    for(int i=0;i<inventario.length;i++){ if(inventario[i]==null){inventario[i]=recompensa; break;} }
                    int monedas = 1 + (int)(Math.random()*5); oro+=monedas; System.out.println("Ganas "+monedas+" monedas.");
                    int recuperacion = 25 + (int)(Math.random()*(50)); ps+=recuperacion; if(ps>100) ps=100;
                    System.out.println("Recuperas "+recuperacion+" PS.");
                    //La tabrtnita
                    System.out.println("\nTras una larga batalla, ves a lo lejos una taberna.");
                    System.out.print("¿Deseas entrar? (s/n): "); String entrar = sc.nextLine();
                    if(entrar.equalsIgnoreCase("s")){
                        System.out.println("=== TABERNA ===");
                        for(int i=0;i<3;i++){
                            int idxTienda = (int)(Math.random()*recompensas.length);
                            int precio = 1 + (int)(Math.random()*5);
                            System.out.println((i+1)+") "+recompensas[idxTienda]+" - "+precio+" monedas");
                        }
                        System.out.print("Elige objeto o 0 para salir: ");
                        int eleccion = sc.nextInt(); sc.nextLine();
                        if(eleccion>0 && eleccion<=3){
                            int idxObj = (int)(Math.random()*recompensas.length); int precio = 1 + (int)(Math.random()*5);
                            if(oro>=precio){ inventario[0]=recompensas[idxObj]; oro-=precio; System.out.println("Compraste "+recompensas[idxObj]); }
                            else System.out.println("No tienes suficiente oro.");
                        }
                    }
                }

                // Muerte
                if(ps<=0){ System.out.println("\nHas muerto."); jugadorVivo=false; break; }

                System.out.print("\n¿Deseas seguir explorando? (s/n): "); String seguir = sc.nextLine();
                if(!seguir.equalsIgnoreCase("s")) break;
            }

        }
        System.out.println("GG!");
    }
}












