package rpg;

public abstract class SpellCaster extends Hero {


    protected int manaPoints;
    protected int diminuerMana;

    public SpellCaster() {
//        this.weaponDamage = 12;
        this.manaPoints = 90;
    }

    public int getMana() {
        return manaPoints;
    }

    public abstract boolean attack(Enemy enemy);


    @Override
    public void increaseArrowOrMana() {
        manaPoints+=20;
    }
}
