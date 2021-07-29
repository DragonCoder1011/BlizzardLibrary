package dev.blizzardlibrary.builder;

public class BuilderAccess {

    private ItemBuilder itemBuilder = null;

    public ItemBuilder getItemBuilder() {
        if(itemBuilder == null){
            itemBuilder = new ItemBuilder();
        }

        return itemBuilder;
    }
}
