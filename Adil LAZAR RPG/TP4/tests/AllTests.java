import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rpg.*;

import java.util.ArrayList;
import java.util.List;


public class AllTests {


    @Test
    public void m(){
        int i=0;
        int b = 0;

        Assertions.assertTrue(i+b==0);
    }


    /**
     * Verifie que la liste generé pour les ennemis est bien de taille n-1, n ou n+1 oÃ¹ n est la taille de la liste de heros.
     */
    @Test
    public void testListEnemy() {
        List<Hero> heroesList = new ArrayList<>();
        heroesList.add(new Hunter(10));
        heroesList.add(new Healer());
        heroesList.add(new Warrior());

        int enemiesSize = (int) Game.probabilite(List.of(heroesList.size() - 1, heroesList.size(), heroesList.size() + 1), List.of(10, 80, 10));
        System.out.println(enemiesSize);

        Assertions.assertTrue(enemiesSize == 2 || enemiesSize == 3 || enemiesSize == 4);
    }

    /**
     * Verifie que la fonction probabilite, qui renvoie un des elements de liste en fonction des probabilites ne renvoie pas -1, qui serait signe d'erreur.
     */
    @Test
    public void testProbabilites () {

        List<Object> liste = new ArrayList<>();
        liste.add(1);
        liste.add(2);
        liste.add(3);

        List<Integer> listeProbabilites = new ArrayList<>();
        listeProbabilites.add(33);
        listeProbabilites.add(34);
        listeProbabilites.add(35);

        Assertions.assertTrue((int) Game.probabilite(liste, listeProbabilites) != -1);
    }

    @Test
    public void SiOnPrendPlusDeDegatsQueDePVOnMeurt(){

        List<Hero> heroesList = new ArrayList<>();
        //200 pv par defaut pour le moment
        Hunter hunter = new Hunter(10);
        heroesList.add(hunter);

        Game game3 = new Game(heroesList);
        game3.generateCombat();

        hunter.reduceLifePoints(300);

        game3.verifyHealth();

        Assertions.assertTrue(game3.getHeroes().isEmpty());
    }

    @Test
    public void ManaManquantAucuneAttaque(){
        Mage mage = new Mage(0);
        Assertions.assertTrue(!mage.attack(new Enemy()));
    }

    @Test
    public void FlecheManquanteAucuneAttaque(){
        Hunter hunter = new Hunter(0);
        Assertions.assertTrue(!hunter.attack(new Enemy()));
    }

    @Test
    public void FlecheDiminue(){

        Hunter hunter = new Hunter(10);

        int flecheDebut = hunter.getArrow();
        hunter.attack(new Enemy());
        Assertions.assertTrue(hunter.getArrow()==flecheDebut-1);
    }

    @Test
    public void PotionDonneDeLaVieHunter(){

        Hunter hunter = new Hunter(10);
        int vieAvant = hunter.getLifePoints();
        System.out.println(vieAvant);

        hunter.useConsumable("A");
        System.out.println(hunter.getLifePoints());

        Assertions.assertTrue(vieAvant<hunter.getLifePoints());
    }

    @Test
    public void PotionDonneDeLaVieCaster(){

        Healer healer = new Healer();
        int vieAvant = healer.getLifePoints();
        System.out.println(vieAvant);

        healer.useConsumable("A");
        System.out.println(healer.getLifePoints());

        Assertions.assertTrue(vieAvant<healer.getLifePoints());
    }

    @Test
    public void PotionDonneDeLaVieWarrior(){
        Warrior warrior = new Warrior();
        warrior.setLifePoints(20);
        int vieAvant = warrior.getLifePoints();
        System.out.println(vieAvant);

        warrior.useConsumable("A");
        System.out.println(warrior.getLifePoints());

        Assertions.assertTrue(vieAvant<warrior.getLifePoints());
    }
}
