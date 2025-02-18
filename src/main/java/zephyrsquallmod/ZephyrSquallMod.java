package zephyrsquallmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import zephyrsquallmod.cards.BaseCard;
import zephyrsquallmod.cards.attack.PlanOfAttack;
import zephyrsquallmod.cards.skill.Book;
import zephyrsquallmod.cards.skill.GlyphOfWarding;
import zephyrsquallmod.character.ZephyrSquallCharacter;
import zephyrsquallmod.powers.LightReadingPower;
import zephyrsquallmod.powers.MaelstromPower;
import zephyrsquallmod.powers.OneWithTheWindPower;
import zephyrsquallmod.relics.BaseRelic;
import zephyrsquallmod.util.GeneralUtils;
import zephyrsquallmod.util.KeywordInfo;
import zephyrsquallmod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

@SpireInitializer
public class ZephyrSquallMod implements
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber,
        OnPlayerTurnStartSubscriber,
        OnPlayerLoseBlockSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "zephyrsquallmod";

    private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
    private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

    private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
    private static final String BG_POWER = characterPath("cardback/bg_power.png");
    private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/small_orb.png");
    private static final Color cardColor = new Color(108f/255f, 238f/255f, 245f/255f, 1f);

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new ZephyrSquallMod();

        BaseMod.addColor(ZephyrSquallCharacter.Enums.CARD_COLOR, cardColor,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
    }

    public ZephyrSquallMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditCards() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(ZephyrSquallMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new ZephyrSquallCharacter(),
                CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT, ZephyrSquallCharacter.Enums.ZEPHYR_SQUALL);
    }

    public static Predicate<AbstractCard> canBeStreamlined = card -> card.cost > 0 && card.costForTurn > 0 && !card.freeToPlayOnce;
    // Use this if you are checking whether it is an extra turn created by Tailwind.
    public static boolean isTailwindExtraTurn = false;
    // This variable is solely to make sure isTailwindExtraTurn isn't set back to false immediately upon the extra turn starting.
    public static boolean isStartingTailwindExtraTurn = false;
    public static int tailwindGained = 0;
    public static boolean hasOverdrawnThisTurn = false;
    public static int timesAttackedThisTurn = 0;
    public static AbstractCreature lastAttackCardTarget = null;
    // Build Momentum specifically doesn't trigger its effect on the first turn to prevent any ambiguity over
    // whether the player didn't receive damage the previous nonexistent turn. So force this value to true at the
    // start of combat.
    public static boolean hasLostHPLastTurn = true;

    public static boolean isWellRead() {
        int cardThreshold = BaseMod.MAX_HAND_SIZE;
        for(AbstractPower power : AbstractDungeon.player.powers) {
            if (power.ID.equals(LightReadingPower.POWER_ID)) {
                cardThreshold -= power.amount;
            }
        }
        return AbstractDungeon.player.hand.size() >= cardThreshold;
    }

    public static float addPlanOfAttackDamage(float damage, DamageInfo.DamageType type, String cardID) {
        if (type == DamageInfo.DamageType.NORMAL) {
            int planOfAttacksInHand = 0;
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.cardID.equals(PlanOfAttack.ID)) {
                    planOfAttacksInHand++;
                }
            }
            if (cardID.equals(PlanOfAttack.ID)) {
                planOfAttacksInHand--;
            }
            return damage + planOfAttacksInHand;
        }
        return damage;
    }

    public static float applyGlyphOfWardingDamageReduction(float damage) {
        int glyphOfWardingDamageReduction = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card instanceof Book && card.cardID.equals(Book.ID)) {
                // Cast the card to a Book so its recordedCards field can be accessed.
                Book book = (Book) card;
                for (AbstractCard recordedCard : book.recordedCards) {
                    if (recordedCard.cardID.equals(GlyphOfWarding.ID)) {
                        glyphOfWardingDamageReduction += recordedCard.magicNumber;
                    }
                }
            }
        }
        return damage - glyphOfWardingDamageReduction;
    }

    // This is intended to be like the onAttack hook that powers have, with the difference that it is only called once
    // per hit for attacks that hit every enemy, rather than once per hit per enemy. This assumes that other mods will only have Attacks
    public static void onIndividualAttack(AbstractCreature source, AbstractCreature target, DamageInfo.DamageType type, int[] damage) {
        if (source == AbstractDungeon.player && type == DamageInfo.DamageType.NORMAL) {
            // Keep track of attacks made for Assessment.
            timesAttackedThisTurn++;

            // Damage all enemies if the player has the Maelstrom power.
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power.ID.equals(MaelstromPower.POWER_ID)) {
                    power.flash();
                    AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(power.owner, DamageInfo.createDamageMatrix(power.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
            }

            // Keep track of the last target for Wind's Fury
            lastAttackCardTarget = target;
        }
    }

    // This is called whenever the player attempts to draw more cards than their maximum hand size.
    public static void onOverdraw(int cardsOverdrawn) {
        // If the player has the Knowledge is Power power, deal its damage to a random enemy for each card overdrawn.
        if (AbstractDungeon.player.hasPower(makeID("KnowledgeIsPower"))) {
            AbstractPower power = AbstractDungeon.player.getPower(makeID("KnowledgeIsPower"));
            power.flash();
            for (int i = 0; i < cardsOverdrawn; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(power.owner, power.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            }
        }

        // Track whether this is the first time the player has overdrawn this turn. If so, handle the Mind Map power.
        if (!ZephyrSquallMod.hasOverdrawnThisTurn) {
            ZephyrSquallMod.hasOverdrawnThisTurn = true;
            if (AbstractDungeon.player.hasPower(makeID("MindMap"))) {
                AbstractPower mindMapPower = AbstractDungeon.player.getPower(makeID("MindMap"));
                mindMapPower.flash();
                for (int i = 0; i < mindMapPower.amount; i++) {
                    AbstractDungeon.actionManager.addToBottom(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                }
            }
        }
    }

    // Zephyr's Attack's often consist of many hits in a row, which use slashing effects to imply that Zephyr is using
    // his claws and teeth to attack. To keep these effects interesting, this function will produce a random slash
    // effect each time so these attacks don't use the same slash direction for every individual hit. The heavy slash
    // effect is omitted as having this effect occur in a string of otherwise weak slash hits is jarring. This is a
    // purely cosmetic random effect, so it uses Java's own random function to avoid progressing any of Slay the Spire's
    // RNG seeds.
    private static final ArrayList<AbstractGameAction.AttackEffect> slashEffects = new ArrayList<>( Arrays.asList(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    public static AbstractGameAction.AttackEffect getRandomSlashEffect() {
        int effectIndex = MathUtils.random(slashEffects.size() - 1);
        return slashEffects.get(effectIndex);
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        if (!isStartingTailwindExtraTurn)
            isTailwindExtraTurn = false;
        isStartingTailwindExtraTurn = false;
        hasOverdrawnThisTurn = false;
        timesAttackedThisTurn = 0;
    }
    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        isTailwindExtraTurn = false;
        isStartingTailwindExtraTurn = false;
        tailwindGained = 0;
        timesAttackedThisTurn = 0;
        lastAttackCardTarget = null;
        hasLostHPLastTurn = true;
    }

    @Override
    public int receiveOnPlayerLoseBlock(int amount) {
        if (AbstractDungeon.player.hasPower(OneWithTheWindPower.POWER_ID) && isTailwindExtraTurn) {
            return 0;
        }
        return amount;
    }
}
