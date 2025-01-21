package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cardmodifiers.StreamlineModifier;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.util.CardStats;

import static basemod.helpers.CardModifierManager.addModifier;

public class WindBlast extends BaseCard {
    public static final String ID = makeID(WindBlast.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 25;
    private static final int UPG_DAMAGE = 5;

    public WindBlast() {
        this(false);
    }

    public WindBlast(boolean streamlineOnCreation) {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setEthereal(true);
        setExhaust(true);

        if (streamlineOnCreation) {
            addModifier(this, new StreamlineModifier());
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}
