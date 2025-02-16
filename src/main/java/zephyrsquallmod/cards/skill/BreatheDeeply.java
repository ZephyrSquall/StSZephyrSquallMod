package zephyrsquallmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.cards.attack.WindBlast;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.BreatheDeeplyPower;
import zephyrsquallmod.powers.BreatheDeeplyUpgradePower;
import zephyrsquallmod.util.CardStats;

public class BreatheDeeply extends BaseCard {
    public static final String ID = makeID(BreatheDeeply.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ZephyrSquallCharacter.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    public BreatheDeeply() {
        super(ID, info);

        this.cardsToPreview = new WindBlast();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new BreatheDeeplyUpgradePower(p)));
        else
            addToBot(new ApplyPowerAction(p, p, new BreatheDeeplyPower(p)));
    }
}
