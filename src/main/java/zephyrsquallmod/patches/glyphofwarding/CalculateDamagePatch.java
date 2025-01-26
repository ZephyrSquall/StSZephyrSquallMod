package zephyrsquallmod.patches.glyphofwarding;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

import java.util.ArrayList;

// Reduce the damage done by monster attacks.
@SpirePatch2(
        clz = AbstractMonster.class,
        method = "calculateDamage"
)
public class CalculateDamagePatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void checkGlyphOfWarding(@ByRef float[] tmp) {
        tmp[0] = ZephyrSquallMod.applyGlyphOfWardingDamageReduction(tmp[0]);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
