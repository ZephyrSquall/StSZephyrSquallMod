package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// DamageRandomEnemyAction calls DamageAction after determining the random target, so patching just DamageAction to
// count attacks accounts for both DamageAction and DamageRandomEnemyAction. The only other actions that damage enemies with attacks
// are DamageAllEnemiesAction and PummelDamageAction, so these are handled with their own separate patches.
@SpirePatch2(
        clz = DamageAction.class,
        method = "update"
)
public class DamageActionPatch {

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
