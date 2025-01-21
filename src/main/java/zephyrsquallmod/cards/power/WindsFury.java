package zephyrsquallmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.WindsFuryPower;
import zephyrsquallmod.util.CardStats;

public class WindsFury extends BaseCard {
    public static final String ID = makeID(WindsFury.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 1;
    private static final int UPG_COST = 2;

    public WindsFury() {
        super(ID, info);

        setMagic(MAGIC);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WindsFuryPower(p, magicNumber)));
    }
}
