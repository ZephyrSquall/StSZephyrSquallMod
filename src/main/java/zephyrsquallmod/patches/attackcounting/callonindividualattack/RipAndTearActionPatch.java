package zephyrsquallmod.patches.attackcounting.callonindividualattack;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.unique.RipAndTearAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// RipAndTearAction recursively calls itself a second time, so only add to the count once per call.
@SpirePatch2(
        clz = RipAndTearAction.class,
        method = "update"
)
public class RipAndTearActionPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"target", "info"}
    )
    public static void onIndividualAttack(AbstractCreature target, DamageInfo info) {
        ZephyrSquallMod.onIndividualAttack(info.owner, target, info.type, new int[]{info.base});
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "damage");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
