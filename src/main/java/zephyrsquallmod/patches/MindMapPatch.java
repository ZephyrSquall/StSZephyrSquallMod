package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

// Drawing cards is also done by the FastDrawCardAction, ScrapeAction, and PathVictoryAction (judging by them being
// affected by the No Draw power). However, all of these actions have no usages by any cards, suggesting they're all
// depreciated actions. I'm unsure about FastCardDrawAction, but ScrapeAction was used by Scrape before it was updated
// to use DrawCardAction with a follow-up action, and PathVictoryAction was used by Pressure Points before it was
// renamed from Path to Victory when it had an entirely different effect "Draw a card and reduce its cost to 0 this
// turn." There are also many other actions that put cards into the hand which can trigger the "My Hand is Full!"
// thought bubble, but these aren't technically "drawing" cards (e.g. Secret Weapon has you search your draw pile for an
// attack and immediately put it into your hand, without using the word "draw"). As such, DrawCardAction appears to be
// the only action that needs patching, but I'll keep these other actions in mind as I'm playtesting if any unusual
// interactions come up.
@SpirePatch2(
        clz = DrawCardAction.class,
        method = "update"
)
public class MindMapPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void checkMindMap() {
        if (!ZephyrSquallMod.hasAttemptedDrawWithFullHandThisTurn) {
            ZephyrSquallMod.hasAttemptedDrawWithFullHandThisTurn = true;
            if (AbstractDungeon.player.hasPower(makeID("MindMap"))) {
                AbstractPower mindMapPower = AbstractDungeon.player.getPower(makeID("MindMap"));
                mindMapPower.flash();
                for (int i = 0; i < mindMapPower.amount; i++) {
                    AbstractDungeon.actionManager.addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng),false));
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "createHandIsFullDialog");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
