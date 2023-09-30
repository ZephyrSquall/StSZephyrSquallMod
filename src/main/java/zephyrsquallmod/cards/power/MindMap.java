package zephyrsquallmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.MindMapPower;
import zephyrsquallmod.util.CardStats;

public class MindMap extends BaseCard {
    public static final String ID = makeID(MindMap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 1;
    private static final int UPG_COST = 2;

    public MindMap() {
        super(ID, info);

        setMagic(MAGIC);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MindMapPower(p, magicNumber)));
    }
}
