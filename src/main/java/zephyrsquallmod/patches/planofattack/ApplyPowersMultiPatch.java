package zephyrsquallmod.patches.planofattack;

import com.evacipated.cardcrawl.modthespire.finders.MatchFinderExprEditor;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.patches.finders.LastInOrderFinder;

import java.util.ArrayList;
import java.util.List;

// Increase the displayed damage numbers on multiple-target Attack cards.
// (As far as I can tell, it's actually the single-target damage number that gets displayed on multiple-target Attack
// cards, but I'm patching this to be consistent with all the other PlanOfAttack patches just in case.)
@SpirePatch2(
        clz = AbstractCard.class,
        method = "applyPowers"
)
public class ApplyPowersMultiPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp", "i", "damageTypeForTurn", "cardID"}
    )
    // Arrays are inherently passed by reference, so @ByRef isn't needed here to change tmp in calculateCardDamage().
    public static void checkPlanOfAttack(float[] tmp, int i, DamageInfo.DamageType damageTypeForTurn, String cardID) {
        tmp[i] = ZephyrSquallMod.addPlanOfAttackDamage(tmp[i], damageTypeForTurn, cardID);
    }

    private static class Locator extends SpireInsertLocator {
        // The relics on the player are checked twice in applyPowers() (as well as in calculateCardDamage()). The first
        // check is for single target damage and the second one is for multi-target damage. Hence, this patch wants to
        // match only on the last relics check. There is no provided matcher that matches only on the last match, so I
        // created a custom matcher that does so.
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            List<Matcher> expectedMatches = new ArrayList<>();
            MatchFinderExprEditor editor = new LastInOrderFinder(expectedMatches, finalMatcher);
            ctMethodToPatch.instrument(editor);
            if (!editor.didFindLocation()) {
                throw new PatchingException(ctMethodToPatch, "Location matching given description could not be found for patch");
            } else {
                return editor.getFoundLocations();
            }
        }
    }
}
