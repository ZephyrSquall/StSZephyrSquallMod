package zephyrsquallmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.FlashOfInspirationAction;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class FlashOfInspiration extends BaseCard {
    public static final String ID = makeID(FlashOfInspiration.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            -2
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public FlashOfInspiration() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void triggerWhenDrawn() {
        addToBot(new FlashOfInspirationAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
}