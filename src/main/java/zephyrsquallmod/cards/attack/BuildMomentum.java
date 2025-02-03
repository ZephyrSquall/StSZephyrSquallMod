package zephyrsquallmod.cards.attack;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class BuildMomentum extends BaseCard {
    public static final String ID = makeID(BuildMomentum.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public BuildMomentum() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = (!ZephyrSquallMod.hasLostHPLastTurn) ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), ZephyrSquallMod.getRandomSlashEffect()));
        if (!ZephyrSquallMod.hasLostHPLastTurn)
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), ZephyrSquallMod.getRandomSlashEffect()));
    }
}
