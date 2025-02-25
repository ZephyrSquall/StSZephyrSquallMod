package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.actions.common.RecordCardsInHandAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.DrawDownPower;
import zephyrsquallmod.util.CardStats;

public class SecretPassage extends BaseCard {
    public static final String ID = makeID(SecretPassage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 2;
    private static final int UPG_COST = 2;

    public SecretPassage() {
        super(ID, info);

        setMagic(MAGIC);
        setCostUpgrade(UPG_COST);
        this.cardsToPreview = new Book();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = (ZephyrSquallMod.isWellRead()) ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DrawDownPower(p, 5)));
        if (ZephyrSquallMod.isWellRead()) {
            addToBot(new RecordCardsInHandAction(2, true, true));
        }
    }
}
