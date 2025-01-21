package zephyrsquallmod.patches.attackcounting.callonindividualattack;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.defect.SunderAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

@SpirePatch2(
        clz = SunderAction.class,
        method = "update"
)
public class SunderActionPatch {

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
