package zephyrsquallmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class FocusAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private final float startingDuration;
    private final int damage;

    public FocusAction(AbstractCreature target, AbstractCreature source, int damage) {
        setValues(target, source);
        this.damage = damage;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            if (!AbstractDungeon.player.hand.isEmpty())
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            int count = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            if (count > 0) {
                addToTop(new DamageAction(this.target, new DamageInfo(this.source, damage * count, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
                //Is there a better way to discard the chosen cards? I couldn't figure out any way to do it with the DiscardAction or DiscardSpecificCardAction actions because there's no way to get back the number of discarded cards from those methods.
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(false);
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            this.isDone = true;
        }
        AbstractDungeon.player.hand.applyPowers();
        tickDuration();
    }
 }
