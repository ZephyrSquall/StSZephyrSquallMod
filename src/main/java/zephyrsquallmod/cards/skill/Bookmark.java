package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.util.CardStats;

public class Bookmark extends BaseCard {
    public static final String ID = makeID(Bookmark.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            0
    );

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public Bookmark() {
        super(ID, info);

        this.setMagic(MAGIC, UPG_MAGIC);
        this.selfRetain = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));
    }
}
