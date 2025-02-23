package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class CrashingPower extends BasePower {
    public static final String POWER_ID = makeID("Crashing");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public CrashingPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; i++)
            sb.append(DESCRIPTIONS[1]);
        this.description = sb.toString();
    }

    public void atStartOfTurn() {
        flash();
        addToBot(new LoseEnergyAction(this.amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, CrashingPower.POWER_ID));
    }
}
