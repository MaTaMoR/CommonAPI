package me.matamor.commonapi.hologram.lines.updating;

import me.matamor.commonapi.utils.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TextAnimation implements Animation<String> {

    private final List<String> slides;
    private int slide = 0;

    public TextAnimation() {
        this.slides = new ArrayList<>();
    }

    public TextAnimation(String... stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = Arrays.asList(stacks);
    }

    public TextAnimation(List<String> stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = stacks;
    }

    @Override
    public String firstSlide() {
        return this.slides.get(0);
    }

    @Override
    public String nextSlide() {
        if (this.slides.isEmpty()) {
            throw new IllegalStateException("There are no slides to display");
        }

        if (this.slide >= this.slides.size()) {
            this.slide = 0;
        }

        return this.slides.get(this.slide++);
    }

    @Override
    public Collection<String> getSlides() {
        return this.slides;
    }

    @Override
    public boolean addSlide(String slide) {
        return this.slides.add(slide);
    }

    @Override
    public boolean removeSlide(String slide) {
        if (this.slides.remove(slide)) {
            this.slide = Math.min(this.slide, this.slides.size());
            return true;
        }

        return false;
    }
}