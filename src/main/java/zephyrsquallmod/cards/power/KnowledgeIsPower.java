package zephyrsquallmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.KnowledgeIsPowerPower;
import zephyrsquallmod.util.CardStats;

public class KnowledgeIsPower extends BaseCard {
    public static final String ID = makeID(KnowledgeIsPower.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = 5;
    private static final int UPG_COST = 2;

    public KnowledgeIsPower() {
        super(ID, info);

        setMagic(MAGIC, UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new KnowledgeIsPowerPower(p, magicNumber)));
    }
}
