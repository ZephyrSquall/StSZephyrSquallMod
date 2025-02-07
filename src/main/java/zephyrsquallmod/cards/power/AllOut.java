package zephyrsquallmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.AllOutPower;
import zephyrsquallmod.powers.TailwindPower;
import zephyrsquallmod.util.CardStats;

public class AllOut extends BaseCard {
    public static final String ID = makeID(AllOut.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int UPG_COST = 0;

    public AllOut() {
        super(ID, info);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.currentBlock > 0) {
            addToBot(new RemoveAllBlockAction(p, p));
            addToBot(new ApplyPowerAction(p, p, new TailwindPower(p, p.currentBlock)));
        }
        if (!p.hasPower(AllOutPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new AllOutPower(p)));
        }
    }
}
