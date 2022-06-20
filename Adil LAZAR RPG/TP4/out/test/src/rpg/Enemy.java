package rpg;

public class Enemy {
    protected int lifePoints;
    protected int weaponDamage;
    protected final int ratio = 2;

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void reduceLifePoints(int n) {
        lifePoints = lifePoints - n < 0 ? 0 : lifePoints - n;
    }


    public boolean attack(Hero hero) {
        int damage = weaponDamage;
        if(hero.isDefence()){
            damage = weaponDamage/ratio;
        }
        hero.reduceLifePoints(damage);
        return true;
    }
}