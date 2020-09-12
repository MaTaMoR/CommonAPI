package me.matamor.commonapi.hologram.lines.updating;

import me.matamor.commonapi.utils.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AbstractAnimation<T> implements Animation<T> {

    private final List<T> slides;
    private int slide = 0;

    public AbstractAnimation() {
        this.slides = new ArrayList<>();
    }

    public AbstractAnimation(T... stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = Arrays.asList(stacks);
    }

    public AbstractAnimation(List<T> stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = stacks;
    }

    @Override
    public T firstSlide() {
        return this.slides.get(0);
    }

    @Override
    public T nextSlide() {
        if (this.slides.isEmpty()) {
            throw new IllegalStateException("There are no slides to display");
        }

        if (this.slide >= this.slides.size()) {
            this.slide = 0;
        }

        return this.slides.get(this.slide++);
    }

    @Override
    public Collection<T> getSlides() {
        return this.slides;
    }

    @Override
    public boolean addSlide(T slide) {
        return this.slides.add(slide);
    }

    @Override
    public boolean removeSlide(T slide) {
        if (this.slides.remove(slide)) {
            this.slide = Math.min(this.slide, this.slides.size());
            return true;
        }

        return false;
    }
}