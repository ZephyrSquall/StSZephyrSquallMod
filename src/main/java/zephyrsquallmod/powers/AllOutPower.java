package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class AllOutPower extends BasePower {
    public static final String POWER_ID = makeID("AllOut");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public AllOutPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
