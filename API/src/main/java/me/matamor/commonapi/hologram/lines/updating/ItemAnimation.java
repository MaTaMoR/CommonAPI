package me.matamor.commonapi.hologram.lines.updating;

import me.matamor.commonapi.utils.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ItemAnimation implements Animation<ItemStack> {

    private final List<ItemStack> slides;
    private int slide = 0;

    public ItemAnimation() {
        this.slides = new ArrayList<>();
    }

    public ItemAnimation(ItemStack... stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = Arrays.asList(stacks);
    }

    public ItemAnimation(List<ItemStack> stacks) {
        Validate.notNull(stacks, "Cannot provide null stacks");
        this.slides = stacks;
    }

    @Override
    public ItemStack firstSlide() {
        return this.slides.get(0);
    }

    @Override
    public ItemStack nextSlide() {
        if (this.slides.isEmpty()) {
            throw new IllegalStateException("There are no slides to display");
        }

        if (this.slide >= this.slides.size()) {
            this.slide = 0;
        }

        return this.slides.get(this.slide++);
    }

    @Override
    public Collection<ItemStack> getSlides() {
        return this.slides;
    }

    @Override
    public boolean addSlide(ItemStack slide) {
        return this.slides.add(slide);
    }

    @Override
    public boolean removeSlide(ItemStack slide) {
        if (this.slides.remove(slide)) {
            this.slide = Math.min(this.slide, slides.size());
            return true;
        }

        return false;
    }
}