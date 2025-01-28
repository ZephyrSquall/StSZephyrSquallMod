package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.unique.GoWithTheFlowAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class GoWithTheFlow extends BaseCard {
    public static final String ID = makeID(GoWithTheFlow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF_AND_ENEMY,
            3
    );

    private static final int BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int NO_BLOCK = 0;
    private static final int NO_MULTI = 0;
    public boolean wantsIntentInfo = false;
    public boolean targetIsAttacking = false;

    public GoWithTheFlow() {
        super(ID, info);

        setBlock(BLOCK);
        setBlock(MAGIC);
        setExhaust(true, false);
    }

    public void applyPowers() {
        this.wantsIntentInfo = false;
        this.targetIsAttacking = false;
        this.baseBlock = NO_BLOCK;
        this.baseMagicNumber = NO_MULTI;
        this.magicNumber = this.baseMagicNumber;

        super.applyPowers();
        this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster m) {
        this.wantsIntentInfo = true;
        m.applyPowers();
        if (targetIsAttacking) {
            if (this.baseBlock < 0)
                this.baseBlock = 0;
        }
        this.magicNumber = this.baseMagicNumber;
        super.calculateCardDamage(m);

        if (AbstractDungeon.player.hasRelic("Runic Dome")) {
            this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        } else {
            if (targetIsAttacking) {
                this.rawDescription = (this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[0];
            } else {
                this.rawDescription = (this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[1];
            }
        }
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GoWithTheFlowAction(targetIsAttacking, block, magicNumber));
    }
}
