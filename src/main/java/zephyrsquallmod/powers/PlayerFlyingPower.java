package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class PlayerFlyingPower extends BasePower {
    public static final String POWER_ID = makeID("PlayerFlying");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PlayerFlyingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription() {
        if (amount == 1)
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage / 2.0F;
        return damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            flash();
            addToBot(new ReducePowerAction(this.owner, this.owner, PlayerFlyingPower.POWER_ID, 1));
        }
        return damageAmount;
    }
}
