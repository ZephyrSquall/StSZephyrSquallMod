package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.ZephyrSquallMod;

public class SeizeAnOpeningAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ReprieveAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractMonster target;
    private final DamageInfo info;
    private final int multiplier;

    public SeizeAnOpeningAction(AbstractMonster target, DamageInfo info, int multiplier) {
        this.target = target;
        this.info = info;
        this.multiplier = multiplier;
    }

    public void update() {
        if (target != null && target.getIntentBaseDmg() < 0)
            for (int i = 0; i < multiplier; i++)
                addToTop(new DamageAction(target, info, ZephyrSquallMod.getRandomSlashEffect()));
        else
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        this.isDone = true;
    }
}