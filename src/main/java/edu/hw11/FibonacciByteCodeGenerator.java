package edu.hw11;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public class FibonacciByteCodeGenerator implements ByteCodeAppender {
    private static final int OPERAND_STACK_SIZE = 5;
    private static final String METHOD_NAME = "fib";
    private static final String METHOD_DESCRIPTOR = "(I)J";

    @Override
    public @NotNull Size apply(
        @NotNull MethodVisitor methodVisitor,
        Implementation.@NotNull Context context,
        @NotNull MethodDescription methodDescription
    ) {
        methodVisitor.visitCode();

        Label elseLabel = new Label();

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGT, elseLabel);

        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.I2L);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        methodVisitor.visitLabel(elseLabel);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitInsn(Opcodes.ISUB);
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            ByteBuddyUtils.FIBONACCI_CALCULATOR,
            METHOD_NAME,
            METHOD_DESCRIPTOR
        );

        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
        methodVisitor.visitInsn(Opcodes.ICONST_2);
        methodVisitor.visitInsn(Opcodes.ISUB);
        methodVisitor.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            ByteBuddyUtils.FIBONACCI_CALCULATOR,
            METHOD_NAME,
            METHOD_DESCRIPTOR
        );

        methodVisitor.visitInsn(Opcodes.LADD);
        methodVisitor.visitInsn(Opcodes.LRETURN);

        methodVisitor.visitEnd();
        return new Size(OPERAND_STACK_SIZE, 0);
    }
}
