package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import zephyrsquallmod.patches.WindCutPatch;
import zephyrsquallmod.relics.Anemometer;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class HeadwindPower extends BasePower {
    public static final String POWER_ID = makeID("Headwind");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public HeadwindPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        WindCutPatch.headwindApplied.set(owner, WindCutPatch.headwindApplied.get(owner) + amount);
    }

    public void updateDescription() {
        if (AbstractDungeon.player.hasRelic(Anemometer.ID))
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        else
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfRound() {
        int targetAmount = AbstractDungeon.player.hasRelic(Anemometer.ID) ? (this.amount * 3) / 4 : this.amount / 2;
        if (targetAmount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, HeadwindPower.POWER_ID));
        } else {
            // reductionAmount may differ from targetAmount due to rounding, so it has to be calculated separately.
            int reductionAmount = this.amount - targetAmount;
            addToBot(new ReducePowerAction(this.owner, this.owner, HeadwindPower.POWER_ID, reductionAmount));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage - this.amount;
        return damage;
    }
}
