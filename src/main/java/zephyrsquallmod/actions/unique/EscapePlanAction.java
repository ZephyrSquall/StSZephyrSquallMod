package zephyrsquallmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class EscapePlanAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractMonster target;
    private final int block;

    public EscapePlanAction(AbstractMonster target, int block) {
        this.target = target;
        this.block = block;
    }

    public void update() {
        if (this.target.getIntentBaseDmg() >= 0) {
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
        this.isDone = true;
    }
}