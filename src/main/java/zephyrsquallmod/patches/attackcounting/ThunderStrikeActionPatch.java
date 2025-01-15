package zephyrsquallmod.patches.attackcounting;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.defect.ThunderStrikeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// ThunderStrikeAction calls itself recursively for however many attacks need to be made, so only add to the count once
// per call.
@SpirePatch2(
        clz = ThunderStrikeAction.class,
        method = "update"
)
public class ThunderStrikeActionPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"info"}
    )
    public static void onIndividualAttack(DamageInfo info) {
        ZephyrSquallMod.onIndividualAttack(info.owner, info.type);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "damage");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
