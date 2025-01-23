package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.TailwindPower;
import zephyrsquallmod.util.CardStats;

public class WhipUp extends BaseCard {
    public static final String ID = makeID(WhipUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public WhipUp() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;
        if (ZephyrSquallMod.isTailwindExtraTurn) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return canUse;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TailwindPower(p, magicNumber)));
    }
}
