package zephyrsquallmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;

@SpirePatch2(
        clz = AbstractCreature.class,
        method = SpirePatch.CLASS
)
public class WindCutPatch {
    public static SpireField<Integer> headwindApplied = new SpireField<>(() -> 0);
}
