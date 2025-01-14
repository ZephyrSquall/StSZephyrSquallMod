package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

@SpirePatch2(
        clz = PummelDamageAction.class,
        method = "update"
)
public class PummelDamageActionPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"info"}
    )
    public static void countAttack(DamageInfo info) {
        if (info.owner == AbstractDungeon.player && info.type == DamageInfo.DamageType.NORMAL) {
            ZephyrSquallMod.timesAttackedThisTurn++;
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "damage");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
