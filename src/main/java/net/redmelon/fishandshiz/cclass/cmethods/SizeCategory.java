package net.redmelon.fishandshiz.cclass.cmethods;

public enum SizeCategory {
    /**Used in determining the size value of an entity. Because an entity up to two size category values in their class,
     * as a method and in their size-based goals, playing around with those can influence how entities attack one another.
     * For example, having it as LARGE in the method but SMALL in the goal makes it appear larger to other entities
     * but can only itself attack entities smaller than SMALL**/
    EGG, FRY, TINY, SMALL, MEDIUM, LARGE, VERY_LARGE;
}
