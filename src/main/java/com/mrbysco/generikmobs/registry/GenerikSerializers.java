package com.mrbysco.generikmobs.registry;

import com.mojang.authlib.GameProfile;
import com.mrbysco.generikmobs.GenerikMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class GenerikSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, GenerikMod.MOD_ID);

	public static final RegistryObject<EntityDataSerializer<Optional<GameProfile>>> OPTIONAL_GAME_PROFILE = ENTITY_DATA_SERIALIZER.register("optional_game_profile", () -> new EntityDataSerializer<Optional<GameProfile>>() {
		public void write(FriendlyByteBuf friendlyByteBuf, Optional<GameProfile> optionalGameProfile) {
			friendlyByteBuf.writeBoolean(optionalGameProfile.isPresent());
			if (optionalGameProfile.isPresent()) {
				friendlyByteBuf.writeNbt(NbtUtils.writeGameProfile(new CompoundTag(), optionalGameProfile.get()));
			}

		}

		public Optional<GameProfile> read(FriendlyByteBuf friendlyByteBuf) {
			return !friendlyByteBuf.readBoolean() ? Optional.empty() : Optional.of(NbtUtils.readGameProfile(friendlyByteBuf.readNbt()));
		}

		public Optional<GameProfile> copy(Optional<GameProfile> optionalGameProfile) {
			return optionalGameProfile;
		}
	});
}
