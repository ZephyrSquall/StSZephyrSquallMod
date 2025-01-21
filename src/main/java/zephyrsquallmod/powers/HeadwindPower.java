package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class HeadwindPower extends BasePower {
    public static final String POWER_ID = makeID("Headwind");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public HeadwindPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        if (amount == 1)
            this.description = DESCRIPTIONS[0];
        else
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && AbstractDungeon.player.hasPower(TailwindPower.POWER_ID))
            return damage - (this.amount * AbstractDungeon.player.getPower(TailwindPower.POWER_ID).amount);
        return damage;
    }
}
