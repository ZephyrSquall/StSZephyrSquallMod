package zephyrsquallmod.patches.attackcounting.callonindividualattack;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.unique.FullHealthAdditionalDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// FullHealthAdditionalDamageAction calls the damage method twice, but each call is in mutually exclusive branches of an
// if statement, so patching both ensures the attack is counted once and can't cause the attack to be double-counted.
@SpirePatch2(
        clz = FullHealthAdditionalDamageAction.class,
        method = "update"
)
public class FullHealthAdditionalDamageActionPatch {

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
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
