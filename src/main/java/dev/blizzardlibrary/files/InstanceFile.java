package dev.blizzardlibrary.files;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Use this to extend off of.
 */
public abstract class InstanceFile extends AbstractFile {

    @Override
    public void createFile(JavaPlugin plugin, String fileName) {
        super.createFile(plugin, fileName);
    }

    @Override
    public void createFile(JavaPlugin plugin, String folderName, String fileName) {
        super.createFile(plugin, folderName, fileName);
    }

    public void saveFile() {
        super.saveFile(getFileConfiguration());
    }


    @Override
    public void reloadFile(JavaPlugin plugin, String fileName) {
        super.reloadFile(plugin, fileName);
    }

    @Override
    public void reloadFile(JavaPlugin plugin, String folderName, String fileName) {
        super.reloadFile(plugin, folderName, fileName);
    }

    @Override
    public boolean isFileNotEmpty() {
        return super.isFileNotEmpty();
    }

    @Override
    public boolean containsPath(String path) {
        return super.containsPath(path);
    }

    public abstract void setData();
}