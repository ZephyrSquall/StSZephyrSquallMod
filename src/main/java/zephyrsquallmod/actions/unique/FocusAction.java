package zephyrsquallmod.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class FocusAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private static final ArrayList<AttackEffect> slashEffects = new ArrayList<>( Arrays.asList(AttackEffect.SLASH_HORIZONTAL, AttackEffect.SLASH_VERTICAL, AttackEffect.SLASH_DIAGONAL));
    private int damage;

    public FocusAction(AbstractCreature target, AbstractCreature source, int damage) {
        setValues(target, source, damage);
        this.damage = damage;
    }

    public void update() {
        addToTop(new SelectCardsInHandAction(Integer.MAX_VALUE, TEXT[0], true, true, card -> true, discardSelected));
        this.isDone = true;
    }

    Consumer<List<AbstractCard>> discardSelected = cards -> {
        for(AbstractCard card : cards) {
            int effectIndex = MathUtils.random(slashEffects.size() - 1);
            AttackEffect slashEffect = slashEffects.get(effectIndex);
            addToTop(new DamageAction(this.target, new DamageInfo(this.source, this.damage, DamageInfo.DamageType.NORMAL), slashEffect));
            addToTop(new DiscardSpecificCardAction(card));
        }
    };
 }
