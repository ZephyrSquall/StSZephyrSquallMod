package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import zephyrsquallmod.cards.attack.WindBlast;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class BreatheDeeplyUpgradePower extends BasePower {
    public static final String POWER_ID = makeID("BreatheDeeplyUpgrade");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BreatheDeeplyUpgradePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurn() {
        addToBot(new MakeTempCardInHandAction(new WindBlast(true), this.amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, BreatheDeeplyUpgradePower.POWER_ID));
    }
}
