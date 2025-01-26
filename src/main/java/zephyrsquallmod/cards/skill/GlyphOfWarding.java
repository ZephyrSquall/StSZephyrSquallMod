package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.util.CardStats;

public class GlyphOfWarding extends BaseCard {
    public static final String ID = makeID(GlyphOfWarding.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            -2
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public GlyphOfWarding() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        this.cardsToPreview = new Book();
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
}
