package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.actions.unique.BrowseTheLibraryAction;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class BrowseTheLibrary extends BaseCard {
    public static final String ID = makeID(BrowseTheLibrary.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public BrowseTheLibrary() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        this.cardsToPreview = new Book();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BrowseTheLibraryAction(magicNumber));
    }
}
