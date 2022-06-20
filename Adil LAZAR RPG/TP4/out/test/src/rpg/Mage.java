package rpg;

public class Mage extends SpellCaster {
    public Mage(int manaPoints) {
        this.manaPoints= manaPoints;
    }


    @Override
    public boolean attack(Enemy enemy) {
        if(manaPoints>diminuerMana){
            manaPoints = manaPoints - diminuerMana;
            enemy.reduceLifePoints(weaponDamage);
            return true;
        }
        else{
            System.out.println("Vous n'avez pas assez de mana pour attaquer, Veuillez réessayer");
            return false;
        }
    }
}
