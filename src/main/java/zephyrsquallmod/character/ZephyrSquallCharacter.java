package zephyrsquallmod.character;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import zephyrsquallmod.ZephyrSquallMod;
import zephyrsquallmod.cards.attack.Focus;
import zephyrsquallmod.cards.attack.Strike;
import zephyrsquallmod.cards.skill.Defend;
import zephyrsquallmod.cards.skill.Study;
import zephyrsquallmod.relics.FieldJournal;

import java.util.ArrayList;

import static zephyrsquallmod.ZephyrSquallMod.*;

public class ZephyrSquallCharacter extends CustomPlayer {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 73;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    //Strings
    private static final String ID = makeID("ZephyrSquall"); //This should match whatever you have in the CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    //Image file paths
    private static final String COMBAT = characterPath("combat.png");
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.
    private static final float drawYReduction = 60.0F * Settings.scale;
    private static final String[] ORB_TEXTURES = {
            characterPath("orb/layer1.png"),
            characterPath("orb/layer2.png"),
            characterPath("orb/layer3.png"),
            characterPath("orb/layer4.png"),
            characterPath("orb/layer5.png"),
            characterPath("orb/base.png"),
            characterPath("orb/layer1d.png"),
            characterPath("orb/layer2d.png"),
            characterPath("orb/layer3d.png"),
            characterPath("orb/layer4d.png"),
            characterPath("orb/layer5d.png")};
    private static final String ORB_VFX_TEXTURE = characterPath("orb/vfx.png");
    private static final float[] ORB_LAYER_SPEEDS = new float[] {30.0F, 20.0F, 40.0F, 15.0F, 0.0F};

    public static class Enums {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass ZEPHYR_SQUALL;
        @SpireEnum(name = "ZEPHYR_SQUALL_CYAN_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "ZEPHYR_SQUALL_CYAN_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public ZephyrSquallCharacter() {
        super(NAMES[0], Enums.ZEPHYR_SQUALL, new CustomEnergyOrb(ORB_TEXTURES, ORB_VFX_TEXTURE, ORB_LAYER_SPEEDS), null, null);

        // Zephyr has a huge sprite, help it fit into the screen and take up more of the floor space in the room by
        // moving it down slightly.
        drawY = drawY - drawYReduction;

        initializeClass(COMBAT,
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                10.0F, 10.0F, 450.0F, 350.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Study.ID);
        retVal.add(Focus.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(FieldJournal.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Focus();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                ZephyrSquallMod.getRandomSlashEffect(),
                ZephyrSquallMod.getRandomSlashEffect(),
                ZephyrSquallMod.getRandomSlashEffect(),
                ZephyrSquallMod.getRandomSlashEffect(),
                ZephyrSquallMod.getRandomSlashEffect(),
                ZephyrSquallMod.getRandomSlashEffect(),
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
        };
    }

    private final Color cardRenderColor = Color.CYAN.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.CYAN.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.CYAN.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("EVENT_TOME", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "EVENT_TOME";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }
    @Override
    public String getVampireText() {
        return TEXT[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new ZephyrSquallCharacter();
    }

    @Override
    public void movePosition(float x, float y) {
        // Usually when movePositions is called, it completely overwrites the player's drawY variable. To keep Zephyr's
        // y position slightly lowered, it must be lowered again every time this method is called.
        float newY = y - drawYReduction;
        super.movePosition(x, newY);
    }
}
