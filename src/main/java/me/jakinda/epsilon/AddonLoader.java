package me.jakinda.epsilon;

import me.jakinda.epsilon.block.CustomBlock;
import me.jakinda.epsilon.item.CustomItem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonLoader {

    private final Epsilon plugin;
    private final File addonsFolder;
    private final List<URLClassLoader> loaders = new ArrayList<>();

    public AddonLoader(Epsilon plugin) {
        this.plugin = plugin;
        this.addonsFolder = new File(plugin.getDataFolder(), "addons");

        if (!addonsFolder.exists()) addonsFolder.mkdirs();
    }

    public void loadAddons() {
        File[] jarFiles = addonsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if (jarFiles == null || jarFiles.length == 0) {
            plugin.getLogger().info("No addons found.");
            return;
        }

        for (File jarFile : jarFiles) {
            loadAddon(jarFile);
        }
    }

    private void loadAddon(File jarFile) {
        try {
            URL jarUrl = jarFile.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl}, plugin.getClass().getClassLoader());
            loaders.add(loader);

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();

                    if (!name.endsWith(".class")) continue;

                    // com/example/MyItem.class -> com.example.MyItem
                    String className = name.replace("/", ".").replace(".class", "");
                    try {
                        Class<?> clazz = loader.loadClass(className);

                        if (Modifier.isAbstract(clazz.getModifiers())) continue;
                        if (clazz.isInterface()) continue;

                        if (CustomItem.class.isAssignableFrom(clazz)) {
                            clazz.getDeclaredConstructor().newInstance();
                            plugin.getLogger().info("Loaded item: " + clazz.getSimpleName());
                        } else if (CustomBlock.class.isAssignableFrom(clazz)) {
                            clazz.getDeclaredConstructor().newInstance();
                            plugin.getLogger().info("Loaded block: " + clazz.getSimpleName());
                        }
                    } catch (NoSuchMethodException e) {
                        plugin.getLogger().warning(className + " has no no-args constructor, skipping.");
                    } catch (Exception e) {
                        plugin.getLogger().warning("Failed to load class: " + className);
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load addon: " + jarFile.getName());
            e.printStackTrace();
        }
    }

    public void unloadAddons() {
        for (URLClassLoader loader: loaders) {
            try {
                loader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loaders.clear();
    }
}
