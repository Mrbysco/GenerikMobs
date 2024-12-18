package com.mrbysco.generikmobs.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import net.minecraft.Util;
import net.minecraft.server.Services;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

public class ProfileUtil {
	private static Executor mainThreadExecutor;
	public static final Executor CHECKED_MAIN_THREAD_EXECUTOR = p_294078_ -> {
		Executor executor = mainThreadExecutor;
		if (executor != null) {
			executor.execute(p_294078_);
		}
	};
	@Nullable
	private static LoadingCache<String, CompletableFuture<Optional<GameProfile>>> profileCacheByName;
	@Nullable
	private static LoadingCache<UUID, CompletableFuture<Optional<GameProfile>>> profileCacheById;

	public static void setup(final Services services, Executor executor) {
		mainThreadExecutor = executor;
		final BooleanSupplier booleansupplier = () -> profileCacheById == null;
		profileCacheByName = CacheBuilder.newBuilder()
				.expireAfterAccess(Duration.ofMinutes(10L))
				.maximumSize(256L)
				.build(new CacheLoader<String, CompletableFuture<Optional<GameProfile>>>() {
					public CompletableFuture<Optional<GameProfile>> load(String username) {
						return fetchProfileByName(username, services);
					}
				});
		profileCacheById = CacheBuilder.newBuilder()
				.expireAfterAccess(Duration.ofMinutes(10L))
				.maximumSize(256L)
				.build(new CacheLoader<UUID, CompletableFuture<Optional<GameProfile>>>() {
					public CompletableFuture<Optional<GameProfile>> load(UUID id) {
						return fetchProfileById(id, services, booleansupplier);
					}
				});
	}

	static CompletableFuture<Optional<GameProfile>> fetchProfileByName(String name, Services services) {
		return services.profileCache()
				.getAsync(name)
				.thenCompose(
						profile -> {
							LoadingCache<UUID, CompletableFuture<Optional<GameProfile>>> loadingcache = profileCacheById;
							return loadingcache != null && !profile.isEmpty()
									? loadingcache.getUnchecked(profile.get().getId()).thenApply(gameProfile -> gameProfile.or(() -> profile))
									: CompletableFuture.completedFuture(Optional.empty());
						}
				);
	}

	static CompletableFuture<Optional<GameProfile>> fetchProfileById(UUID id, Services services, BooleanSupplier cacheUninitialized) {
		return CompletableFuture.supplyAsync(() -> {
			if (cacheUninitialized.getAsBoolean()) {
				return Optional.empty();
			} else {
				ProfileResult profileresult = services.sessionService().fetchProfile(id, true);
				return Optional.ofNullable(profileresult).map(ProfileResult::profile);
			}
		}, Util.backgroundExecutor());
	}

	public static CompletableFuture<Optional<GameProfile>> fetchGameProfile(String profileName) {
		LoadingCache<String, CompletableFuture<Optional<GameProfile>>> loadingcache = profileCacheByName;
		return loadingcache != null && StringUtil.isValidPlayerName(profileName)
				? loadingcache.getUnchecked(profileName)
				: CompletableFuture.completedFuture(Optional.empty());
	}

	public static CompletableFuture<Optional<GameProfile>> fetchGameProfile(UUID profileUuid) {
		LoadingCache<UUID, CompletableFuture<Optional<GameProfile>>> loadingcache = profileCacheById;
		return loadingcache != null ? loadingcache.getUnchecked(profileUuid) : CompletableFuture.completedFuture(Optional.empty());
	}

	public static CompletableFuture<ResolvableProfile> resolve(ResolvableProfile resolvableProfile) {
		if (resolvableProfile.isResolved()) {
			return CompletableFuture.completedFuture(resolvableProfile);
		} else {
			return resolvableProfile.id().isPresent() ? fetchGameProfile(resolvableProfile.id().get()).thenApply(p_332081_ -> {
				GameProfile gameprofile = p_332081_.orElseGet(() -> new GameProfile(resolvableProfile.id().get(), resolvableProfile.name().orElse("")));
				return new ResolvableProfile(gameprofile);
			}) : fetchGameProfile(resolvableProfile.name().orElseThrow()).thenApply(p_339530_ -> {
				GameProfile gameprofile = p_339530_.orElseGet(() -> new GameProfile(Util.NIL_UUID, resolvableProfile.name().get()));
				return new ResolvableProfile(gameprofile);
			});
		}
	}

	public static void clear() {
		mainThreadExecutor = null;
		profileCacheByName = null;
		profileCacheById = null;
	}
}
