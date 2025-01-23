package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.unique.EscapePlanAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class EscapePlan extends BaseCard {
    public static final String ID = makeID(EscapePlan.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            2
    );

    private static final int BLOCK = 0;
    private static final int NO_BLOCK = 0;

    public EscapePlan() {
        super(ID, info);

        setBlock(BLOCK);
    }

    public void applyPowers() {
        this.baseBlock = NO_BLOCK;
        super.applyPowers();
        this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        if (mo.getIntentBaseDmg() >= 0) {
            int blockFromTarget = mo.getIntentDmg();
            if (!this.upgraded)
                blockFromTarget /= 2;
            if (blockFromTarget < 0)
                blockFromTarget = 0;
            this.baseBlock = blockFromTarget;
            this.rawDescription = (this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = (this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        if (AbstractDungeon.player.hasRelic("Runic Dome"))
            this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        super.calculateCardDamage(mo);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EscapePlanAction(m, block));
    }
}
