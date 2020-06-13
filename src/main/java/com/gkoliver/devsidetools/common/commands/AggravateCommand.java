package com.gkoliver.devsidetools.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class AggravateCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("aggravate")
				.then(Commands.argument("aggressor", EntityArgument.entity())
				.then(Commands.argument("aggression", EntityArgument.entity()).executes((context)->{
					return aggravate(context.getSource(), EntityArgument.getEntity(context, "aggressor"), EntityArgument.getEntity(context, "aggression"), false);
				}).then(Commands.argument("doBoth", BoolArgumentType.bool()).executes((context)->{
					return aggravate(context.getSource(), EntityArgument.getEntity(context, "aggressor"), EntityArgument.getEntity(context, "aggression"), BoolArgumentType.getBool(context, "doBoth"));
				})))
		));
	}
	public static int aggravate(CommandSource source, Entity aggressor, Entity aggression, boolean doBoth) {
		if (aggressor instanceof LivingEntity && aggression instanceof LivingEntity) {
			LivingEntity aggressor1 = (LivingEntity) aggressor;
			LivingEntity aggression1 = (LivingEntity) aggression;
			aggressor1.setRevengeTarget(aggression1);
			if (doBoth) {
				aggression1.setRevengeTarget(aggressor1);
			}
			source.sendFeedback(new TranslationTextComponent("commands.aggravate"), true);
			
		}
		return 1;
	}

}