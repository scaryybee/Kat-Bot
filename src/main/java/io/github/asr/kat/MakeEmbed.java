package io.github.asr.kat;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class MakeEmbed {
    public static MessageEmbed create(String title, String description) {
        return new MessageEmbed(null, title, description,
                EmbedType.UNKNOWN, null, 0x777777, null, null, null,
                null, null, null, null);
    }
}
