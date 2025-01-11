package zephyrsquallmod.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Arrays;

public class FlurryAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int damage;
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final DamageInfo.DamageType damageTypeForTurn;
    private final int energyOnUse;
    private final boolean upgraded;
    private static final ArrayList<AttackEffect> slashEffects = new ArrayList<>( Arrays.asList(AttackEffect.SLASH_HORIZONTAL, AttackEffect.SLASH_VERTICAL, AttackEffect.SLASH_DIAGONAL));


    public FlurryAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (this.upgraded) {
            effect *= 3;
        } else {
            effect *= 2;
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                int effectIndex = MathUtils.random(slashEffects.size() - 1);
                AttackEffect slashEffect = slashEffects.get(effectIndex);
                addToBot(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), slashEffect));
            }
            if (!this.freeToPlayOnce)
                this.p.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}