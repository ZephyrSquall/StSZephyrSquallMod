package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class Examine extends BaseCard {
    public static final String ID = makeID(Examine.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Examine() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        this.exhaust = true;
        this.cardsToPreview = new Bookmark();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Bookmark(), this.magicNumber));
    }
}
