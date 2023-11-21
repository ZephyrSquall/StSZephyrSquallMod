package zephyrsquallmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import zephyrsquallmod.ZephyrSquallMod;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class StreamlineRandomCardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("StreamlineAction"));
    public static final String[] TEXT = uiStrings.TEXT;

    public StreamlineRandomCardAction() {}

    public void update() {
        AbstractCard[] streamlineableCards = AbstractDungeon.player.hand.group.stream()
                .filter(ZephyrSquallMod.canBeStreamlined)
                .toArray(AbstractCard[]::new);
        if (streamlineableCards.length == 0) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[1], true));
        } else {
            AbstractCard cardToStreamline = streamlineableCards[AbstractDungeon.cardRandomRng.random(0, streamlineableCards.length - 1)];
            addToTop(new StreamlineSpecificCardAction(cardToStreamline));
        }
        this.isDone = true;
    }
}
