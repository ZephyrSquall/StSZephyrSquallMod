package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.unique.FlurryAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class Flurry extends BaseCard {
    public static final String ID = makeID(Flurry.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 3;

    public Flurry() {
        super(ID, info);

        setDamage(DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FlurryAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
    }
}
