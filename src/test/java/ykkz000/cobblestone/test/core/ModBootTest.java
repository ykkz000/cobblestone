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

package ykkz000.cobblestone.test.core;

import net.fabricmc.api.*;
import net.minecraft.SharedConstants;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ykkz000.cobblestone.api.core.ModBoot;
import ykkz000.cobblestone.api.core.annotation.AutoBootstrap;
import ykkz000.cobblestone.api.core.annotation.Configuration;
import ykkz000.cobblestone.api.core.annotation.ConfigurationInstance;

public class ModBootTest {
    private static int commonSideCount = 0;
    private static int clientSideCount = 0;
    private static int dedicatedServerSideCount = 0;

    @Configuration(path = "classpath:/cobblestone_api_test/test_configuration.json")
    protected static class ConfigurationClass {
        @ConfigurationInstance
        private static ConfigurationClass INSTANCE;
        private InnerClass a;

        public void setA(InnerClass a) {
            this.a = a;
        }

        public InnerClass getA() {
            return a;
        }

        protected static class InnerClass {
            private int b;

            public void setB(int b) {
                this.b = b;
            }

            public int getB() {
                return b;
            }
        }
    }

    @AutoBootstrap
    private static class CommonSideCommonClass {
        static {
            commonSideCount += ConfigurationClass.INSTANCE.a.b;
        }
    }

    @AutoBootstrap
    private static class CommonSideRegistryClass {
        static {
            Registry.register(Registries.ITEM, Identifier.of("cobblestone_api_test:test_item"), new Item(new Item.Settings()));
        }
    }

    @AutoBootstrap
    @Environment(EnvType.CLIENT)
    private static class ClientSideCommonClass {
        static {
            clientSideCount++;
        }
    }

    @AutoBootstrap
    @Environment(EnvType.CLIENT)
    private static class DedicatedServerSideCommonClass {
        static {
            dedicatedServerSideCount++;
        }
    }

    public static class TestModInitializer implements ModInitializer {
        @Override
        public void onInitialize() {
            new ModBoot(TestModInitializer.class).start();
        }
    }

    public static class TestClientModInitializer implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
            new ModBoot(TestClientModInitializer.class).start();
        }
    }

    public static class TestDedicatedServerModInitializer implements DedicatedServerModInitializer {
        @Override
        public void onInitializeServer() {
            new ModBoot(TestDedicatedServerModInitializer.class).start();
        }
    }


    @BeforeEach
    public void setup() {
        SharedConstants.createGameVersion();
        net.minecraft.Bootstrap.initialize();
        TestModInitializer testModInitializer = new TestModInitializer();
        testModInitializer.onInitialize();
        TestClientModInitializer testClientModInitializer = new TestClientModInitializer();
        testClientModInitializer.onInitializeClient();
        TestDedicatedServerModInitializer testDedicatedServerModInitializer = new TestDedicatedServerModInitializer();
        testDedicatedServerModInitializer.onInitializeServer();
    }

    @Test
    public void testCommonSideCommonClass() {
        Assertions.assertEquals(5, commonSideCount);
    }

    @Test
    public void testCommonSideRegistryClass() {
        Assertions.assertTrue(Registries.ITEM.containsId(Identifier.of("cobblestone_api_test:test_item")));
    }

    @Test
    public void testClientSideCommonClass() {
        Assertions.assertEquals(1, clientSideCount);
    }

    @Test
    public void testDedicatedServerSideCommonClass() {
        Assertions.assertEquals(1, dedicatedServerSideCount);
    }
}
