package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class QuickThinking extends BaseCard {
    public static final String ID = makeID(QuickThinking.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public QuickThinking() {
        super(ID, info);

        setBlock(BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    public void triggerWhenDrawn() {
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }
}
