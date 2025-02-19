package zephyrsquallmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.TailwindPower;

import static zephyrsquallmod.ZephyrSquallMod.makeID;

public class BottledSquall extends BasePotion {
    public static final String ID = makeID(BottledSquall.class.getSimpleName());

    public BottledSquall() {
        super(ID, 10, PotionRarity.RARE, PotionSize.BOLT, PotionColor.WHITE);
        playerClass = ZephyrSquallCharacter.Enums.ZEPHYR_SQUALL;
        labOutlineColor = Color.CYAN.cpy();
    }

    @Override
    public void addAdditionalTips() {
        tips.add(new PowerTip(ZephyrSquallMod.keywords.get("Tailwind").PROPER_NAME, ZephyrSquallMod.keywords.get("Tailwind").DESCRIPTION));
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TailwindPower(AbstractDungeon.player, potency)));
        }
    }
}
