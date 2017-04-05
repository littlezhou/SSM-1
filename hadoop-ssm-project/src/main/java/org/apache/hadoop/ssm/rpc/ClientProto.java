// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: add_test.proto

package org.apache.hadoop.ssm.rpc;

public final class ClientProto {
  private ClientProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface AddParametersOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional int32 para1 = 1;
    /**
     * <code>optional int32 para1 = 1;</code>
     */
    boolean hasPara1();
    /**
     * <code>optional int32 para1 = 1;</code>
     */
    int getPara1();

    // optional int32 para2 = 3;
    /**
     * <code>optional int32 para2 = 3;</code>
     */
    boolean hasPara2();
    /**
     * <code>optional int32 para2 = 3;</code>
     */
    int getPara2();
  }
  /**
   * Protobuf type {@code rpcTest.protobuf.AddParameters}
   */
  public static final class AddParameters extends
      com.google.protobuf.GeneratedMessage
      implements AddParametersOrBuilder {
    // Use AddParameters.newBuilder() to construct.
    private AddParameters(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private AddParameters(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final AddParameters defaultInstance;
    public static AddParameters getDefaultInstance() {
      return defaultInstance;
    }

    public AddParameters getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private AddParameters(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              para1_ = input.readInt32();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000002;
              para2_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ClientProto.internal_static_rpcTest_protobuf_AddParameters_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ClientProto.internal_static_rpcTest_protobuf_AddParameters_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AddParameters.class, Builder.class);
    }

    public static com.google.protobuf.Parser<AddParameters> PARSER =
        new com.google.protobuf.AbstractParser<AddParameters>() {
      public AddParameters parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AddParameters(input, extensionRegistry);
      }
    };

    @Override
    public com.google.protobuf.Parser<AddParameters> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional int32 para1 = 1;
    public static final int PARA1_FIELD_NUMBER = 1;
    private int para1_;
    /**
     * <code>optional int32 para1 = 1;</code>
     */
    public boolean hasPara1() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional int32 para1 = 1;</code>
     */
    public int getPara1() {
      return para1_;
    }

    // optional int32 para2 = 3;
    public static final int PARA2_FIELD_NUMBER = 3;
    private int para2_;
    /**
     * <code>optional int32 para2 = 3;</code>
     */
    public boolean hasPara2() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional int32 para2 = 3;</code>
     */
    public int getPara2() {
      return para2_;
    }

    private void initFields() {
      para1_ = 0;
      para2_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, para1_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt32(3, para2_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, para1_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, para2_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    protected Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static AddParameters parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AddParameters parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AddParameters parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AddParameters parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AddParameters parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AddParameters parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static AddParameters parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static AddParameters parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static AddParameters parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AddParameters parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(AddParameters prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code rpcTest.protobuf.AddParameters}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements AddParametersOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ClientProto.internal_static_rpcTest_protobuf_AddParameters_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ClientProto.internal_static_rpcTest_protobuf_AddParameters_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AddParameters.class, Builder.class);
      }

      // Construct using rpcTest.protobuf.ClientProto.AddParameters.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        para1_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        para2_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ClientProto.internal_static_rpcTest_protobuf_AddParameters_descriptor;
      }

      public AddParameters getDefaultInstanceForType() {
        return AddParameters.getDefaultInstance();
      }

      public AddParameters build() {
        AddParameters result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public AddParameters buildPartial() {
        AddParameters result = new AddParameters(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.para1_ = para1_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.para2_ = para2_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof AddParameters) {
          return mergeFrom((AddParameters)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AddParameters other) {
        if (other == AddParameters.getDefaultInstance()) return this;
        if (other.hasPara1()) {
          setPara1(other.getPara1());
        }
        if (other.hasPara2()) {
          setPara2(other.getPara2());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        AddParameters parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (AddParameters) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional int32 para1 = 1;
      private int para1_ ;
      /**
       * <code>optional int32 para1 = 1;</code>
       */
      public boolean hasPara1() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional int32 para1 = 1;</code>
       */
      public int getPara1() {
        return para1_;
      }
      /**
       * <code>optional int32 para1 = 1;</code>
       */
      public Builder setPara1(int value) {
        bitField0_ |= 0x00000001;
        para1_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 para1 = 1;</code>
       */
      public Builder clearPara1() {
        bitField0_ = (bitField0_ & ~0x00000001);
        para1_ = 0;
        onChanged();
        return this;
      }

      // optional int32 para2 = 3;
      private int para2_ ;
      /**
       * <code>optional int32 para2 = 3;</code>
       */
      public boolean hasPara2() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional int32 para2 = 3;</code>
       */
      public int getPara2() {
        return para2_;
      }
      /**
       * <code>optional int32 para2 = 3;</code>
       */
      public Builder setPara2(int value) {
        bitField0_ |= 0x00000002;
        para2_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 para2 = 3;</code>
       */
      public Builder clearPara2() {
        bitField0_ = (bitField0_ & ~0x00000002);
        para2_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:rpcTest.protobuf.AddParameters)
    }

    static {
      defaultInstance = new AddParameters(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:rpcTest.protobuf.AddParameters)
  }

  public interface AddResultOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional int32 result = 1;
    /**
     * <code>optional int32 result = 1;</code>
     */
    boolean hasResult();
    /**
     * <code>optional int32 result = 1;</code>
     */
    int getResult();
  }
  /**
   * Protobuf type {@code rpcTest.protobuf.AddResult}
   */
  public static final class AddResult extends
      com.google.protobuf.GeneratedMessage
      implements AddResultOrBuilder {
    // Use AddResult.newBuilder() to construct.
    private AddResult(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private AddResult(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final AddResult defaultInstance;
    public static AddResult getDefaultInstance() {
      return defaultInstance;
    }

    public AddResult getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private AddResult(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              result_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ClientProto.internal_static_rpcTest_protobuf_AddResult_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ClientProto.internal_static_rpcTest_protobuf_AddResult_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AddResult.class, Builder.class);
    }

    public static com.google.protobuf.Parser<AddResult> PARSER =
        new com.google.protobuf.AbstractParser<AddResult>() {
      public AddResult parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AddResult(input, extensionRegistry);
      }
    };

    @Override
    public com.google.protobuf.Parser<AddResult> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional int32 result = 1;
    public static final int RESULT_FIELD_NUMBER = 1;
    private int result_;
    /**
     * <code>optional int32 result = 1;</code>
     */
    public boolean hasResult() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional int32 result = 1;</code>
     */
    public int getResult() {
      return result_;
    }

    private void initFields() {
      result_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, result_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, result_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    protected Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static AddResult parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AddResult parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AddResult parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AddResult parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AddResult parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AddResult parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static AddResult parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static AddResult parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static AddResult parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AddResult parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(AddResult prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code rpcTest.protobuf.AddResult}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements AddResultOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ClientProto.internal_static_rpcTest_protobuf_AddResult_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ClientProto.internal_static_rpcTest_protobuf_AddResult_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AddResult.class, Builder.class);
      }

      // Construct using rpcTest.protobuf.ClientProto.AddResult.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        result_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ClientProto.internal_static_rpcTest_protobuf_AddResult_descriptor;
      }

      public AddResult getDefaultInstanceForType() {
        return AddResult.getDefaultInstance();
      }

      public AddResult build() {
        AddResult result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public AddResult buildPartial() {
        AddResult result = new AddResult(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.result_ = result_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof AddResult) {
          return mergeFrom((AddResult)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AddResult other) {
        if (other == AddResult.getDefaultInstance()) return this;
        if (other.hasResult()) {
          setResult(other.getResult());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        AddResult parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (AddResult) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional int32 result = 1;
      private int result_ ;
      /**
       * <code>optional int32 result = 1;</code>
       */
      public boolean hasResult() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional int32 result = 1;</code>
       */
      public int getResult() {
        return result_;
      }
      /**
       * <code>optional int32 result = 1;</code>
       */
      public Builder setResult(int value) {
        bitField0_ |= 0x00000001;
        result_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 result = 1;</code>
       */
      public Builder clearResult() {
        bitField0_ = (bitField0_ & ~0x00000001);
        result_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:rpcTest.protobuf.AddResult)
    }

    static {
      defaultInstance = new AddResult(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:rpcTest.protobuf.AddResult)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_rpcTest_protobuf_AddParameters_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_rpcTest_protobuf_AddParameters_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_rpcTest_protobuf_AddResult_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_rpcTest_protobuf_AddResult_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\016add_test.proto\022\020rpcTest.protobuf\"-\n\rAd" +
      "dParameters\022\r\n\005para1\030\001 \001(\005\022\r\n\005para2\030\003 \001(" +
      "\005\"\033\n\tAddResult\022\016\n\006result\030\001 \001(\005B\037\n\020rpcTes" +
      "t.protobufB\013ClientProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_rpcTest_protobuf_AddParameters_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_rpcTest_protobuf_AddParameters_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_rpcTest_protobuf_AddParameters_descriptor,
              new String[] { "Para1", "Para2", });
          internal_static_rpcTest_protobuf_AddResult_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_rpcTest_protobuf_AddResult_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_rpcTest_protobuf_AddResult_descriptor,
              new String[] { "Result", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
