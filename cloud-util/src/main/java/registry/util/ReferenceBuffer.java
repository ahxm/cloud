package registry.util;

import java.nio.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by anzhen on 2016/5/24.
 */
public class ReferenceBuffer implements ReferenceResource {

    private ByteBuffer buffer;

    private ReferenceResource resource;

    private AtomicBoolean released = new AtomicBoolean(false);

    public ReferenceBuffer(ByteBuffer buffer, ReferenceResource resource) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer can not be null");
        }
        this.buffer = buffer;
        this.resource = resource;
        if (resource != null) {
            resource.retain();
        }
    }


    public ByteBuffer getBuffer() {
        return buffer;
    }

    public ReferenceResource getResource() {
        return resource;
    }

    public final int capacity() {
        return buffer.capacity();
    }

    public final int position() {
        return buffer.position();
    }

    public final Buffer position(int newPosition) {
        return buffer.position(newPosition);
    }

    public final int limit() {
        return buffer.limit();
    }

    public final Buffer limit(int newLimit) {
        return buffer.limit(newLimit);
    }

    public final Buffer mark() {
        return buffer.mark();
    }

    public final Buffer reset() {
        return buffer.reset();
    }

    public final Buffer clear() {
        return buffer.clear();
    }

    public final Buffer flip() {
        return buffer.flip();
    }

    public final Buffer rewind() {
        return buffer.rewind();
    }

    public final int remaining() {
        return buffer.remaining();
    }

    public final boolean hasRemaining() {
        return buffer.hasRemaining();
    }

    public final boolean isReadOnly() {
        return buffer.isReadOnly();
    }

    public final boolean hasArray() {
        return buffer.hasArray();
    }

    public final int arrayOffset() {
        return buffer.arrayOffset();
    }

    public final boolean isDirect() {
        return buffer.isDirect();
    }

    public final ByteBuffer slice() {
        return buffer.slice();
    }

    public final ByteBuffer duplicate() {
        return buffer.duplicate();
    }

    public final ByteBuffer asReadOnlyBuffer() {
        return buffer.asReadOnlyBuffer();
    }

    public final byte get() {
        return buffer.get();
    }

    public final ByteBuffer put(byte b) {
        return buffer.put(b);
    }

    public final byte get(int index) {
        return buffer.get(index);
    }

    public final ByteBuffer put(int index, byte b) {
        return buffer.put(index, b);
    }

    public final ByteBuffer get(byte[] dst, int offset, int length) {
        return buffer.get(dst, offset, length);
    }

    public final ByteBuffer get(byte[] dst) {
        return buffer.get(dst);
    }

    public final ByteBuffer put(ByteBuffer src) {
        return buffer.put(src);
    }

    public final ByteBuffer put(byte[] src, int offset, int length) {
        return buffer.put(src, offset, length);
    }

    public final ByteBuffer put(byte[] src) {
        return buffer.put(src, 0, src.length);
    }

    public final byte[] array() {
        return buffer.array();
    }

    public final ByteBuffer compact() {
        return buffer.compact();
    }

    public final char getChar() {
        return buffer.getChar();
    }

    public final ByteBuffer putChar(char value) {
        return buffer.putChar(value);
    }

    public final char getChar(int index) {
        return buffer.getChar(index);
    }

    public final ByteBuffer putChar(int index, char value) {
        return buffer.putChar(index, value);
    }

    public final CharBuffer asCharBuffer() {
        return buffer.asCharBuffer();
    }

    public final short getShort() {
        return buffer.getShort();
    }

    public final ByteBuffer putShort(short value) {
        return buffer.putShort(value);
    }

    public final short getShort(int index) {
        return buffer.getShort(index);
    }

    public final ByteBuffer putShort(int index, short value) {
        return buffer.putShort(index, value);
    }

    public final ShortBuffer asShortBuffer() {
        return buffer.asShortBuffer();
    }

    public final int getInt() {
        return buffer.getInt();
    }

    public final ByteBuffer putInt(int value) {
        return buffer.putInt(value);
    }

    public final int getInt(int index) {
        return buffer.getInt(index);
    }

    public final ByteBuffer putInt(int index, int value) {
        return buffer.putInt(index, value);
    }

    public final IntBuffer asIntBuffer() {
        return buffer.asIntBuffer();
    }

    public final long getLong() {
        return buffer.getLong();
    }

    public final ByteBuffer putLong(long value) {
        return buffer.putLong(value);
    }

    public final long getLong(int index) {
        return buffer.getLong(index);
    }

    public final ByteBuffer putLong(int index, long value) {
        return buffer.putLong(index, value);
    }

    public final LongBuffer asLongBuffer() {
        return buffer.asLongBuffer();
    }

    public final float getFloat() {
        return buffer.getFloat();
    }

    public final ByteBuffer putFloat(float value) {
        return buffer.putFloat(value);
    }

    public final float getFloat(int index) {
        return buffer.getFloat(index);
    }

    public final ByteBuffer putFloat(int index, float value) {
        return buffer.putFloat(index, value);
    }

    public final FloatBuffer asFloatBuffer() {
        return buffer.asFloatBuffer();
    }

    public final double getDouble() {
        return buffer.getDouble();
    }

    public final ByteBuffer putDouble(double value) {
        return buffer.putDouble(value);
    }

    public final double getDouble(int index) {
        return buffer.getDouble(index);
    }

    public final ByteBuffer putDouble(int index, double value) {
        return buffer.putDouble(index, value);
    }

    public final DoubleBuffer asDoubleBuffer() {
        return buffer.asDoubleBuffer();
    }

    /**
     * 保留资源
     */
    @Override
    public void retain() {

    }

    /**
     * 释放资源
     */
    @Override
    public void release() {
        if (released.compareAndSet(false, true)) {
            if (released != null) {
                resource.release();
            }
        }
    }

    /**
     * 是否使用中
     *
     * @return 使用标示
     */
    @Override
    public boolean isUsed() {
        return released.get() == false;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p/>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     * {@code x}, {@code x.equals(x)} should return
     * {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     * {@code x} and {@code y}, {@code x.equals(y)}
     * should return {@code true} if and only if
     * {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     * {@code x}, {@code y}, and {@code z}, if
     * {@code x.equals(y)} returns {@code true} and
     * {@code y.equals(z)} returns {@code true}, then
     * {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     * {@code x} and {@code y}, multiple invocations of
     * {@code x.equals(y)} consistently return {@code true}
     * or consistently return {@code false}, provided no
     * information used in {@code equals} comparisons on the
     * objects is modified.
     * <li>For any non-null reference value {@code x},
     * {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p/>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p/>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see java.util.HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ReferenceBuffer buffer1 = (ReferenceBuffer) obj;
        if (buffer != null ? !buffer.equals(buffer1.buffer) : buffer1.buffer != null) {
            return false;
        }

        if (resource != null ? !resource.equals(buffer1.resource) : buffer1.resource != null) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * <p/>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     * an execution of a Java application, the {@code hashCode} method
     * must consistently return the same integer, provided no information
     * used in {@code equals} comparisons on the object is modified.
     * This integer need not remain consistent from one execution of an
     * application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     * method, then calling the {@code hashCode} method on each of
     * the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     * according to the {@link Object#equals(Object)}
     * method, then calling the {@code hashCode} method on each of the
     * two objects must produce distinct integer results.  However, the
     * programmer should be aware that producing distinct integer results
     * for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p/>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java<font size="-2"><sup>TM</sup></font> programming language.)
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        int result = buffer!=null?buffer.hashCode():0;
        result = 31*result+(resource!=null?resource.hashCode():0);
        return result;
    }
}

