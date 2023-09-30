package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class MindMapPower extends BasePower {
    public static final String POWER_ID = makeID("MindMap");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public MindMapPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (amount == 1)
            this.description = DESCRIPTIONS[0];
        else
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
