package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class WindsFuryPower extends BasePower {
    public static final String POWER_ID = makeID("WindsFury");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public WindsFuryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}
