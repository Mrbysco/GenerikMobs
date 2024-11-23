package com.mrbysco.generikmobs.util;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import net.minecraft.Util;
import net.minecraft.server.Services;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.StringUtil;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class ProfileUtil {
	private static GameProfileCache profileCache;
	private static MinecraftSessionService sessionService;
	private static Executor mainThreadExecutor;

	@Nullable
	public static void updateGameProfile(@Nullable GameProfile profile, Consumer<GameProfile> profileConsumer) {
		if (profile != null && !StringUtil.isNullOrEmpty(profile.getName()) && (!profile.isComplete() ||
				!profile.getProperties().containsKey("textures")) && profileCache != null && sessionService != null) {
			profileCache.getAsync(profile.getName(), (gameProfile) -> {
				Util.backgroundExecutor().execute(() -> {
					Util.ifElse(gameProfile, (gameProfile1) -> {
						Property property = Iterables.getFirst(gameProfile1.getProperties().get("textures"), (Property) null);
						if (property == null) {
							gameProfile1 = sessionService.fillProfileProperties(gameProfile1, true);
						}

						GameProfile gameprofile = gameProfile1;
						mainThreadExecutor.execute(() -> {
							profileCache.add(gameprofile);
							profileConsumer.accept(gameprofile);
						});
					}, () -> {
						mainThreadExecutor.execute(() -> {
							profileConsumer.accept(profile);
						});
					});
				});
			});
		} else {
			profileConsumer.accept(profile);
		}
	}

	public static void setup(GameProfileCache gameProfileCache, MinecraftSessionService service, Executor executor) {
		profileCache = gameProfileCache;
		sessionService = service;
		mainThreadExecutor = executor;
	}

	public static void setup(Services services, Executor executor) {
		setup(services.profileCache(), services.sessionService(), executor);
	}

	public static void clear() {
		profileCache = null;
		sessionService = null;
		mainThreadExecutor = null;
	}
}
