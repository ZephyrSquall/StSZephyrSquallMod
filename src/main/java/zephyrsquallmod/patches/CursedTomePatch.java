package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import zephyrsquallmod.relics.CursedTome;

@SpirePatch2(
        clz = DrawCardAction.class,
        method = "update"
)
public class CursedTomePatch {
    @SpirePrefixPatch
    public static void skipDraws(DrawCardAction __instance, boolean ___clearDrawHistory) {
        // The DrawCardAction sometimes calls itself again, e.g. to split up a draw before and after shuffling the
        // discard pile into the draw pile if the player tried to draw more cards than is currently in the draw pile.
        // The clearDrawHistory variable is true when a draw is truly a new set of card draws and not simply a recursive
        // call. So the draws should only be reduced if clearDrawHistory is true, otherwise the draw reduction may be
        // applied to the same draw twice. This has to be the very first thing that occurs in the update method, because
        // for some reason clearDrawHistory is immediately set to false after it is checked, which is the first thing
        // that happens in the unpatched update method.
        if (___clearDrawHistory && AbstractDungeon.player.hasRelic(CursedTome.ID) && !AbstractDungeon.player.hasPower("No Draw")) {
            AbstractRelic cursedTome = AbstractDungeon.player.getRelic(CursedTome.ID);
            boolean hasDrawSkipped = false;
            int originalAmount = __instance.amount;
            for (int i = 0; i < originalAmount; i++) {
                cursedTome.counter++;
                if (cursedTome.counter == CursedTome.DRAW_SKIP_FREQUENCY) {
                    hasDrawSkipped = true;
                    __instance.amount--;
                    cursedTome.counter = 0;
                }
            }
            if (hasDrawSkipped) {
                cursedTome.flash();
            }
        }
    }
}
