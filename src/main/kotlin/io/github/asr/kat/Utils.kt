package io.github.asr.kat

import net.dv8tion.jda.api.entities.User

fun User.isRegistered() = options.isSet("users.$id")

fun User.money() = options.getLong("users.$id.money")

fun User.setMoney(money: Long) = options.set("users.$id.money", money)

fun User.addMoney(money: Long) = setMoney(money() + money)

fun User.subMoney(money: Long) = setMoney(money() - money)

fun User.getTimes(times: Int) = options.getLong("users.$id.times.$times")
