package rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Hero {
    final int basicLifePoints = 70 ;
    boolean defence;
    protected int lifePoints = basicLifePoints;

    private int armor;

    protected int weaponDamage = 200;
    int maxLife = 100;
    final int addConsumableEffect = 20;
    int bonusConsumable;


    private List<Potion> potions = new ArrayList();
    private List<Food> lembas = new ArrayList();




    public List<Potion> getPotions() {
        return potions;
    }

    public List<Food> getFood() {
        return lembas;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = Math.min(lifePoints, maxLife);
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public Hero() {
        for(int i=0;i<3;i++){
            potions.add(new Potion());
            lembas.add(new Food());
        }

    }

    public void reduceLifePoints(int n){
        lifePoints = Math.max(lifePoints - n, 0);
    }

    public void addLifePoints(int n){
        lifePoints = Math.min(lifePoints + n, maxLife);
    }

    public abstract boolean attack(Enemy enemy);

    public void defend(){
        setDefence(true);
    }

    public boolean useConsumable(String choix){
        if(!potions.isEmpty() && choix.equals("A")){
            setLifePoints(lifePoints+potions.get(0).getValue()+bonusConsumable);
            potions.remove(0);
            return true;
        }
        else if (!lembas.isEmpty() && choix.equals("B")){

            setLifePoints(lifePoints+lembas.get(0).getValue()+bonusConsumable);
            lembas.remove(0);
            return true;
        }
        else{
            System.out.println("Vous n'avez pas suffisamment du consommable pour l'action souhaitée, veuillez réessayer");
            return false;
        }
    }


    public void increaseArmor() {
        armor+=20;
    }

    public void increaseDamage() {
        weaponDamage+=20;
    }


    public abstract void increaseArrowOrMana();

    public void increaseConsumableNumber() {
        potions.add(new Potion());
        lembas.add(new Food());
    }

    public void increaseConsumableEffect() {
        bonusConsumable+=addConsumableEffect;
    }

    public void resetLifePoints() {
        lifePoints = basicLifePoints;
    }

    public void resetDefence() { defence = false; }

    public void setDefence(boolean defence) {
        this.defence = defence;
    }

    public boolean isDefence() {
        return defence;
    }
}
