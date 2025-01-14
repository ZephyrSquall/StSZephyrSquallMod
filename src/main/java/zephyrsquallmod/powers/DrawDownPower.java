package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

// The localization files for the base game imply there used to be a power called Draw Down which reduced the number of
// cards you drew for the next turn only, but this power doesn't actually exist in the game's files. This power is
// exactly what I want for Zephyr's Secret Passage card, so I implement it exactly how I believe it would have worked in
// the original game here.
public class DrawDownPower extends BasePower {
    public static final String POWER_ID = makeID("DrawDown");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public DrawDownPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        AbstractDungeon.player.gameHandSize -= amount;
    }

    public void onRemove() {
        AbstractDungeon.player.gameHandSize += this.amount;
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        if (reduceAmount > this.amount) {
            reduceAmount = this.amount;
        }
        this.amount -= reduceAmount;
        AbstractDungeon.player.gameHandSize += reduceAmount;
        if (this.amount <= 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
