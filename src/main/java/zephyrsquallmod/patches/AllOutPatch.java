package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import zephyrsquallmod.powers.AllOutPower;

@SpirePatch2(
        clz = AbstractCreature.class,
        method = "addBlock"
)
public class AllOutPatch {

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void zeroOutBlockAmount(@ByRef int[] tmp) {
        if (AbstractDungeon.player.hasPower(AllOutPower.POWER_ID) && tmp[0] > 0) {
            tmp[0] = 0;
            AbstractDungeon.player.getPower(AllOutPower.POWER_ID).flash();
        }
    }

    // Inserting this code immediately before the first instance of accessing the "player" field of the
    // "AbstractDungeon" class puts this code immediately at the start of an if branch that is only entered if the
    // creature gaining Block is the player. By putting the patched code in this spot, no checks are needed to ensure
    // monster Block gain isn't getting zeroed out too.
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws PatchingException, CannotCompileException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
