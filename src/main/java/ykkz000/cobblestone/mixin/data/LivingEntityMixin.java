/*
 * Cobblestone API
 * Copyright (C) 2025  ykkz000
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ykkz000.cobblestone.mixin.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ykkz000.cobblestone.api.data.ExtraDataProvider;
import ykkz000.cobblestone.api.data.NbtMapper;

import java.util.HashMap;

/**
 * Mixin for LivingEntity. Allows developers to add more data to the living entity.
 *
 * @author ykkz000
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements ExtraDataProvider {
    @Unique
    private final HashMap<Identifier, NbtCompound> extendedData = new HashMap<>();

    @Override
    @Intrinsic
    public <T> T getExtraData(Identifier key, Class<T> clazz) throws JsonProcessingException {
        return NbtMapper.readFromNbt(extendedData.get(key), clazz);
    }

    @Override
    @Intrinsic
    public <T> void setExtraData(Identifier key, T data) {
        extendedData.put(key, NbtMapper.writeToNbt(data));
    }

    @Inject(method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    public void readExtendedDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        extendedData.clear();
        nbt = nbt.getCompound("cobblestone_extended_data");
        for (String key : nbt.getKeys()) {
            extendedData.put(Identifier.tryParse(key), nbt.getCompound(key));
        }
    }

    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("RETURN"))
    public void writeExtendedDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtCompound extendedDataNbt = new NbtCompound();
        for (Identifier key : extendedData.keySet()) {
            extendedDataNbt.put(key.toString(), extendedData.get(key));
        }
        nbt.put("cobblestone_extended_data", extendedDataNbt);
    }
}
