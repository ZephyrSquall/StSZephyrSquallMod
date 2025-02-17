package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class Assessment extends BaseCard {
    public static final String ID = makeID(Assessment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int MAGIC = 0;

    public Assessment() {
        super(ID, info);

        setMagic(MAGIC);
    }

    public void applyPowers() {
        this.baseMagicNumber = ZephyrSquallMod.timesAttackedThisTurn;
        this.magicNumber = this.baseMagicNumber;
        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }

    public void onMoveToDiscard() {
        if (this.upgraded) {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new DrawCardAction(magicNumber + 2));
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            addToBot(new DrawCardAction(magicNumber + 1));
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        initializeDescription();
    }
}
