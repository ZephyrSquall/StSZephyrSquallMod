package zephyrsquallmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import zephyrsquallmod.ZephyrSquallMod;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class TailwindPower extends BasePower {
    public static final String POWER_ID = makeID("Tailwind");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static final int TAILWIND_REQUIRED = 10;

    public TailwindPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        ZephyrSquallMod.tailwindGained += amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + TAILWIND_REQUIRED + DESCRIPTIONS[1];
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && amount >= 10) {
            addToBot(new VFXAction(new WhirlwindEffect(new Color(0.6F, 1.0F, 1.0F, 1.0F), false)));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, TailwindPower.POWER_ID));
            addToBot(new SkipEnemiesTurnAction());
            ZephyrSquallMod.isTailwindExtraTurn = true;
            ZephyrSquallMod.isStartingTailwindExtraTurn = true;
        }
    }
}
