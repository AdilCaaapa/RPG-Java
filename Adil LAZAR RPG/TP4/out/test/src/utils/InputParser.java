package utils;

import rpg.Hero;

import java.util.List;
import java.util.Scanner;

public class InputParser {
    Scanner sc = new Scanner(System.in);

    public String askTurnChoice(int n){
        int choix;

        String choixString;

        do {
            System.out.println("Que voulez-vous faire ce tour avec le hero n°"+n+" ? [A (attaquer)/D (defendre)/C (consommable)]");
            choixString = sc.nextLine();
        }
        while (!choixString.equals("A") && !choixString.equals("D") && !choixString.equals("C"));

        return choixString;
    }

    public String askPrice(int numero,Hero hero){
        String choixString;
        do {
            System.out.println("Quelle compétence voulez-vous ameliorer pour le hero n°"+numero+" qui est du type '"+hero.getClass().toString()+"' ? [A (Augmenter Armure)/B (Augmenter Dégâts)/C (Augmenter Efficacité Potion et Nourriture)/D (Augmenter le nombre de potion ou nourriture)/E (Augmenter Nombre de Fleches ou Mana)]");
            choixString = sc.nextLine();
        }
        while (!choixString.equals("A") && !choixString.equals("B") && !choixString.equals("D") && !choixString.equals("C") && !choixString.equals("D") && !choixString.equals("E"));

        return choixString;
    }

    public int askNumberInList(List<Object> list) {
        int choix;

        int choixInt=-1;

        do {
            System.out.println("Quel est votre choix ? Numero entre 0 et "+(list.size()-1));

            while(!sc.hasNextInt()) {
                sc.next();
            }
            choixInt = sc.nextInt();
            sc.nextLine();
        }
        while (choixInt<0 || choixInt>=list.size());


        return choixInt;
    }

    public String askConsumable() {

        String choix;

        do {
            System.out.println("Quel type de consommable voulez-vous prendre ? P (Potions) / N (Nourriture)");

            choix = sc.nextLine();


        }
        while (!choix.equals("P") && !choix.equals("N"));

        return choix;
    }
}
