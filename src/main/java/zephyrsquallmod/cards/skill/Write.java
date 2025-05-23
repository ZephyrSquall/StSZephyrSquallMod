package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.common.RecordCardsInHandAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class Write extends BaseCard {
    public static final String ID = makeID(Write.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Write() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        this.cardsToPreview = new Book();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RecordCardsInHandAction(this.magicNumber, true, true));
    }
}
