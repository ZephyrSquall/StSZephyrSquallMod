package zephyrsquallmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.OneWithTheWindPower;
import zephyrsquallmod.powers.TailwindPower;
import zephyrsquallmod.util.CardStats;

public class OneWithTheWind extends BaseCard {
    public static final String ID = makeID(OneWithTheWind.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 3;

    public OneWithTheWind() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(OneWithTheWindPower.POWER_ID))
            addToBot(new ApplyPowerAction(p, p, new OneWithTheWindPower(p)));
        if (this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new TailwindPower(p, magicNumber)));
    }
}
