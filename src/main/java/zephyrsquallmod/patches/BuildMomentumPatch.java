package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

@SpirePatch2(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class BuildMomentumPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageReceivedThisTurn"}
    )
    public static void RenderRecordedCards(int damageReceivedThisTurn) {
        // getNextAction is not called on the very first turn, so this won't change whether Build Momentum gets its
        // additional effect on the first turn.
        ZephyrSquallMod.hasLostHPLastTurn = damageReceivedThisTurn > 0;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "damageReceivedThisTurn");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
