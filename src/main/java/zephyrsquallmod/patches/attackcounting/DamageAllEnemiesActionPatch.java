package zephyrsquallmod.patches.attackcounting;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// For DamageAllEnemiesAction, the damage method is inside a for loop, so matching the inserted code on the damage
// method will double-count attacks for each additional enemy on the field (or not count attacks at all if there are no
// enemies, e.g. Awakened One between phases). Thus, the patch is inserted on the powers method, as this only occurs
// once (in the initialization of a foreach loop) on a successful attack. Note that the size method is also outside any
// loops, but this was not chosen because the same method also appears elsewhere in the class and so would double-count
// attacks and possibly count unsuccessful attacks.
@SpirePatch2(
        clz = DamageAllEnemiesAction.class,
        method = "update"
)
public class DamageAllEnemiesActionPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageType"}
    )
    public static void countAttack(DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            ZephyrSquallMod.timesAttackedThisTurn++;
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
