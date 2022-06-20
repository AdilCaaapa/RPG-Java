package rpg;

import utils.InputParser;

import java.util.*;

public class Game {

    public static List<Hero> heroes;
    public static List<Enemy> enemies;
    public static InputParser inputParser = new InputParser();
    private static int playerTurn;

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Game() {

    }

    public Game(List<Hero> heroes) {
        this.heroes = heroes;
    }

    Enemy enemy;
    static int probaBoss = 10;

    public static void main(String[] args) {
        playGame();
    }


    public static void playGame(){

        heroes = new ArrayList<>();
        heroes.add(new Hunter(10));
        heroes.add(new Healer());
        heroes.add(new Warrior());



        while (!heroes.isEmpty()){
            generateCombat();

//Ordre aléatoire des joueurs
            Collections.shuffle(heroes);
            Collections.shuffle(enemies);

            playerTurn = (int) probabilite(List.of(0,1),List.of(50,50));

            while(!heroes.isEmpty() && !enemies.isEmpty()){
                fight();
            }
        }
        System.out.println("Vous avez PERDU !");
    }

    private static void RemiseRecompenses() {
        System.out.println("Vous avez remporté le combat !");
        int i=0;
        for(Hero hero : heroes){
            i++;
            hero.resetLifePoints();

            String choice = inputParser.askPrice(i,hero);
            switch (choice) {
                case "A":
                    hero.increaseArmor();

                case "B":
                    hero.increaseDamage();

                case "C":
                    hero.increaseConsumableEffect();

                case "D":
                    hero.increaseConsumableNumber();

                case "E":
                    hero.increaseArrowOrMana();
            }
        }
    }

    private static void fight() {

        String choice;
        int aimed;
        Hero heroActuel;

        verifyHealth();
        actionChoix();
        verifyHealth();

        if(!enemies.isEmpty()){
            verifyHealth();
            randomAttack();
        }
    }

    private static void randomAttack() {
        int aimed;
        Random random = new Random();

        for(int i=0; i<enemies.size();i++){
            aimed = random.nextInt(heroes.size());
            enemies.get(i).attack(heroes.get(aimed));
        }
    }

    private static int actionChoix() {
        Hero heroActuel;
        String choice;
        int aimed;
        for (int i = 0; i < heroes.size(); i++) {
            heroActuel = heroes.get(i);
            heroActuel.resetDefence();
            System.out.println("Heros: "+heroes);
            List<Integer> heroesHealth = new ArrayList<>();

            for(Hero hero : heroes){
                heroesHealth.add(hero.getLifePoints());
            }
            System.out.println("Points de vies alliés: "+heroesHealth);
            List<Integer> enemyHealth = new ArrayList<>();

            for(Enemy enemy : enemies){
                enemyHealth.add(enemy.getLifePoints());
            }

            System.out.println("Points de vies ennemis: "+enemyHealth);
            System.out.println("Ennemis: "+enemies+"\n\n");


            System.out.println("Numero du hero actuel: "+i);
            System.out.println("Type de hero actuel: "+heroActuel.getClass());
            System.out.println("PV: "+heroActuel.getLifePoints()+"Weapon DMG: "+heroActuel.getWeaponDamage());

            if(heroActuel.getClass()==Healer.class || heroActuel.getClass()==Mage.class){
                System.out.println("Mana left : "+((SpellCaster)heroActuel).getMana());
            }
            else if (heroActuel.getClass()==Hunter.class){
                System.out.println("Arrows left : "+((Hunter)heroActuel).getArrow());
            }
            System.out.println("Liste de potions: "+heroActuel.getPotions());
            System.out.println("Liste de nourriture: "+heroActuel.getFood());

            boolean bool = true;
            do {
                choice = inputParser.askTurnChoice(i);

                switch (choice) {
                    case "A":
                        System.out.println("Vous avez choisi d'attaquer !");

                        if (heroActuel.getClass() == Healer.class) {
                            System.out.println("Quel allié voulez-vous soigner ? ");
                            aimed = inputParser.askNumberInList(new ArrayList<>(heroes));
                            bool = ((Healer) heroActuel).healattack(heroes,aimed);
                        }
                        else {
                            System.out.println("Quel ennemi voulez-vous attaquer ? ");
                            aimed = inputParser.askNumberInList(new ArrayList<>(enemies));
                            bool = heroActuel.attack(enemies.get(aimed));
                        }

                        verifyHealth();
                        if(enemies.isEmpty()){
                            RemiseRecompenses();
                            return 0;
                        }

                        break;


                    case "D":
                        System.out.println("Vous avez choisi de défendre !");
                        heroActuel.defend();
                        bool=true;
                        break;

                    case "C":
                        System.out.println("Vous avez choisi de consommer !");
                        //if (Consommable existe)
                        bool = heroActuel.useConsumable(inputParser.askConsumable());
                        break;
                }

            }
            while (!bool);
        }
        return 0;
    }

    /**
     * Verifie si les héros qui jouent sont toujours en vie, s'ils n'ont plus de Points de vie, les enlève de la liste.
     */
    public static void verifyHealth() {

        for (int i=0;i<heroes.size();i++){
            if (heroes.get(i).getLifePoints()==0){
                heroes.remove(i);
            }
        }

        for (int j=0;j<enemies.size();j++){
            if (enemies.get(j).getLifePoints()==0){
                enemies.remove(j);
            }
        }
    }

    /**
     * Genère une liste d'ennemis en prenant en compte les imprecisions en terme de nombres et les probabilite que le boss apparaisse.
     * @return
     */
    public static void generateCombat(){
        List<Enemy> enemyArrayList  = new ArrayList<>();

        int taille =  heroes.size()<=1 ? (int) probabilite(List.of(heroes.size(),heroes.size()+1),List.of(90,10)):(int) probabilite(List.of(heroes.size()-1,heroes.size(),heroes.size()+1),List.of(10,80,10));
        Random random = new Random();

        for(int i=0;i<taille;i++){
            enemyArrayList.add((Enemy) probabilite(List.of(new BasicEnemy(),new Boss()),List.of(100-probaBoss,probaBoss)));
        }

        enemies = enemyArrayList;
    }


    /**Créer une forme de probabilité: chaque élément (entier) de contenu est lié à une probabilité dans la liste probabilités, lorsque l'on tombe dans la range prévue, renvoie l'entier correspondant.*/
    public static Object probabilite(List<Object> contenu, List<Integer> probabilites) {

        Random random = new Random();
        int somme = 0;
        int borneDeFin;
        int randInt = random.nextInt(100);

        for(int i=0;i< contenu.size();i++){

            borneDeFin = somme+probabilites.get(i);

            if(randInt>=somme && randInt<=borneDeFin){
                return contenu.get(i);
            }
            somme=borneDeFin;
        }

//      Problème si on arrive jusqu'ici
        return -1;
    }

	public static int getPlayerTurn() {
		return playerTurn;
	}

	public static void setPlayerTurn(int playerTurn) {
		Game.playerTurn = playerTurn;
	}
}
