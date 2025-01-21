package zephyrsquallmod.patches.attackcounting.callonindividualattack;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

@SpirePatch2(
        clz = DamageAllButOneEnemyAction.class,
        method = "update"
)
public class DamageAllButOneEnemyActionPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"source", "damageType", "damage"}
    )
    public static void onIndividualAttack(AbstractCreature source, DamageInfo.DamageType damageType, int[] damage) {
        ZephyrSquallMod.onIndividualAttack(source, null, damageType, damage);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
