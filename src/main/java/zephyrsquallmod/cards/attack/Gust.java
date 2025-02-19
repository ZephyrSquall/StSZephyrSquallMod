package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.HeadwindPower;
import zephyrsquallmod.util.CardStats;

public class Gust extends BaseCard {
    public static final String ID = makeID(Gust.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Gust() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            addToBot(new ApplyPowerAction(mo, p, new HeadwindPower(mo, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
    }
}

