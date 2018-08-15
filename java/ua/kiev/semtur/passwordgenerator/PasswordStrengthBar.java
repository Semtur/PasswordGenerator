package ua.kiev.semtur.passwordgenerator;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by SemTur on 06.09.2017.
 */

public class PasswordStrengthBar extends TextView {
    private int mMaxValue = 128;
    private String mValueUnit;

    public PasswordStrengthBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PasswordStrengthBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordStrengthBar(Context context) {
        super(context);
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
    }

    public void setValueUnit(String valueUnit) {
        mValueUnit = valueUnit;
    }

    public synchronized void setValue(int value) {
        this.setText(String.valueOf(value) + " " + mValueUnit);
        LayerDrawable background = (LayerDrawable) this.getBackground();
        ClipDrawable barValue = (ClipDrawable) background.getDrawable(1);
        int newClipLevel = (int) (value * 10000 / mMaxValue);
        barValue.setLevel(newClipLevel);
        drawableStateChanged();
    }
}
