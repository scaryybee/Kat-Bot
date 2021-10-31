package io.github.asr.kat

import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlin.random.Random

fun User.betting(bet: Long): Int {
    subMoney(bet)
    val random = Random.nextInt(100)

    val times = if (random < 5) 5 else if (random < 20) 3 else if (random < 60) 2 else 0

    val path = "users.${this.id}.times.$times"
    if (!options.isSet(path)) options.set(path, 0)

    val time = options.getLong(path)

    options.set(path, time + 1)
    addMoney(bet * times)

    optionSave()

    return times
}

fun User.getTimesEmbed() = MakeEmbed.create("도박 전적",
    "잃은 횟수 ${getTimes(0)}\n" +
            "2 배 ${getTimes(2)}\n" +
            "3 배 ${getTimes(3)}\n" +
            "5 배 ${getTimes(5)}")!!

class Listeners : ListenerAdapter() {
    override fun onSlashCommand(event: SlashCommandEvent) {
        if (event.user.isBot) return

        if (!event.isFromGuild) return

        when (event.name) {
            "온라인" -> event.reply("온라인 상태입니다!").queue()

            "등록" -> {
                if (event.user.isRegistered()) {
                    event.reply("이미 등록을 했어요!").queue()
                    return
                }

                event.user.setMoney(100000)
                optionSave()

                event.reply("등록이 되었습니다!").queue()
            }

            "도박" -> {
                if (event.options.size < 1) {
                    event.reply("베팅할 돈을 입력해주세요!")
                    return
                }
                val bet = event.options[0].asLong

                if (!event.user.isRegistered()) {
                    event.reply("등록되지 않았습니다! 등록해주세요!").queue()
                    return
                } else if (event.user.money() < bet) {
                    event.reply("당신의 돈보다 더 많은 돈을 도박 할 수 없습니다!").queue()
                    return
                } else if (bet < 1000) {
                    event.reply("최소 금액은 1000 원입니다!").queue()
                    return
                } else {
                    val times = event.user.betting(bet)
                    event.reply("베팅한 돈이 $times 배가 되었어요!").queue()
                }
            }

            "올인" -> {
                if (!event.user.isRegistered()) {
                    event.reply("등록되지 않았습니다! 등록해주세요!").queue()
                    return
                } else if (event.user.money() < 1000) {
                    event.reply("최소 금액은 1000 원입니다!").queue()
                    return
                }
                event.reply("베팅한 돈이 ${event.user.betting(event.user.money())} 배가 되었어요!").queue()
            }

            "하프" -> {
                if (!event.user.isRegistered()) {
                    event.reply("등록되지 않았습니다! 등록해주세요!").queue()
                    return
                } else if (event.user.money() / 2 < 1000) {
                    event.reply("최소 금액은 1000 원입니다!").queue()
                    return
                }
                event.reply("베팅한 돈이 ${event.user.betting(event.user.money() / 2)} 배가 되었어요!").queue()
            }

            "돈" -> {
                val user = if (event.options.size < 1) event.user else event.options[0].asUser

                if (!user.isRegistered()) {
                    event.reply("유저가 등록하지 않았습니다!").queue()
                    return
                }

                val embed = MakeEmbed.create("${user.name} 님의 돈", "${user.money().formatNumber()} 원")

                event.replyEmbeds(embed).queue()
            }

            "송금" -> {
                if (event.options.size < 2) {
                    event.reply("보낼 사람과 보낼 돈을 적어주세요!").queue()
                    return
                } else if (!event.user.isRegistered()) {
                    event.reply("등록되지 않았습니다! 등록해주세요!").queue()
                    return
                } else if (!event.options[0].asUser.isRegistered()) {
                    event.reply("등록 되지 않은 유저에게 송금 할 수 없습니다!").queue()
                    return
                }

                val money = event.options[1].asLong

                if (money < 0) {
                    event.reply("음수인 돈을 보낼 수 없습니다!").queue()
                    return
                } else if (money <= event.user.money()) {
                    event.user.subMoney(money)
                    event.options[0].asUser.addMoney(money)
                    event.reply("${event.options[0].asUser.name} 님에게 $money 원을 보냈습니다.").queue()
                } else event.reply("보낼 돈이 부족합니다!").queue()

                optionSave()
            }

            "일" -> {
                event.reply("1000 원을 받았습니다!").queue()
                event.user.addMoney(1000)
                optionSave()
            }

            "전적" -> {
                if (!event.user.isRegistered()) {
                    event.reply("등록되지 않았습니다! 등록해주세요!").queue()
                    return
                }

                if (event.options.size == 0) event.replyEmbeds(event.user.getTimesEmbed()).queue()
                else {
                    if (!event.options[0].asUser.isRegistered()) {
                        event.reply("등록 되지 않은 유저의 전적을 볼 수 없습니다!").queue()
                        return
                    }
                    event.replyEmbeds(event.options[0].asUser.getTimesEmbed()).queue()
                }
            }
        }
    }
}