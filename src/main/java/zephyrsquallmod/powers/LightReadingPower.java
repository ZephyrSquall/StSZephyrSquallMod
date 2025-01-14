package zephyrsquallmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class LightReadingPower extends BasePower {
    public static final String POWER_ID = makeID("LightReading");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public LightReadingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        int cardThreshold = BaseMod.MAX_HAND_SIZE - amount;
        if (cardThreshold <= 0) {
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + cardThreshold + DESCRIPTIONS[1];
        }
    }
}
