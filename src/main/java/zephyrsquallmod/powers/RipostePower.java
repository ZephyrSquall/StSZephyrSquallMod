package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class RipostePower extends BasePower {
    public static final String POWER_ID = makeID("Riposte");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RipostePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        if (amount == 1)
            this.description = DESCRIPTIONS[0];
        else
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.owner != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            int blockedDamage = info.output - damageAmount;
            if (blockedDamage > 0) {
                flash();
                addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, blockedDamage * amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
        }
        return damageAmount;
    }

    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, RipostePower.POWER_ID));
    }
}
