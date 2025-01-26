package zephyrsquallmod.patches.glyphofwarding;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// Reduce the damage done by monster attacks.
@SpirePatch2(
        clz = DamageInfo.class,
        method = "applyPowers"
)
public class ApplyPowersPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp", "target"}
    )
    public static void checkGlyphOfWarding(@ByRef float[] tmp, AbstractCreature target) {
        if (target == AbstractDungeon.player)
            tmp[0] = (int) ZephyrSquallMod.applyGlyphOfWardingDamageReduction(tmp[0]);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
