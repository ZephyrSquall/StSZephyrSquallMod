package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.HeadwindPower;
import zephyrsquallmod.powers.TailwindPower;
import zephyrsquallmod.util.CardStats;

public class BlowAway extends BaseCard {
    public static final String ID = makeID(BlowAway.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int MAGIC = 1;

    public BlowAway() {
        super(ID, info);

        setMagic(MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new HeadwindPower(p)));
        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new TailwindPower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.target = CardTarget.SELF_AND_ENEMY;
    }
}
