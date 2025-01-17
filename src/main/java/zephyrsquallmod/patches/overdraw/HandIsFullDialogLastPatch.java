package zephyrsquallmod.patches.overdraw;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.finders.MatchFinderExprEditor;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.patches.finders.LastInOrderFinder;

import java.util.ArrayList;
import java.util.List;

@SpirePatch2(
        clz = DrawCardAction.class,
        method = "update"
)
public class HandIsFullDialogLastPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"amount"}
    )
    public static void checkOverdraw(int amount) {
        int cardsOverdrawn = amount + AbstractDungeon.player.hand.size() - BaseMod.MAX_HAND_SIZE;
        ZephyrSquallMod.onOverdraw(cardsOverdrawn);
    }

    private static class Locator extends SpireInsertLocator {
        // Matching on the last "createHandIsFullDialog" call isn't helpful, as the amount variable is changed
        // immediately before it to account for the difference in hand size and deck size. We need to capture the amount
        // variable before it is changed to accurately calculate how many cards were overdrawn. Fortunately, the last
        // call to check the size of a card group is right at the start of the same if statement body, before amount is
        // modified, so this is the perfect method call to locate on.
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "size");
            List<Matcher> expectedMatches = new ArrayList<>();
            MatchFinderExprEditor editor = new LastInOrderFinder(expectedMatches, finalMatcher);
            ctMethodToPatch.instrument(editor);
            if (!editor.didFindLocation()) {
                throw new PatchingException(ctMethodToPatch, "Location matching given description could not be found for patch");
            } else {
                return editor.getFoundLocations();
            }
        }
    }
}
