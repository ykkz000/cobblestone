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

package ykkz000.cobblestone.test.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.minecraft.nbt.NbtCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ykkz000.cobblestone.api.data.NbtMapper;

public class NbtMapperTest {
    private NbtCompound testNbt;
    private TestPojo testPojo;

    @BeforeEach
    protected void setup() {
        testPojo = new TestPojo(new InnerPojo("Bob", 25, false), "admin");
        testNbt = new NbtCompound();
        NbtCompound innerNbt = new NbtCompound();
        innerNbt.putString("name", "Bob");
        innerNbt.putInt("age", 25);
        innerNbt.putBoolean("active", false);
        testNbt.put("user", innerNbt);
        testNbt.putString("role", "admin");
    }

    @Test
    protected void testReadFromNbt() throws JsonProcessingException {
        TestPojo result = NbtMapper.readFromNbt(testNbt, TestPojo.class);
        Assertions.assertEquals(testPojo, result);
    }

    @Test
    protected void testWriteToNbt() {
        NbtCompound result = NbtMapper.writeToNbt(testPojo);
        NbtCompound innerResult = result.getCompound("user");
        NbtCompound testInnerNbt = testNbt.getCompound("user");
        Assertions.assertAll(
                () -> Assertions.assertEquals(testInnerNbt.get("name"), innerResult.get("name")),
                () -> Assertions.assertEquals(testInnerNbt.get("age"), innerResult.get("age")),
                () -> Assertions.assertEquals(testInnerNbt.get("active"), innerResult.get("active")),
                () -> Assertions.assertEquals(testNbt.get("role"), result.get("role"))
        );
    }

    protected static class InnerPojo {
        private String name;
        private int age;
        private boolean active;

        public InnerPojo() {
        }

        public InnerPojo(String name, int age, boolean active) {
            this.name = name;
            this.age = age;
            this.active = active;
        }

        public String getName() {
            return name;
        }

        public InnerPojo setName(String name) {
            this.name = name;
            return this;
        }

        public int getAge() {
            return age;
        }

        public InnerPojo setAge(int age) {
            this.age = age;
            return this;
        }

        public boolean isActive() {
            return active;
        }

        public InnerPojo setActive(boolean active) {
            this.active = active;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InnerPojo innerPojo = (InnerPojo) o;
            return age == innerPojo.age && active == innerPojo.active && name.equals(innerPojo.name);
        }
    }

    protected static class TestPojo {
        private InnerPojo user;
        private String role;

        public TestPojo() {
        }

        public TestPojo(InnerPojo user, String role) {
            this.user = user;
            this.role = role;
        }

        public InnerPojo getUser() {
            return user;
        }

        public TestPojo setUser(InnerPojo user) {
            this.user = user;
            return this;
        }

        public String getRole() {
            return role;
        }

        public TestPojo setRole(String role) {
            this.role = role;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestPojo that = (TestPojo) o;
            return user.equals(that.user) && role.equals(that.role);
        }
    }
}
