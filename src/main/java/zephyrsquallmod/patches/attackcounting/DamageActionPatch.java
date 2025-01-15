package zephyrsquallmod.patches.attackcounting;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;

// There are several other actions in the base game that deal damage directly without calling this DamageAction. All of
// these actions are handled separately with different patches (except for DarkOrbEvokeAction, InstantKillAction,
// LoseHPAction, PoisonLoseHpAction, as these actions can never deal attack damage i.e. their damage type is always
// THORNS or HP_LOSS). DamageRandomEnemyAction calls DamageAction after determining the random target, so patching just
// DamageAction to count attacks accounts for both DamageAction and DamageRandomEnemyAction. I hope this is something
// only the base game does due to technical debt, as I don't believe I can account for other mods causing attacks to
// deal damage through a custom action that calls the damage method directly (patching the damage method directly causes
// attacks that hit all enemies to be double-counted).
@SpirePatch2(
        clz = DamageAction.class,
        method = "update"
)
public class DamageActionPatch {

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
