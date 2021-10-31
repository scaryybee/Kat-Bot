package io.github.asr.kat

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File


val options = YamlConfiguration.loadConfiguration(File("options.yml"))

fun optionSave() = options.save(File("options.yml"))

fun token(): String {
    try {
        if (File("options.yml").exists()) {
            options.save(File("options.yml"))
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return options.getString("token")!!
}

fun main() {
    val jda: JDA = JDABuilder.createLight(token()).apply {
        this.setActivity(Activity.watching("타짜"))
        this.addEventListeners(Listeners())
    }.build()

    jda.updateCommands().apply {
        this.addCommands(CommandData("온라인", "커맨드가 온라인 상태인지 확인 할 수 있어요!"))

        this.addCommands(CommandData("등록", "등록을 할 수 있어요!"))
        println("명령어 \"등록\" 등록 완료!")

        this.addCommands(CommandData("도박", "도박을 할 수 있어요!")
            .addOption(OptionType.INTEGER, "돈", "도박할 돈 (최소 금액 1000 원)", true))
        println("명령어 \"도박\" 등록 완료!")

        this.addCommands(CommandData("올인", "도박에서 올인을 할 수 있어요!"))
        println("명령어 \"올인\" 등록 완료!")
        this.addCommands(CommandData("하프", "도박에서 하프를 할 수 있어요!"))
        println("명령어 \"하프\" 등록 완료!")

        this.addCommands(CommandData("돈", "내 돈을 볼수 있어요!")
            .addOption(OptionType.USER, "유저", "유저의 돈을 볼 수 있어요!"))
        println("명령어 \"돈\" 등록 완료!")

        this.addCommands(CommandData("송금", "누군가에게 돈을 보낼 수 있어요!")
            .addOption(OptionType.USER, "유저", "돈을 보낼 유저", true)
            .addOption(OptionType.INTEGER, "돈", "유저한테 보낼 돈", true))
        println("명령어 \"송금\" 등록 완료!")

        this.addCommands(CommandData("일", "일을 해서 돈을 벌 수 있어요!"))
        println("명령어 \"일\" 등록 완료!")

        this.addCommands(CommandData("전적", "도박 전적을 볼 수 있어요!")
            .addOption(OptionType.USER, "유저", "전적을 확인 할 유저"))
        println("명령어 \"전적\" 등록 완료!")
    }.queue()
}