package me.jakinda.epsilon.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class Text {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component of(String message) {
        return mm.deserialize(message);
    }

    public static Component of(String message, TagResolver... resolvers) {
        return mm.deserialize(message, resolvers);
    }

    public static Component of(String message, Object... placeholders) {
        if (placeholders.length == 0) {
            return mm.deserialize(message);
        }

        if (placeholders.length % 2 != 0) {
            throw new IllegalArgumentException("Placeholders must be in key-value pairs");
        }

        TagResolver[] resolvers = new TagResolver[placeholders.length / 2];
        for (int i = 0; i < placeholders.length; i += 2) {
            String key = (String) placeholders[i];
            Object value = placeholders[i + 1];

            if (value instanceof Component comp) {
                resolvers[i / 2] = Placeholder.component(key, comp);
            } else {
                resolvers[i / 2] = Placeholder.unparsed(key, String.valueOf(value));
            }
        }

        return mm.deserialize(message, resolvers);
    }

    public static String toSnakeCase(String pascalCase) {
        return pascalCase
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }

    public static String extractNamespace(Class<?> clazz) {
        String packageName = clazz.getPackageName();
        String[] parts = packageName.split("\\.");

        if (parts.length > 2) {
            return parts[2].toLowerCase().replaceAll("[^a-z]", "");
        }

        return parts[parts.length - 1].toLowerCase().replaceAll("[^a-z]", "");
    }
}

