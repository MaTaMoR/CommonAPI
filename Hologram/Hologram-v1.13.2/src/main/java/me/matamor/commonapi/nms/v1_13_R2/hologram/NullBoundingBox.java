package me.matamor.commonapi.nms.v1_13_R2.hologram;

import net.minecraft.server.v1_13_R2.*;

public class NullBoundingBox extends AxisAlignedBB {

    public NullBoundingBox() {
        super(0, 0, 0, 0, 0, 0);
    }

    @Override
    public double a() {
        return 0.0;
    }

    @Override
    public AxisAlignedBB a(AxisAlignedBB arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public MovingObjectPosition b(Vec3D arg0, Vec3D arg1) {
        return null;
    }

    @Override
    public AxisAlignedBB grow(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public AxisAlignedBB shrink(double arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(BlockPosition arg0) {
        return this;
    }

    @Override
    public boolean a(double arg0, double arg1, double arg2, double arg3, double arg4, double arg5) {
        return false;
    }

    @Override
    public boolean b(Vec3D arg0) {
        return false;
    }

    @Override
    public AxisAlignedBB g(double arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(Vec3D arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(AxisAlignedBB arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public boolean c(AxisAlignedBB arg0) {
        return false;
    }

    @Override
    public AxisAlignedBB d(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public double a(EnumDirection.EnumAxis arg0) {
        return 0.0;
    }

    @Override
    public MovingObjectPosition a(Vec3D arg0, Vec3D arg1, BlockPosition arg2) {
        return null;
    }

    @Override
    public double b(EnumDirection.EnumAxis arg0) {
        return 0.0;
    }

    @Override
    public boolean e(double arg0, double arg1, double arg2) {
        return false;
    }

    @Override
    public AxisAlignedBB f(double arg0, double arg1, double arg2) {
        return this;
    }
}