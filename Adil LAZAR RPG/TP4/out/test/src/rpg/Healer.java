package rpg;

import java.util.List;

public class Healer extends SpellCaster {
    public List<Hero> heroes;
    public int aim;

    public boolean attack() {
        return attack(new Enemy());
    }

    @Override
    public boolean attack(Enemy enemy) {
        if(manaPoints>diminuerMana){
            manaPoints = manaPoints - diminuerMana;
            heroes.get(aim).addLifePoints(weaponDamage);
            return true;
        }
        else{
            System.out.println("Vous n'avez pas assez de mana pour soigner, Veuillez réessayer");
            return false;
        }
    }

    public boolean healattack(List<Hero> heroes,int aimedHero) {
        this.heroes = heroes;
        this.aim = aimedHero;
        return attack();
    }
}
