package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import zephyrsquallmod.ZephyrSquallMod;

public class HarryAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final int block;
    private final DamageInfo info;

    public HarryAction(AbstractPlayer p, AbstractMonster m, int block, DamageInfo info ) {
        this.p = p;
        this.m = m;
        this.block = block;
        this.info = info;
    }

    public void update() {
        for (int i = 0; i < 2; i++) {
            addToTop(new DamageAction(m, info, ZephyrSquallMod.getRandomSlashEffect()));
        }
        if (ZephyrSquallMod.timesAttackedThisTurn >= 4) {
            addToTop(new GainBlockAction(p, p, block));
        }
        this.isDone = true;
    }
}
