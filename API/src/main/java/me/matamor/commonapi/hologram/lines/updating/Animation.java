package me.matamor.commonapi.hologram.lines.updating;

import java.util.Collection;

public interface Animation<T> {

    T firstSlide();

    T nextSlide();

    Collection<T> getSlides();

    boolean addSlide(T slide);

    boolean removeSlide(T slide);

}