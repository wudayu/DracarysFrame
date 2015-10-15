package com.nfky.yaoyijia.views;

import com.nfky.yaoyijia.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;

/**
 * Created by David on 8/27/15.
 * <p/>
 * 圆行图片的控件，可自动切割
 * 请勿修改此文件
 */
public class CircleImageView extends ImageView {

    /**
     * The constant TAG.
     */
    public static final String TAG = "RoundedImageView";
    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final float DEFAULT_RADIUS = 0f;
    /**
     * The constant DEFAULT_BORDER_WIDTH.
     */
    public static final float DEFAULT_BORDER_WIDTH = 0f;
    private static final ScaleType[] SCALE_TYPES = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };

    private float cornerRadius = DEFAULT_RADIUS;
    private float borderWidth = DEFAULT_BORDER_WIDTH;
    private ColorStateList borderColor =
            ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
    private boolean isOval = false;
    private boolean mutateBackground = false;

    private int mResource;
    private Drawable mDrawable;
    private Drawable mBackgroundDrawable;

    private ScaleType mScaleType;

    /**
     * Instantiates a new Circle image view.
     *
     * @param context the context
     */
    public CircleImageView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Circle image view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Circle image view.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param defStyle the def style
     */
    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);

        int index = a.getInt(R.styleable.CircleImageView_android_scaleType, -1);
        if (index >= 0) {
            setScaleType(SCALE_TYPES[index]);
        } else {
            // Set default scaleType to FIT_CENTER
            setScaleType(ScaleType.FIT_CENTER);
        }

        cornerRadius = a.getDimensionPixelSize(R.styleable.CircleImageView_corner_radius, -1);
        borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, -1);

        // don't allow negative values for radius and border
        if (cornerRadius < 0) {
            cornerRadius = DEFAULT_RADIUS;
        }
        if (borderWidth < 0) {
            borderWidth = DEFAULT_BORDER_WIDTH;
        }

        borderColor = a.getColorStateList(R.styleable.CircleImageView_border_color);
        if (borderColor == null) {
            borderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        }

        mutateBackground = a.getBoolean(R.styleable.CircleImageView_mutate_background, false);
        isOval = a.getBoolean(R.styleable.CircleImageView_oval, false);

        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(true);

        a.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    /**
     * Return the current scale type in use by this ImageView.
     *
     * ref android.R.styleable#ImageView_scaleType
     * see android.widget.ImageView.ScaleType
     */
    @Override
    public ScaleType getScaleType() {
        return mScaleType;
    }

    /**
     * Controls how the image should be resized or moved to match the size
     * of this ImageView.
     *
     * @param scaleType The desired scaling mode.
     * ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public void setScaleType(ScaleType scaleType) {
        assert scaleType != null;

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            invalidate();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mResource = 0;
        mDrawable = RoundedDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mResource = 0;
        mDrawable = RoundedDrawable.fromBitmap(bm);
        updateDrawableAttrs();
        super.setImageDrawable(mDrawable);
    }

    @Override
    public void setImageResource(int resId) {
        if (mResource != resId) {
            mResource = resId;
            mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(mDrawable);
        }
    }

    @Override public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    private Drawable resolveResource() {
        Resources rsrc = getResources();
        if (rsrc == null) { return null; }

        Drawable d = null;

        if (mResource != 0) {
            try {
                d = rsrc.getDrawable(mResource);
            } catch (Exception e) {
                Log.w(TAG, "Unable to find resource: " + mResource, e);
                // Don't try again.
                mResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(d);
    }

    @Override
    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    private void updateDrawableAttrs() {
        updateAttrs(mDrawable);
    }

    private void updateBackgroundDrawableAttrs(boolean convert) {
        if (mutateBackground) {
            if (convert) {
                mBackgroundDrawable = RoundedDrawable.fromDrawable(mBackgroundDrawable);
            }
            updateAttrs(mBackgroundDrawable);
        }
    }

    private void updateAttrs(Drawable drawable) {
        if (drawable == null) { return; }

        if (drawable instanceof RoundedDrawable) {
            ((RoundedDrawable) drawable)
                    .setScaleType(mScaleType)
                    .setCornerRadius(cornerRadius)
                    .setBorderWidth(borderWidth)
                    .setBorderColor(borderColor)
                    .setOval(isOval);
        } else if (drawable instanceof LayerDrawable) {
            // loop through layers to and set drawable attrs
            LayerDrawable ld = ((LayerDrawable) drawable);
            for (int i = 0, layers = ld.getNumberOfLayers(); i < layers; i++) {
                updateAttrs(ld.getDrawable(i));
            }
        }
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
        updateBackgroundDrawableAttrs(true);
        super.setBackgroundDrawable(mBackgroundDrawable);
    }

    /**
     * Gets corner radius.
     *
     * @return the corner radius
     */
    public float getCornerRadius() {
        return cornerRadius;
    }

    /**
     * Sets corner radius.
     *
     * @param resId the res id
     */
    public void setCornerRadius(int resId) {
        setCornerRadius(getResources().getDimension(resId));
    }

    /**
     * Sets corner radius.
     *
     * @param radius the radius
     */
    public void setCornerRadius(float radius) {
        if (cornerRadius == radius) { return; }

        cornerRadius = radius;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
    }

    /**
     * Gets border width.
     *
     * @return the border width
     */
    public float getBorderWidth() {
        return borderWidth;
    }

    /**
     * Sets border width.
     *
     * @param resId the res id
     */
    public void setBorderWidth(int resId) {
        setBorderWidth(getResources().getDimension(resId));
    }

    /**
     * Sets border width.
     *
     * @param width the width
     */
    public void setBorderWidth(float width) {
        if (borderWidth == width) { return; }

        borderWidth = width;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    /**
     * Gets border color.
     *
     * @return the border color
     */
    public int getBorderColor() {
        return borderColor.getDefaultColor();
    }

    /**
     * Sets border color.
     *
     * @param color the color
     */
    public void setBorderColor(int color) {
        setBorderColor(ColorStateList.valueOf(color));
    }

    /**
     * Gets border colors.
     *
     * @return the border colors
     */
    public ColorStateList getBorderColors() {
        return borderColor;
    }

    /**
     * Sets border color.
     *
     * @param colors the colors
     */
    public void setBorderColor(ColorStateList colors) {
        if (borderColor.equals(colors)) { return; }

        borderColor =
                (colors != null) ? colors : ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        if (borderWidth > 0) {
            invalidate();
        }
    }

    /**
     * Is oval boolean.
     *
     * @return the boolean
     */
    public boolean isOval() {
        return isOval;
    }

    /**
     * Sets oval.
     *
     * @param oval the oval
     */
    public void setOval(boolean oval) {
        isOval = oval;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    /**
     * Is mutate background boolean.
     *
     * @return the boolean
     */
    public boolean isMutateBackground() {
        return mutateBackground;
    }

    /**
     * Sets mutate background.
     *
     * @param mutate the mutate
     */
    public void setMutateBackground(boolean mutate) {
        if (mutateBackground == mutate) { return; }

        mutateBackground = mutate;
        updateBackgroundDrawableAttrs(true);
        invalidate();
    }
}
