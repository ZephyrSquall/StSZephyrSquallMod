package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.RipostePower;
import zephyrsquallmod.util.CardStats;

public class Riposte extends BaseCard {
    public static final String ID = makeID(Riposte.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 5;

    public Riposte() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = (ZephyrSquallMod.isWellRead()) ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RipostePower(p)));
        if (ZephyrSquallMod.isWellRead())
            addToBot(new GainBlockAction(p, p, block));
    }
}
