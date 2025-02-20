package zephyrsquallmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class PressureSystemPower extends BasePower {
    public static final String POWER_ID = makeID("PressureSystem");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PressureSystemPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            addToBot(new ApplyPowerAction(m, owner, new HeadwindPower(m, amount), amount, true, AbstractGameAction.AttackEffect.NONE));

    }
}
