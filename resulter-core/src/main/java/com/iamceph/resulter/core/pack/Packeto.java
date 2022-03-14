package com.iamceph.resulter.core.pack;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.iamceph.resulter.core.DataResultable;
import com.iamceph.resulter.core.Resultable;
import com.iamceph.resulter.core.model.ResultableData;
import lombok.experimental.UtilityClass;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class Packeto {
    /**
     * Packs the raw {@link Message} as ResultableData.
     *
     * @param input input to pack
     * @param <K>   type
     * @return ResultableData
     */
    public <K extends Message> ResultableData pack(@NotNull K input) {
        return pack(DataResultable.failIfNull(input));
    }

    /**
     * Packs the {@link DataResultable} into a {@link ResultableData}.
     * <p>
     * NOTE: the {@link DataResultable#data()} object needs to implement {@link ProtoWrapper}!
     *
     * @param input input for packing
     * @return packed {@link ResultableData}.
     */
    @NotNull
    public ResultableData pack(@NotNull DataResultable<?> input) {
        if (input.isFail()) {
            return ResultableData.newBuilder()
                    .setResult(input.asProto())
                    .build();
        }

        final var data = input.data();
        if (data instanceof Message) {
            return buildMessage(input, (Message) data);
        }

        if (data instanceof ProtoWrapper) {
            final var wrapped = (ProtoWrapper<?>) data;
            final var message = wrapped.asProto();

            return buildMessage(input, message);
        }

        val errorResult = DataResultable.fail("Cannot convert data - " + data.getClass().getSimpleName());
        return ResultableData.newBuilder()
                .setResult(errorResult.asProto())
                .build();
    }

    /**
     * Unpacks given {@link ResultableData} into a {@link DataResultable}.
     *
     * @param input input to unpack
     * @param type  type to unpack with
     * @param <K>   type of the unpacking class
     * @return Unpacked data in {@link DataResultable}
     */
    @NotNull
    public <K extends Message> DataResultable<K> unpack(@NotNull ResultableData input, @NotNull Class<K> type) {
        final var result = Resultable.convert(input.getResult());
        if (result.isFail()) {
            return result.transform();
        }

        return unpack(input.getData(), type);
    }

    /**
     * Unpacks given {@link ResultableData} into a {@link DataResultable}.
     *
     * @param input input to unpack
     * @param type  type to unpack with
     * @param <K>   type of the unpacking class
     * @return Unpacked data in {@link DataResultable}
     */
    @NotNull
    public <K extends Message> DataResultable<K> unpack(@NotNull Any input, @NotNull Class<K> type) {
        try {
            return DataResultable.failIfNull(unpackUnsafe(input, type));
        } catch (Exception e) {
            return DataResultable.fail(e);
        }
    }

    /**
     * Unpacks given {@link Any} data.
     * NOTE: This method is unsafe, use {@link Packeto#unpack(ResultableData, Class)} or {@link Packeto#unpack(Any, Class)} rather.
     *
     * @param input input to unpack
     * @param type  type to unpack with
     * @param <K>   type of the unpacking class
     * @return Unpacked data in {@link DataResultable}
     */
    @ApiStatus.Internal
    @Nullable
    public <K extends Message> K unpackUnsafe(@NotNull Any input, @NotNull Class<K> type) throws InvalidProtocolBufferException {
        return input.unpack(type);
    }

    /**
     * Builds the ResultableData.
     *
     * @param input   input resultable
     * @param message message to pack
     * @return resultable data.
     */
    private ResultableData buildMessage(Resultable input, Message message) {
        return ResultableData.newBuilder()
                .setResult(input.asProto())
                .setData(Any.pack(message))
                .build();
    }
}
